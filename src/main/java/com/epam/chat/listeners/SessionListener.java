package com.epam.chat.listeners;

import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.DBType;
import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of session listener
 */
public class SessionListener implements HttpSessionListener {
    private static final String DAO_FACTORY = "daoFactory";
    private static final String USER_NICKNAME = "nickname";
    private static final Map<String, HttpSession> SESSION = new HashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent event) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        ServletContext servletContext = session.getServletContext();
        DBType dbType = DBType.valueOf((servletContext.getInitParameter(DAO_FACTORY)).toUpperCase());
        DAOFactory daoFactory = DAOFactory.getInstance(dbType);
        UserDAO userDAO = daoFactory.getUserDAO();

        String userNickname = (String) session.getAttribute(USER_NICKNAME);
        Role userRole = userDAO.getRole(userNickname);
        User user = new User(userNickname, userRole);

        if (userDAO.isLoggedIn(user)) {
            userDAO.logout(user);
        }

        SESSION.remove(userNickname);
    }

    public static void addUserSession(String userNickname, HttpSession session) {
        SESSION.put(userNickname, session);
    }

    public static void invalidate(String userNickname) {
        HttpSession session = SESSION.get(userNickname);
        session.invalidate();
    }

}
