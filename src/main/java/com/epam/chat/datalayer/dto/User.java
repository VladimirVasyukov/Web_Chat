package com.epam.chat.datalayer.dto;


import java.util.Objects;

/**
 * Represents chat user
 */
public class User {
    private final String nickname;
    private final Role role;
    private final Boolean kicked;

    public User(String nickname, Role role, Boolean kicked) {
        this.nickname = nickname;
        this.role = role;
        this.kicked = kicked;
    }

    public User(String nickname, Role role) {
        this(nickname, role, false);
    }

    public String getNickname() {
        return nickname;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAdmin() {
        return getRole() == Role.ADMIN;
    }

    public boolean isKicked() {
        return kicked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(nickname, user.nickname) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, role);
    }

    @Override
    public String toString() {
        return "User{" +
            "nickname='" + nickname + '\'' +
            ", role=" + role +
            '}';
    }
}
