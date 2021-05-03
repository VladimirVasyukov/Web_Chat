package com.epam.chat.datalayer.db;

public class MySQLException extends RuntimeException {
    public static final String MYSQL_MESSAGE_DAO_ERROR =
        "Can't create MySQL message DAO because of connection pool initialisation failure";
    public static final String MYSQL_USER_DAO_ERROR =
        "Can't create MySQL user DAO because of connection pool initialisation failure";
    public static final String SEND_MESSAGE_ERROR =
        "Can't send message because of connection pool failure or MySQL database not found or damaged";
    public static final String MESSAGE_LIST_ERROR =
        "Can't get last messages because of connection pool failure or MySQL database not found or damaged";
    public static final String USER_LOGIN_ERROR =
        "Can't login user because of connection pool failure or MySQL database not found or damaged";
    public static final String USER_IS_LOGGED_IN_CHECK_ERROR =
        "Can't check if user is logged in because of connection pool failure or MySQL database not found or damaged";
    public static final String USER_LOGOUT_ERROR =
        "Can't logout user because of connection pool failure or MySQL database not found or damaged";
    public static final String USER_UNKICK_ERROR =
        "Can't unkick user because of connection pool failure or MySQL database not found or damaged";
    public static final String USER_KICK_ERROR =
        "Can't kick user because of connection pool failure or MySQL database not found or damaged";
    public static final String USER_IS_KICKED_CHECK_ERROR =
        "Can't check if user is kicked because of connection pool failure or MySQL database not found or damaged";
    public static final String USER_LOGGED_LIST_ERROR =
        "Can't get all logged in users because of connection pool failure or MySQL database not found or damaged";
    public static final String USER_KICKED_LIST_ERROR =
        "Can't get all kicked users because of connection pool failure or MySQL database not found or damaged";
    public static final String USER_GET_ROLE_ERROR =
        "Can't get user role because of connection pool failure or MySQL database not found or damaged";

    public MySQLException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
