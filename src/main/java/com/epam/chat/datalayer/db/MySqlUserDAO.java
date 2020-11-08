package com.epam.chat.datalayer.db;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.User;

import java.util.List;

public class MySqlUserDAO implements UserDAO {


    /**
     *
     * @param userToLogin user we want to lgin
     */
    @Override
    public void login(User userToLogin) {
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     *
     * @param user user to check
     * @return
     */
    @Override
    public boolean isLoggedIn(User user) {
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     *
     * @param userToLogout user we want to logout
     */
    @Override
    public void logout(User userToLogout) {
        throw new UnsupportedOperationException("Implement this method");
    }

    @Override
    public void unkick(User user) {
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     *
     * @param admin - user responsible for the kick action (with the role admin)
     * @param kickableUser - user that should be kicked
     */
    @Override
    public void kick(User admin, User kickableUser) {
        throw new UnsupportedOperationException("Implement this method");
    }


    /**
     *
     * @param user user to check
     * @return
     */
    @Override
    public boolean isKicked(User user) {
        throw new UnsupportedOperationException("Implement this method");
    }


    /**
     *
     * @return
     */
    @Override
    public List<User> getAllLogged() {
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     *
     * @return
     */
    @Override
    public List<User> getAllKicked() {
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     *
     * @param nick nick of user to find the role
     * @return
     */
    @Override
    public Role getRole(String nick) {
        throw new UnsupportedOperationException("Implement this method");
    }
}
