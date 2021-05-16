package com.epam.chat.filters;

import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.DBType;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.User;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Implementation of servlet filter
 */
public class ChatFilter implements Filter {
    private static final String DAO_FACTORY = "daoFactory";
    private static final String USER_NICKNAME_ATTRIBUTE = "nickname";
    private static final String ERROR_ATTRIBUTE = "errorMessage";
    private static final String IS_KICKED_ERROR = "You were kicked from chat by admin";
    private static final String NOT_LOGGED_IN_ERROR = "You have to be logged in to access this page";
    private static final String ERROR_PATH = "WEB-INF/pages/error.jsp";

    @Override
    public void destroy() {

    }

    /**
     * Applying filter to request
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        ServletContext servletContext = httpRequest.getServletContext();
        DBType dbType = DBType.valueOf((servletContext.getInitParameter(DAO_FACTORY)).toUpperCase());
        DAOFactory daoFactory = DAOFactory.getInstance(dbType);
        UserDAO userDAO = daoFactory.getUserDAO();

        checkSessionErrors(httpRequest, userDAO);

        if (httpRequest.getAttribute(ERROR_ATTRIBUTE) != null) {
            httpRequest.getRequestDispatcher(ERROR_PATH).forward(httpRequest, response);
        } else {
            MessageDAO messageDAO = daoFactory.getMessageDAO();
            List<Message> messages = messageDAO.getLast(30);
            Collections.reverse(messages);
            request.setAttribute("messages", messages);

            UserDAO getUserDAO = daoFactory.getUserDAO();
            List<User> users = getUserDAO.getAllLogged();
            List<User> mappedUsers = users
                .stream()
                .map(u -> new User(u.getNickname(), u.getRole(), getUserDAO.isKicked(u)))
                .collect(Collectors.toList());
            request.setAttribute("users", mappedUsers);

            User currentUser = getCurrentUser(httpRequest, userDAO);
            request.setAttribute("currentUser", currentUser);

            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig config) {

    }

    private User getCurrentUser(HttpServletRequest httpRequest, UserDAO userDAO) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null && session.getAttribute(USER_NICKNAME_ATTRIBUTE) != null) {
            String userNickname = (String) session.getAttribute(USER_NICKNAME_ATTRIBUTE);
            Role userRole = userDAO.getRole(userNickname);
            return new User(userNickname, userRole);
        }

        throw new IllegalArgumentException();
    }

    private void checkSessionErrors(HttpServletRequest httpRequest, UserDAO userDAO) {
        try {
            User user = getCurrentUser(httpRequest, userDAO);
            boolean isLoggedIn = userDAO.isLoggedIn(user);
            boolean isKicked = userDAO.isKicked(user);

            if (!isLoggedIn) {
                httpRequest.setAttribute(ERROR_ATTRIBUTE, NOT_LOGGED_IN_ERROR);
            }

            if (isKicked) {
                httpRequest.setAttribute(ERROR_ATTRIBUTE, IS_KICKED_ERROR);
            }

        } catch (IllegalArgumentException e) {
            httpRequest.setAttribute(ERROR_ATTRIBUTE, NOT_LOGGED_IN_ERROR);
        }
    }
}
