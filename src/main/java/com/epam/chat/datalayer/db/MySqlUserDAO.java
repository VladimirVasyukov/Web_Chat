package com.epam.chat.datalayer.db;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.User;

import java.util.List;

public class MySqlUserDAO implements UserDAO {

    @Override
    public void login(User userToLogin) {
        throw new UnsupportedOperationException("Implement this method");
    }

    @Override
    public boolean isLoggedIn(User user) {
        throw new UnsupportedOperationException("Implement this method");
    }

    @Override
    public void logout(User userToLogout) {
        throw new UnsupportedOperationException("Implement this method");
    }

    @Override
    public void unkick(User user) {
        throw new UnsupportedOperationException("Implement this method");
    }

    @Override
    public void kick(User user) {
        throw new UnsupportedOperationException("Implement this method");
    }

    @Override
    public boolean isKicked(User user) {
        throw new UnsupportedOperationException("Implement this method");
    }

    @Override
    public List<User> getAllLogged() {
        throw new UnsupportedOperationException("Implement this method");
    }

    @Override
    public Role getRole(String nick) {
        throw new UnsupportedOperationException("Implement this method");
    }
}
