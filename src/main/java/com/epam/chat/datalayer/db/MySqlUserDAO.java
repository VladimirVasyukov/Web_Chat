package com.epam.chat.datalayer.db;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.db.connectionpool.ConnectionPool;
import com.epam.chat.datalayer.db.connectionpool.ConnectionPoolException;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDAO implements UserDAO {
    private static final Logger LOG = LogManager.getLogger(MySqlMessageDAO.class.getName());
    private static final String LOGIN = "login";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String LOGOUT = "logout";
    private static final String UNKICK = "unkick";
    private static final String KICK = "kick";
    private static final String IS_KICKED = "isKicked";
    private static final String GET_ALL_LOGGED = "getAllLogged";
    private static final String GET_ALL_KICKED = "getAllKicked";
    private static final String GET_ROLE = "getRole";
    private static final String ADD_USER = "addUser";
    private static final String IS_LOGGED_IN_COLUMN = "IsLoggedIn";
    private static final String IS_KICKED_COLUMN = "IsKicked";
    private static final String NICKNAME_COLUMN = "Nickname";
    private static final String ROLE_ID_COLUMN = "RoleID";
    private static final int FIRST_PARAMETER = 1;
    private static final int SECOND_PARAMETER = 2;
    private final ConnectionPool connectionPool;
    private final QueryManager queryManager;

    public MySqlUserDAO(ConnectionPool connectionPool, QueryManager queryManager) {
        this.connectionPool = connectionPool;
        this.queryManager = queryManager;
    }

    /**
     * @param userToLogin user we want to login
     */
    @Override
    public void login(User userToLogin) {
        String userNickname = userToLogin.getNickname();
        String sqlAddUserQuery = queryManager.getValue(ADD_USER);
        String sqlLoginQuery = queryManager.getValue(LOGIN);
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement addUserPreparedStatement = connection.prepareStatement(sqlAddUserQuery);
             PreparedStatement loginPreparedStatement = connection.prepareStatement(sqlLoginQuery)) {
            connection.setAutoCommit(false);

            addUserPreparedStatement.setString(FIRST_PARAMETER, userNickname);
            addUserPreparedStatement.setInt(SECOND_PARAMETER, Role.getIDByRole(userToLogin.getRole()));
            addUserPreparedStatement.executeUpdate();

            loginPreparedStatement.setString(FIRST_PARAMETER, userNickname);
            loginPreparedStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (ConnectionPoolException | SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new MySQLException(MySQLException.USER_LOGIN_ERROR, e);
        }
    }

    /**
     * @param user user to check
     * @return boolean result
     */
    @Override
    public boolean isLoggedIn(User user) {
        String sqlQuery = queryManager.getValue(IS_LOGGED_IN);
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setString(FIRST_PARAMETER, user.getNickname());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.first();
                return resultSet.getBoolean(IS_LOGGED_IN_COLUMN);
            }
        } catch (ConnectionPoolException | SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new MySQLException(MySQLException.USER_IS_LOGGED_IN_CHECK_ERROR, e);
        }
    }

    /**
     * @param userToLogout user we want to logout
     */
    @Override
    public void logout(User userToLogout) {
        unkickOrLogoutUser(userToLogout, LOGOUT, MySQLException.USER_LOGOUT_ERROR);
    }

    @Override
    public void unkick(User user) {
        unkickOrLogoutUser(user, UNKICK, MySQLException.USER_UNKICK_ERROR);
    }

    /**
     * @param admin        - user responsible for the kick action (with the role admin)
     * @param kickableUser - user that should be kicked
     */
    @Override
    public void kick(User admin, User kickableUser) {
        String sqlQuery = queryManager.getValue(KICK);
        if (admin.getRole() == Role.ADMIN) {
            try (Connection connection = connectionPool.takeConnection()) {
                connection.setAutoCommit(false);

                addKickMessage(connection, admin, kickableUser);
                if (isKickedUserLoggedIn(connection, kickableUser)) {
                    logoutKickedUser(connection, kickableUser);
                }
                try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                    connection.commit();
                    connection.setAutoCommit(true);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (ConnectionPoolException | SQLException e) {
                LOG.error(e.getMessage(), e);
                throw new MySQLException(MySQLException.USER_KICK_ERROR, e);
            }
        }
    }


    /**
     * @param user user to check
     * @return boolean result
     */
    @Override
    public boolean isKicked(User user) {
        String sqlQuery = queryManager.getValue(IS_KICKED);
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setString(FIRST_PARAMETER, user.getNickname());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.first();
                return resultSet.getBoolean(IS_KICKED_COLUMN);
            }
        } catch (ConnectionPoolException | SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new MySQLException(MySQLException.USER_IS_KICKED_CHECK_ERROR, e);
        }
    }


    /**
     * @return all logged users
     */
    @Override
    public List<User> getAllLogged() {
        return getUserList(GET_ALL_LOGGED, MySQLException.USER_LOGGED_LIST_ERROR);
    }

    /**
     * @return all kicked users
     */
    @Override
    public List<User> getAllKicked() {
        return getUserList(GET_ALL_KICKED, MySQLException.USER_KICKED_LIST_ERROR);
    }

    /**
     * @param nick nick of user to find the role
     * @return user role
     */
    @Override
    public Role getRole(String nick) {
        String sqlQuery = queryManager.getValue(GET_ROLE);
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setString(FIRST_PARAMETER, nick);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.first();
                return Role.getRoleByID(resultSet.getInt(ROLE_ID_COLUMN));
            }
        } catch (ConnectionPoolException | SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new MySQLException(MySQLException.USER_GET_ROLE_ERROR, e);
        }
    }


    private void unkickOrLogoutUser(User user, String queryKey, String errorMessage) {
        String sqlQuery = queryManager.getValue(queryKey);
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(FIRST_PARAMETER, user.getNickname());
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new MySQLException(errorMessage, e);
        }
    }

    private void addKickMessage(Connection connection, User admin, User kickableUser)
        throws SQLException {
        String sqlQuery = queryManager.getValue(KICK);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(FIRST_PARAMETER, admin.getNickname());
            preparedStatement.setString(SECOND_PARAMETER, kickableUser.getNickname());
            preparedStatement.executeUpdate();
        }
    }

    private boolean isKickedUserLoggedIn(Connection connection, User kickableUser)
        throws SQLException {
        String sqlQuery = queryManager.getValue(IS_LOGGED_IN);
        try (PreparedStatement preparedStatement = connection.prepareStatement(
            sqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            boolean isKickedUserLoggedIn;
            preparedStatement.setString(FIRST_PARAMETER, kickableUser.getNickname());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.first();
                isKickedUserLoggedIn = resultSet.getBoolean(IS_LOGGED_IN_COLUMN);
            }
            return isKickedUserLoggedIn;
        }
    }

    private void logoutKickedUser(Connection connection, User kickableUser) throws SQLException {
        String sqlQuery = queryManager.getValue(LOGOUT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(FIRST_PARAMETER, kickableUser.getNickname());
            preparedStatement.executeUpdate();
        }
    }

    private List<User> getUserList(String queryKey, String errorMessage) {
        String sqlQuery = queryManager.getValue(queryKey);
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                userList.add(getUserFromResultSet(resultSet));
            }
            return userList;
        } catch (ConnectionPoolException | SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new MySQLException(errorMessage, e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        String nickname = resultSet.getString(NICKNAME_COLUMN);
        Role role = Role.getRoleByID(resultSet.getInt(ROLE_ID_COLUMN));
        return new User(nickname, role);
    }

}
