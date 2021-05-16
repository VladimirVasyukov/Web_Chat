package com.epam.chat.commands;

import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.DBType;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.Status;
import com.epam.chat.datalayer.dto.User;
import com.epam.chat.listeners.SessionListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Processor {
    private static final String DAO_FACTORY = "daoFactory";
    private static final String KICKABLE_USER = "kickableUser";
    private static final String KICKED_USER = "kickedUser";
    private static final String MESSAGE_COUNT = "messageCount";
    private static final String MESSAGE_TEXT = "messageText";
    private static final String USER_NICKNAME = "userNickname";
    private static final String ERROR_ATTRIBUTE = "errorMessage";
    private static final String KICKED_USERS_ATTRIBUTE = "kickedUsers";
    private static final String LOGGED_USERS_ATTRIBUTE = "loggedUsers";
    private static final String MESSAGES_ATTRIBUTE = "messages";
    private static final String NICKNAME_ATTRIBUTE = "nickname";
    private static final String ADMIN_NAME_DOMEN = "@epam.com";
    private static final String CHAT_PATH = "chat";
    private static final String ERROR_PATH = "error";
    private static final String LOGIN_PATH = "login";
    private static final String MESSAGE_LIST_PATH = "message-list";
    private static final String IS_LOGGED_IN_ERROR = "Sorry, user with same nickname is already logged in";
    private static final String NICKNAME_LENGTH_ERROR = "Nickname must be more than 2 characters";
    private static final int MIN_NICKNAME_LENGTH = 2;
    private DAOFactory daoFactory;
    private UserDAO userDAO;
    private MessageDAO messageDAO;

    public Processor() {
        this.daoFactory = null;
        this.userDAO = null;
        this.messageDAO = null;
    }

    public void sendMessage(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userFromNickname = (String) session.getAttribute(NICKNAME_ATTRIBUTE);
        UserDAO userDAO = getUserDAOServletRequest(request);
        User userFrom = getUserFromNickname(userDAO, userFromNickname);

        String messageText = request.getParameter(MESSAGE_TEXT);
        MessageDAO messageDAO = getMessageDAOServletRequest(request);
        messageDAO.sendMessage(new Message(userFrom, LocalDateTime.now(), messageText, Status.MESSAGE));

        response.sendRedirect(CHAT_PATH);
    }

    public void getLast(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        MessageDAO messageDAO = getMessageDAOServletRequest(request);
        List<Message> messageList = messageDAO.getLast(Integer.parseInt(request.getParameter(MESSAGE_COUNT)));
        Collections.reverse(messageList);
        request.setAttribute(MESSAGES_ATTRIBUTE, messageList);
        request.getRequestDispatcher(MESSAGE_LIST_PATH).forward(request, response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String userNickname = request.getParameter(USER_NICKNAME);
        int userNicknameLength = userNickname.length();
        if (userNicknameLength < MIN_NICKNAME_LENGTH) {
            request.setAttribute(ERROR_ATTRIBUTE, NICKNAME_LENGTH_ERROR);
        }

        UserDAO userDAO = getUserDAOServletRequest(request);
        User user = getUserFromRequest(userDAO, userNickname);

        if (userDAO.isLoggedIn(user)) {
            request.setAttribute(ERROR_ATTRIBUTE, IS_LOGGED_IN_ERROR);
        } else {
            userDAO.login(user);
        }

        if (request.getAttribute(ERROR_ATTRIBUTE) == null) {
            HttpSession session = request.getSession(true);
            session.setAttribute(NICKNAME_ATTRIBUTE, userNickname);
            SessionListener.addUserSession(userNickname, session);

            response.sendRedirect(CHAT_PATH);
        } else {
            request.getRequestDispatcher(ERROR_PATH).forward(request, response);
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userToLogoutNickname = (String) session.getAttribute(NICKNAME_ATTRIBUTE);

        UserDAO userDAO = getUserDAOServletRequest(request);
        User userToLogout = getUserFromNickname(userDAO, userToLogoutNickname);
        userDAO.logout(userToLogout);

        session.invalidate();
        response.sendRedirect(LOGIN_PATH);
    }

    public void unkick(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String userNickname = request.getParameter(KICKED_USER);

        UserDAO userDAO = getUserDAOServletRequest(request);
        User userToUnkick = getUserFromNickname(userDAO, userNickname);

        userDAO.unkick(userToUnkick);
        response.sendRedirect(CHAT_PATH);
    }

    public void kick(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        String adminNickname = (String) session.getAttribute(NICKNAME_ATTRIBUTE);
        UserDAO userDAO = getUserDAOServletRequest(request);
        User admin = getUserFromNickname(userDAO, adminNickname);

        String kickableUserNickname = request.getParameter(KICKABLE_USER);
        User kickableUser = getUserFromNickname(userDAO, kickableUserNickname);

        userDAO.kick(admin, kickableUser);
        SessionListener.invalidate(kickableUserNickname);
        response.sendRedirect(CHAT_PATH);
    }

    public void getAllLogged(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        UserDAO userDAO = getUserDAOServletRequest(request);
        List<User> userList = userDAO.getAllLogged();
        request.setAttribute(LOGGED_USERS_ATTRIBUTE, userList);
        request.getRequestDispatcher(CHAT_PATH).forward(request, response);
    }

    public void getAllKicked(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        UserDAO userDAO = getUserDAOServletRequest(request);
        List<User> userList = userDAO.getAllKicked();
        request.setAttribute(KICKED_USERS_ATTRIBUTE, userList);
        request.getRequestDispatcher(CHAT_PATH).forward(request, response);
    }

    private UserDAO getUserDAOServletRequest(HttpServletRequest request) {
        if (userDAO == null) {
            userDAO = getDAOFactoryServletRequest(request).getUserDAO();
        }
        return userDAO;
    }

    private MessageDAO getMessageDAOServletRequest(HttpServletRequest request) {
        if (messageDAO == null) {
            messageDAO = getDAOFactoryServletRequest(request).getMessageDAO();
        }
        return messageDAO;
    }

    private DAOFactory getDAOFactoryServletRequest(HttpServletRequest request) {
        if (daoFactory == null) {
            ServletContext servletContext = request.getServletContext();
            DBType dbType = DBType.valueOf((servletContext.getInitParameter(DAO_FACTORY)).toUpperCase());
            daoFactory = DAOFactory.getInstance(dbType);
        }
        return daoFactory;
    }

    private User getUserFromRequest(UserDAO userDAO, String userNickname) {
        Role userRole = userDAO.getRole(userNickname);
        if (userRole == null) {
            if (userNickname.endsWith(ADMIN_NAME_DOMEN)) {
                userRole = Role.ADMIN;
            } else {
                userRole = Role.USER;
            }
        }
        return new User(userNickname, userRole);
    }

    private User getUserFromNickname(UserDAO userDAO, String userNickname) {
        Role userRole = userDAO.getRole(userNickname);
        return new User(userNickname, userRole);
    }
}
