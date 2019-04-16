package com.epam.chat.datalayer.db;

import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.dto.Message;

import java.util.List;

public class OracleMessageDAO implements MessageDAO {

    @Override
    public void sendMessage(Message message) {
        throw new UnsupportedOperationException("Implement this method");

    }

    @Override
    public List<Message> getLast(int count) {
        throw new UnsupportedOperationException("Implement this method");
    }
}
