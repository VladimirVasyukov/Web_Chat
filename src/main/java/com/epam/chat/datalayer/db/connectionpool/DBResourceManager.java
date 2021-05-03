package com.epam.chat.datalayer.db.connectionpool;

import java.util.ResourceBundle;

public class DBResourceManager {
    private final static DBResourceManager INSTANCE = new DBResourceManager();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    public static DBResourceManager getInstance() {
        return INSTANCE;
    }

    public String getValue(String key) {
        return resourceBundle.getString(key);
    }
}
