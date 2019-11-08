package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.dto.Message;

import java.util.List;

public class XMLMessageDAO implements MessageDAO {

    //place XMLDOMParser or XMLMessageParser here

    @Override
    /**
     * Get last messages from xml file using parser
     */
    public List<Message> getLast(int count) {
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Send message using xml file and parse
     * @param message - message to send
     */
    @Override
    public void sendMessage(Message message) {
        throw new UnsupportedOperationException("Implement this method");
    }


}
