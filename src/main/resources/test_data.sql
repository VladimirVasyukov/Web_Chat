INSERT INTO Status (Title, Description) VALUES ('LOGIN', 'logged in the chat');
INSERT INTO Status (Title, Description) VALUES ('MESSAGE', 'sent message');
INSERT INTO Status (Title, Description) VALUES ('KICK', 'kicked from the chat');
INSERT INTO Status (Title, Description) VALUES ('LOGOUT', 'left the chat');

INSERT INTO Role (Title, Description) VALUES ('ADMIN', 'Administrator role');
INSERT INTO Role (Title, Description) VALUES ('USER', 'User role');

INSERT INTO User VALUES ('Vladimir', 1);
INSERT INTO User VALUES ('Olga', 1);
INSERT INTO User VALUES ('Admin', 2);

INSERT INTO Message (UserFrom, TimeStamp, StatusID) VALUES ('Vladimir', '2021-04-15T18:45:59', 1);
INSERT INTO Message (UserFrom, Message, TimeStamp, StatusID) VALUES ('Vladimir', 'Hello world!', '2021-04-15T18:57:33', 2);
INSERT INTO Message (UserFrom, TimeStamp, StatusID) VALUES ('Olga' '2021-04-15T19:02:39' 1);
INSERT INTO Message (UserFrom, Message, TimeStamp, StatusID) VALUES ('Admin' 'Vladimir' '2021-04-15T19:05:13' 3);
INSERT INTO Message (UserFrom, TimeStamp, StatusID) VALUES ('Olga' '2021-04-15T19:07:44' 4);
