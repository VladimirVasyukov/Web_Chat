package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.MessageDAO;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class XMLMessageDAO implements MessageDAO {
    private static final String USER_FROM = "user_from";
    private static final String TIME_STAMP = "time_stamp";
    private static final String TEXT = "text";
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String EMPTY_TEXT = "";
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final Logger LOG = LogManager.getLogger(XMLMessageDAO.class.getName());
    private final XMLProcessor xmlProcessor;

    public XMLMessageDAO(XMLProcessor xmlProcessor) {
        this.xmlProcessor = xmlProcessor;
    }


    /**
     * Get last messages from xml file using parser
     */
    @Override
    public List<Message> getLast(int count) {
        List<Message> messages = new ArrayList<>();
        try {
            Document document = xmlProcessor.parseMessagesXML();
            NodeList messageNodeList = document.getElementsByTagName(MESSAGE);
            int messageNodeListLength = messageNodeList.getLength();

            int messagesCount = count;
            if (messageNodeListLength < messagesCount) {
                messagesCount = messageNodeListLength;
            }

            for (int i = messageNodeListLength; i > messageNodeListLength - messagesCount; i--) {
                Element message = (Element) messageNodeList.item(i - 1);

                User userFrom = getUserFromXML(message);
                LocalDateTime timeStamp = getTimeStampFromXMLMessage(message);
                String messageText = getTextFromXMLMessage(message);
                Status status = getStatusFromXMLMessage(message);
                messages.add(new Message(userFrom, timeStamp, messageText, status));
            }
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new XMLException(
                "Can't create message list because XML Messages file is not found or damaged", e);
        }
        return messages;
    }

    /**
     * Send message using xml file and parse
     * @param message - message to send
     */
    @Override
    public void sendMessage(Message message) {
        try {
            Document document = xmlProcessor.parseMessagesXML();
            Element addMessage = getXMLElementFromMessage(document, message);
            Node root = document.getChildNodes().item(0);
            root.appendChild(addMessage);
            xmlProcessor.updateMessagesXML(document);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new XMLException(
                "Can't send message because XML Messages file is not found or damaged", e);
        }
    }

    private User getUserFromXML(Element message) {
        String userFromNicknameInXML = xmlProcessor.getChildValue(message, USER_FROM);
        Role userFromRole = new XMLUserDAO(xmlProcessor).getRole(userFromNicknameInXML);
        return new User(userFromNicknameInXML, userFromRole);
    }

    private LocalDateTime getTimeStampFromXMLMessage(Element message) {
        String dateTimeInXML = xmlProcessor.getChildValue(message, TIME_STAMP);
        return LocalDateTime.parse(dateTimeInXML, DATE_TIME_FORMAT);
    }

    private String getTextFromXMLMessage(Element message) {
        String messageTextInXML;
        if (xmlProcessor.getChild(message, TEXT) == null) {
            messageTextInXML = EMPTY_TEXT;
        } else {
            messageTextInXML = xmlProcessor.getChildValue(message, TEXT);
        }
        return messageTextInXML;
    }

    private Status getStatusFromXMLMessage(Element message) {
        String statusInXML = xmlProcessor.getChildValue(message, STATUS);
        return xmlProcessor.getEnumValueFromString(statusInXML, Status.values());
    }

    private Element getXMLElementFromMessage(Document document, Message message) {
        Element userFrom = document.createElement(USER_FROM);
        userFrom.setTextContent(message.getUserFrom().getNickname());

        Element addMessage = document.createElement(MESSAGE);
        addMessage.appendChild(userFrom);

        Element timeStamp = document.createElement(TIME_STAMP);
        timeStamp.setTextContent(message.getTimeStamp().format(DATE_TIME_FORMAT));
        addMessage.appendChild(timeStamp);

        if (!message.getText().equals(EMPTY_TEXT)) {
            Element text = document.createElement(TEXT);
            text.setTextContent(message.getText());
            addMessage.appendChild(text);
        }

        Element status = document.createElement(STATUS);
        status.setTextContent(message.getStatus().toString());
        addMessage.appendChild(status);

        return addMessage;
    }

}
