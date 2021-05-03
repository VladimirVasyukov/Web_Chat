package com.epam.chat.datalayer.db;

import java.util.ResourceBundle;

public class QueryManager {
    private final static QueryManager INSTANCE = new QueryManager();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("queries");

    public static QueryManager getInstance() {
        return INSTANCE;
    }

    public String getValue(String key) {
        return resourceBundle.getString(key);
    }

}
