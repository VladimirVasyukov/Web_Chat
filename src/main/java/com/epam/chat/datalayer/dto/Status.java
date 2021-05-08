package com.epam.chat.datalayer.dto;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Status {
    LOGIN(1,"logged in the chat"),
    MESSAGE(2,"sent message"),
    KICK(3,"kicked from the chat"),
    LOGOUT(4,"left the chat");

    private static final Map<Integer, Status> ID_STATUS_MAP = initIDStatusMap();
    private final int id;
    private final String description;

    Status(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Status getStatusByID(int id) {
        return ID_STATUS_MAP.get(id);
    }

    public static int getIDByStatus(Status status) {
        return status.id;
    }

    public String getDescription() {
        return description;
    }

    private static Map<Integer, Status> initIDStatusMap() {
        return Stream.of(values()).collect(Collectors.toMap(status -> status.id, status -> status));
    }

}
