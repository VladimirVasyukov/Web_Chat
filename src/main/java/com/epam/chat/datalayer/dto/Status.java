package com.epam.chat.datalayer.dto;

public enum Status {
    LOGIN("logged in the chat"),
    MESSAGE("sent message"),
    KICK("kicked from the chat"),
    LOGOUT("left the chat");

    private final String description;

    Status(String description) {
        this.description = description;
    }

}
