package com.epam.chat.datalayer.dto;


import java.util.Objects;

/**
 * Represents chat user
 */
public class User {
    private final String nickname;
    private final Role role;

    public User(String nickname, Role role) {
        this.nickname = nickname;
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public Role getRole() {
        return role;
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
