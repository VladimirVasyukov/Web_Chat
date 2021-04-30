package com.epam.chat.datalayer.xml;

public class XMLException extends RuntimeException {

    public static final String XML_MESSAGE_LIST_ERROR =
        "Can't create message list because XML Messages file is not found or damaged";
    public static final String XML_MESSAGE_SEND_ERROR =
        "Can't send message because XML Messages file is not found or damaged";
    public static final String XML_USER_LOGIN_ERROR =
        "Can't login user because XML file is not found or damaged";
    public static final String XML_USER_LOGGED_CHECK_ERROR =
        "Can't check if user is logged in because XML file is not found or damaged";
    public static final String XML_USER_LOGOUT_ERROR =
        "Can't logout user because XML file is not found or damaged";
    public static final String XML_USER_UNCKICK_ERROR =
        "Can't unkick user because XML file is not found or damaged";
    public static final String XML_USER_KICK_ERROR =
        "Can't kick user because XML file is not found or damaged";
    public static final String XML_USER_KICKED_CHECK_ERROR =
        "Can't check if user is kicked because XML file is not found or damaged";
    public static final String XML_USER_LOGGED_LIST_ERROR =
        "Can't create logged in user list because XML file is not found or damaged";
    public static final String XML_USER_KICKED_LIST_ERROR =
        "Can't create kicked user list because XML file is not found or damaged";
    public static final String XML_USER_GET_ROLE_ERROR =
        "Can't create get user role because XML file is not found or damaged";

    public XMLException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
