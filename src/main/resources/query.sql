    --Get last N messages (for example, 5)
SELECT
    Message.ID,
    Message.UserFrom,
    Message.Message,
    Message.TimeStamp,
    Status.Title AS Status
FROM
    Message
        JOIN
    Status ON Message.StatusID = Status.ID
ORDER BY Message.TimeStamp DESC
LIMIT 5;

    --Send message
SET @sendingUser := 'Vladimir';
SET @messageText := 'Hello, World!';
INSERT INTO Message (UserFrom, Message, TimeStamp, StatusID) VALUES (@sendingUser, @messageText, NOW(), 2);

    --Login user
SET @userToLogin := 'Vladimir';
INSERT IGNORE User (Nick) VALUES (@userToLogin);
INSERT INTO Message (UserFrom, TimeStamp, StatusID) VALUES (@userToLogin, NOW(), 1);

    --Check user is logged in
SET @soughtUser := 'Vladimir';
SELECT
    IFNULL((SELECT
                    IF(Message.StatusID = 4, FALSE, TRUE)
            FROM
                Message
            WHERE
                Message.UserFrom = @soughtUser
            ORDER BY Message.TimeStamp DESC
            LIMIT 1),
            FALSE) AS IsLoggedIn;

    --Logout user
SET @userToLogout := 'Vladimir';
INSERT INTO Message (UserFrom, TimeStamp, StatusID) VALUES (@userToLogout, NOW(), 4);

    --Unkick user
SET @userToUnkick := 'Vladimir';
DELETE FROM Message
WHERE Message.Message = @userToUnkick AND Message.StatusID = 3;

    --Kick user
SET @admin := 'Admin';
SET @kickableUser = 'Vladimir';
INSERT INTO Message (UserFrom, Message, TimeStamp, StatusID) VALUES (@admin, @kickableUser, NOW(), 3);

    --Check user is kicked
SET @soughtUser := 'Vladimir';
SELECT
EXISTS( SELECT *
        FROM
            Message
        WHERE
            Message.StatusID = 3
        AND Message.Message = @soughtUser) AS isKicked;

    --Get all logged users
SELECT
    Message1.UserFrom AS Nickname, Role.Title AS Role
FROM
    Message Message1
        LEFT JOIN
    Message Message2 ON (Message1.UserFrom = Message2.UserFrom
        AND Message1.TimeStamp < Message2.TimeStamp)
        JOIN
    User ON Message1.UserFrom = User.Nick
        JOIN
    Role ON User.RoleID = Role.ID
    WHERE
    Message2.ID IS NULL
        AND Message1.StatusID <> 4
ORDER BY Message1.UserFrom;

    --Get all kicked users
SELECT
    Message.Message AS Nickname, Role.Title AS Role
FROM
    Message
        JOIN
    User ON Message.Message = User.Nick
        JOIN
    Role ON User.RoleID = Role.ID
WHERE
    StatusID = 3
ORDER BY Message;

    --Get user role
SET @userInQuestion := 'Admin';
SELECT
    Role.Title AS Role
FROM
    User
        JOIN
    Role ON User.RoleID = Role.ID
WHERE
    User.Nick = @userInQuestion;