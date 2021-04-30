package com.epam.chat.datalayer.dto;


import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents chat message
 */
public class Message {
    private final User userFrom;
    private final LocalDateTime timeStamp;
    private final String messageText;
    private final Status status;

    public Message(User userFrom, LocalDateTime timeStamp, String messageText, Status status) {
        this.userFrom = userFrom;
        this.timeStamp = timeStamp;
        this.messageText = messageText;
        this.status = status;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public Status getStatus() {
        return status;
    }

    public String getText() {
        return messageText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(userFrom, message.userFrom) && Objects.equals(timeStamp, message.timeStamp) &&
            status == message.status && messageText.equals(message.messageText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userFrom, timeStamp, status, messageText);
    }

    @Override
    public String toString() {
        return "Message{" +
            "userFrom=" + userFrom +
            ", timeStamp=" + timeStamp +
            ", status=" + status +
            ", text='" + messageText + '\'' +
            '}';
    }
}
