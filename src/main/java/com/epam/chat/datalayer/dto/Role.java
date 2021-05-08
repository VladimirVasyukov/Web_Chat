package com.epam.chat.datalayer.dto;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Role {
    ADMIN(1,"Administrator role"),
    USER(2,"User role");

    private static final Map<Integer, Role> ID_ROLES_MAP = initIDRolesMap();
    private final int id;
    private final String description;

    Role(int id, String description) {
        this.id = id;
        this.description = description;
    }
    public static Role getRoleByID(int id) {
        return ID_ROLES_MAP.get(id);
    }

    public static int getIDByRole(Role role) {
        return role.id;
    }

    public String getDescription() {
        return description;
    }

    private static Map<Integer, Role> initIDRolesMap() {
        return Stream.of(values()).collect(Collectors.toMap(role -> role.id, role -> role));
    }

}
