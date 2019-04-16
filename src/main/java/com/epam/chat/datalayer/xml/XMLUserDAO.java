package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.User;

import javax.management.relation.Role;
import java.util.List;

public class XMLUserDAO implements UserDAO {

    //XMLDomParser here

    /**
     * Login user using xml parser
     * @param userToLogin user we want to login
     */
    @Override
    public void login(User userToLogin) {
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Check user logged in using parser
     * @param user user to check
     * @return boolean result
     */
    @Override
    public boolean isLoggedIn(User user) {
        throw new UnsupportedOperationException("Implement this method");

    }

    /**
     * Logout user using xml file
     * @param userToLogout user we want to logout
     */
    @Override
    public void logout(User userToLogout) {
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Unckick user using xml parser
     * @param user user we want to logout
     */
    @Override
    public void unkick(User user) {
        throw new UnsupportedOperationException("Implement this method");
    }


    /**
     * Kick user using xml parser
     * @param user user we want to kick from system
     */
    @Override
    public void kick(User user) {
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Check user is kicked using xml parser
     * @param user user to check
     * @return boolean result
     */
    @Override
    public boolean isKicked(User user) {
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get all logged users using xml parser
     * @return all logged users from xml file
     */
    @Override
    public List<User> getAllLogged() {
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get role from xml file using parser by user nick
     * @param nick nick of user to find the role
     * @return user role
     */
    @Override
    public Role getRole(String nick) {
        throw new UnsupportedOperationException("Implement this method");
    }
}