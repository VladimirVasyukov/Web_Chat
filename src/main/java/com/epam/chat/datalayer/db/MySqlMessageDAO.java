package com.epam.chat.datalayer.db;

import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.db.connectionpool.ConnectionPool;
import com.epam.chat.datalayer.db.connectionpool.ConnectionPoolException;
import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.Status;
import com.epam.chat.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MySqlMessageDAO implements MessageDAO {
    private static final Logger LOG = LogManager.getLogger(MySqlMessageDAO.class.getName());
    private static final String SEND_MESSAGE = "sendMessage";
    private static final String GET_LAST = "getLast";
    private static final String USER_FROM_COLUMN = "UserFrom";
    private static final String TIMESTAMP_COLUMN = "TimeStamp";
    private static final String MESSAGE_COLUMN = "Message";
    private static final String STATUS_ID_COLUMN = "StatusID";
    private static final String ROLE_ID_COLUMN = "RoleID";
    private static final String EMPTY_MESSAGE_TEXT = "";
    private static final int FIRST_PARAMETER = 1;
    private static final int SECOND_PARAMETER = 2;
    private static final int THIRD_PARAMETER = 3;
    private static final int FOURTH_PARAMETER = 4;
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");
    private final ConnectionPool connectionPool;
    private final QueryManager queryManager;

    public MySqlMessageDAO(ConnectionPool connectionPool, QueryManager queryManager) {
        this.connectionPool = connectionPool;
        this.queryManager = queryManager;
    }

    @Override
    public void sendMessage(Message message) {
        String sqlQuery = queryManager.getValue(SEND_MESSAGE);
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(FIRST_PARAMETER, message.getUserFrom().getNickname());
            preparedStatement.setString(SECOND_PARAMETER, message.getText());
            preparedStatement.setString(THIRD_PARAMETER, message.getTimeStamp().format(DATE_TIME_FORMAT));
            preparedStatement.setInt(FOURTH_PARAMETER, Status.getIDByStatus(message.getStatus()));
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new MySQLException(MySQLException.SEND_MESSAGE_ERROR, e);
        }
    }

    @Override
    public List<Message> getLast(int count) {
        String sqlQuery = queryManager.getValue(GET_LAST);
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setInt(FIRST_PARAMETER, count);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Message> messageList = new ArrayList<>();
                while (resultSet.next()) {
                    messageList.add(getMessageFromResultSet(resultSet));
                }
                return messageList;
            }
        } catch (ConnectionPoolException | SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new MySQLException(MySQLException.MESSAGE_LIST_ERROR, e);
        }
    }

    private Message getMessageFromResultSet(ResultSet resultSet) throws SQLException {
        String userFromNickname = resultSet.getString(USER_FROM_COLUMN);
        Role userFromRole = Role.getRoleByID(resultSet.getInt(ROLE_ID_COLUMN));
        User userFrom = new User(userFromNickname, userFromRole);
        LocalDateTime timeStamp = resultSet.getTimestamp(TIMESTAMP_COLUMN).toLocalDateTime();
        String message = resultSet.getString(MESSAGE_COLUMN);
        if (message == null) {
            message = EMPTY_MESSAGE_TEXT;
        }
        Status status = Status.getStatusByID(resultSet.getInt(STATUS_ID_COLUMN));
        return new Message(userFrom, timeStamp, message, status);
    }

}
