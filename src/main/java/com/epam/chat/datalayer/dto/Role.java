package com.epam.chat.datalayer.dto;

public enum Role {
    ADMIN("Administrator role"),
    USER("User role");

    private final String description;

    Role(String description) {
        this.description = description;
    }

}
