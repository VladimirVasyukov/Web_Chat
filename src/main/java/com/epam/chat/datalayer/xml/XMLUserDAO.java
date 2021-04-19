package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.Status;
import com.epam.chat.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLUserDAO implements UserDAO {
    private static final String EMPTY_TEXT = "";
    private static final String MESSAGE = "message";
    private static final String USER_FROM = "user_from";
    private static final String TEXT = "text";
    private static final String STATUS = "status";
    private static final String USER = "user";
    private static final String NICKNAME = "nickname";
    private static final String ROLE = "role";
    private static final Logger LOG = LogManager.getLogger(XMLMessageDAO.class.getName());
    private static final Role USER_ROLE = Role.USER;
    private static final boolean FALSE = false;
    private final XMLProcessor xmlProcessor;

    public XMLUserDAO(XMLProcessor xmlProcessor) {
        this.xmlProcessor = xmlProcessor;
    }

    /**
     * Login user using xml parser
     * @param userToLogin user we want to login
     */
    @Override
    public void login(User userToLogin) {
        try {
            Element userElement = findUserXML(userToLogin.getNickname());
            if (userElement == null) {
                addNewUserXML(userToLogin);
            }
            if (!isKicked(userToLogin)) {
                new XMLMessageDAO(xmlProcessor).
                    sendMessage(new Message(userToLogin, LocalDateTime.now(), EMPTY_TEXT, Status.LOGIN));
            }
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new XMLException(
                "Can't login user because XML file is not found or damaged", e);
        }
    }

    /**
     * Check user logged in using parser
     * @param user user to check
     * @return boolean result
     */
    @Override
    public boolean isLoggedIn(User user) {
        try {
            boolean isLoggedIn = FALSE;
            String userNickname = user.getNickname();
            if (findUserXML(userNickname) != null) {
                Document messageDocument = xmlProcessor.parseMessagesXML();
                isLoggedIn = isLoggedInFromMessages(messageDocument, userNickname);
            }
            return isLoggedIn;
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new XMLException(
                "Can't check if user is logged in because XML file is not found or damaged", e);
        }
    }

    /**
     * Logout user using xml file
     * @param userToLogout user we want to logout
     */
    @Override
    public void logout(User userToLogout) {
        try {
            Element userElement = findUserXML(userToLogout.getNickname());
            if (userElement != null) {
                new XMLMessageDAO(xmlProcessor).
                    sendMessage(new Message(userToLogout, LocalDateTime.now(), EMPTY_TEXT, Status.LOGOUT));
            }
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new XMLException(
                "Can't logout user because XML file is not found or damaged", e);
        }
    }

    /**
     * Unckick user using xml parser
     * @param user user we want to logout
     */
    @Override
    public void unkick(User user) {
        try {
            String userNickname = user.getNickname();
            Element userElement = findUserXML(userNickname);
            if (userElement != null) {
                Document messageDocument = xmlProcessor.parseMessagesXML();
                Element kickUserMessageElement = findKickedUserMessage(messageDocument, userNickname);
                if (kickUserMessageElement != null) {
                    kickUserMessageElement.getParentNode().removeChild(kickUserMessageElement);
                    xmlProcessor.updateMessagesXML(messageDocument);
                }
            }
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new XMLException(
                "Can't unkick user because XML file is not found or damaged", e);
        }
    }


    /**
     * @param admin        - user responsible for the kick action (with the role admin)
     * @param kickableUser - user that should be kicked
     */
    @Override
    public void kick(User admin, User kickableUser) {
        if (admin.getRole() == Role.ADMIN) {
            try {
                String userNickname = kickableUser.getNickname();
                Element userElement = findUserXML(userNickname);
                if (userElement != null) {
                    new XMLMessageDAO(xmlProcessor).sendMessage(
                        new Message(admin, LocalDateTime.now(), kickableUser.getNickname(), Status.KICK));
                }
            } catch (FileNotFoundException e) {
                LOG.error(e.getMessage(), e);
                throw new XMLException(
                    "Can't kick user because XML file is not found or damaged", e);
            }
        }
    }

    /**
     * Check user is kicked using xml parser
     * @param user user to check
     * @return boolean result
     */
    @Override
    public boolean isKicked(User user) {
        boolean isKicked = FALSE;
        try {
            String userNickname = user.getNickname();
            Element userElement = findUserXML(userNickname);
            if (userElement != null) {
                Document messageDocument = xmlProcessor.parseMessagesXML();
                Element kickUserMessageElement = findKickedUserMessage(messageDocument, userNickname);
                isKicked = kickUserMessageElement != null;
            }
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new XMLException(
                "Can't check if user is kicked because XML file is not found or damaged", e);
        }
        return isKicked;
    }

    /**
     * Get all logged users using xml parser
     * @return all logged users from xml file
     */
    @Override
    public List<User> getAllLogged() {
        try {
            return getUserList(getUserLoginMap());
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new XMLException(
                "Can't create logged in user list because XML file is not found or damaged", e);
        }
    }

    /**
     * Get all kicked users
     * @return all kicked users from xml file
     */
    @Override
    public List<User> getAllKicked() {
        try {
            return getUserList(getUserKickMap());
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new XMLException(
                "Can't create kicked user list because XML file is not found or damaged", e);
        }
    }

    /**
     * Get role from xml file using parser by user nick
     * @param nick nick of user to find the role
     * @return user role
     */
    @Override
    public Role getRole(String nick) {
        try {
            Role userRole = USER_ROLE;
            Element userElement = findUserXML(nick);
            if (userElement != null) {
                userRole = xmlProcessor.getEnumValueFromString(
                    xmlProcessor.getChild(userElement, ROLE).getTextContent(), Role.values());
            }
            return userRole;
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new XMLException(
                "Can't create get user role because XML file is not found or damaged", e);
        }
    }

    private Element findUserXML(String nickname) throws FileNotFoundException {
        Document document = xmlProcessor.parseUsersXML();
        NodeList userNodeList = document.getElementsByTagName(USER);
        int userNodeListLength = userNodeList.getLength();
        Element userElement = null;
        for (int i = 0; i < userNodeListLength; i++) {
            if (xmlProcessor.getChildValue((Element) userNodeList.item(i), NICKNAME).equals(nickname)) {
                userElement = (Element) userNodeList.item(i);
                break;
            }
        }
        return userElement;
    }

    private void addNewUserXML(User user) throws FileNotFoundException {
        Document document = xmlProcessor.parseUsersXML();
        Element nickname = document.createElement(NICKNAME);
        nickname.setTextContent(user.getNickname());

        Element role = document.createElement(ROLE);
        role.setTextContent(user.getRole().toString());

        Element newUser = document.createElement(USER);
        newUser.appendChild(nickname);
        newUser.appendChild(role);

        Node root = document.getChildNodes().item(0);
        root.appendChild(newUser);
        xmlProcessor.updateUsersXML(document);
    }

    private boolean isLoggedInFromMessages(Document messageDocument, String userNickname) {
        NodeList messageNodeList = messageDocument.getElementsByTagName(MESSAGE);
        int messageNodeListLength = messageNodeList.getLength();
        boolean isLoggedIn = FALSE;
        for (int i = messageNodeListLength; i > 0; i--) {
            Element messageElement = (Element) messageNodeList.item(i - 1);
            Status xmlMessageStatus = xmlProcessor.getEnumValueFromString(
                xmlProcessor.getChildValue(messageElement, STATUS), Status.values());
            if (xmlProcessor.getChildValue(messageElement, USER_FROM).equals(userNickname)) {
                if (xmlMessageStatus != Status.LOGOUT) {
                    isLoggedIn = true;
                }
                break;
            }
            if (xmlMessageStatus == Status.KICK &&
                xmlProcessor.getChildValue(messageElement, TEXT).equals(userNickname)) {
                isLoggedIn = false;
                break;
            }
        }
        return isLoggedIn;
    }

    private Element findKickedUserMessage(Document messageDocument, String userNickname) {
        NodeList messageNodeList = messageDocument.getElementsByTagName(MESSAGE);
        int messageNodeListLength = messageNodeList.getLength();
        Element messageElement = null;
        for (int i = messageNodeListLength; i > 0; i--) {
            Element currentElement = (Element) messageNodeList.item(i - 1);
            Status xmlMessageStatus = xmlProcessor.getEnumValueFromString(
                xmlProcessor.getChildValue(currentElement, STATUS), Status.values());
            if (xmlMessageStatus == Status.KICK &&
                xmlProcessor.getChildValue(currentElement, TEXT).equals(userNickname)) {
                messageElement = currentElement;
                break;
            }
        }
        return messageElement;
    }

    private List<User> getUserList(Map<String, Boolean> soughtUserMap) throws FileNotFoundException {
        Map<String, Role> userRoles = getUserRoleMap();
        List<User> soughtUserList = new ArrayList<>();
        for (Map.Entry<String, Boolean> user : soughtUserMap.entrySet()) {
            if (user.getValue().equals(true)) {
                String nickname = user.getKey();
                soughtUserList.add(new User(nickname, userRoles.get(nickname)));
            }
        }
        return soughtUserList;
    }

    private Map<String, Role> getUserRoleMap() throws FileNotFoundException {
        Document usersDocument = xmlProcessor.parseUsersXML();
        NodeList userNodeList = usersDocument.getElementsByTagName(USER);
        int userNodeListLength = userNodeList.getLength();
        Map<String, Role> userRoles = new HashMap<>();

        for (int i = 0; i < userNodeListLength; i++) {
            Element userElement = (Element) userNodeList.item(i);
            String userNickname = xmlProcessor.getChildValue(userElement, NICKNAME);
            Role userRole = xmlProcessor.getEnumValueFromString(
                xmlProcessor.getChildValue(userElement, ROLE), Role.values());
            userRoles.put(userNickname, userRole);
        }
        return userRoles;
    }

    private Map<String, Boolean> getUserLoginMap() throws FileNotFoundException {
        Document messagesDocument = xmlProcessor.parseMessagesXML();
        NodeList messageNodeList = messagesDocument.getElementsByTagName(MESSAGE);
        int messageNodeListLength = messageNodeList.getLength();

        Map<String, Boolean> userLoginMap = new HashMap<>();
        for (int i = messageNodeListLength - 1; i >= 0; i--) {
            Element messageElement = (Element) messageNodeList.item(i);

            String nickname = xmlProcessor.getChildValue(messageElement, USER_FROM);
            Status xmlMessageStatus = xmlProcessor.getEnumValueFromString(
                xmlProcessor.getChildValue(messageElement, STATUS), Status.values());

            boolean alreadyCheckedUser = userLoginMap.containsKey(nickname);
            if (!alreadyCheckedUser) {
                if (xmlMessageStatus != Status.LOGOUT) {
                    userLoginMap.put(nickname, true);
                } else {
                    userLoginMap.put(nickname, false);
                }
            }
            if (xmlMessageStatus == Status.KICK) {
                nickname = xmlProcessor.getChildValue(messageElement, TEXT);
                alreadyCheckedUser = userLoginMap.containsKey(nickname);
                if (!alreadyCheckedUser) {
                    userLoginMap.put(nickname, false);
                }
            }
        }
        return userLoginMap;
    }

    private Map<String, Boolean> getUserKickMap() throws FileNotFoundException {
        Document messagesDocument = xmlProcessor.parseMessagesXML();
        NodeList messageNodeList = messagesDocument.getElementsByTagName(MESSAGE);
        int messageNodeListLength = messageNodeList.getLength();

        Map<String, Boolean> userKickMap = new HashMap<>();
        for (int i = messageNodeListLength - 1; i >= 0; i--) {
            Element messageElement = (Element) messageNodeList.item(i);

            String nickname = xmlProcessor.getChildValue(messageElement, USER_FROM);
            Status xmlMessageStatus = xmlProcessor.getEnumValueFromString(
                xmlProcessor.getChildValue(messageElement, STATUS), Status.values());

            boolean alreadyCheckedUser = userKickMap.containsKey(nickname);
            if (!alreadyCheckedUser) {
                userKickMap.put(nickname, false);
            }

            if (xmlMessageStatus == Status.KICK) {
                nickname = xmlProcessor.getChildValue(messageElement, TEXT);
                alreadyCheckedUser = userKickMap.containsKey(nickname);
                if (!alreadyCheckedUser) {
                    userKickMap.put(nickname, true);
                }
            }
        }
        return userKickMap;
    }
}
