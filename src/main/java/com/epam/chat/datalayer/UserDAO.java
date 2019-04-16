package com.epam.chat.datalayer;

import com.epam.chat.datalayer.dto.User;

import javax.management.relation.Role;
import java.util.List;


/**
 * Describes access interface to user Data Access Object
 */
public interface UserDAO {

    /**
     * Login user
     * @param userToLogin user we want to lgin
     */
    void login(User userToLogin);

    /**
     * Check is user logged in
     * @param user user to check
     * @return boolean result of check
     */
    boolean isLoggedIn(User user);

    /**
     * Logout user from system
     * @param userToLogout user we want to logout
     */
    void logout(User userToLogout);

    /**
     * Unckick user from system
     * @param user user we want to logout
     */
    void unkick(User user);

    /**
     * User we want to kick
     * @param user user we want to kick from system
     */
    void kick(User user);

    /**
     * Check is user was kicked from system
     * @param user user to check
     * @return boolean result of check
     */
    boolean isKicked(User user);

    /**
     * Get all users logged in the system
     * @return list of users
     */
    List<User> getAllLogged();

    /**
     * Get role of user by his nickname
     * @param nick nick of user to find the role
     * @return role os user
     */
    Role getRole(String nick);

}
