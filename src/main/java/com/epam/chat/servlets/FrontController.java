package com.epam.chat.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.chat.commands.Command;
import com.epam.chat.commands.KickedList;
import com.epam.chat.commands.LoggedList;
import com.epam.chat.commands.MessageList;
import com.epam.chat.commands.Kick;
import com.epam.chat.commands.Login;
import com.epam.chat.commands.Logout;
import com.epam.chat.commands.Processor;
import com.epam.chat.commands.SendMessage;
import com.epam.chat.commands.Unkick;

/**
 * Front controller implementation
 */
public class FrontController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String COMMAND_PARAMETER = "cmd";
    private static final String GET_LAST_CMD_KEY = "getLast";
    private static final String SEND_MESSAGE_CMD_KEY = "sendMessage";
    private static final String LOGIN_CMD_KEY = "login";
    private static final String LOGOUT_CMD_KEY = "logout";
    private static final String UNKICK_CMD_KEY = "unkick";
    private static final String KICK_CMD_KEY = "kick";
    private static final String GET_ALL_LOGGED_CMD_KEY = "getAllLogged";
    private static final String GET_ALL_KICKED_CMD_KEY = "getAllKicked";
    private final Map<String, Command> commandMap = new HashMap<>();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        initCommandMap();
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String commandName = req.getParameter(COMMAND_PARAMETER);
        Command command = defineCommand(commandName);
        command.execute(req, resp);
    }

    /**
     * Processing GET requests
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Processing POST requests
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        processRequest(req, resp);
    }

    /**
     * Define command by her name from request
     *
     * @param commandName command name from request
     * @return chosen command
     */
    protected Command defineCommand(String commandName) {
        return commandMap.get(commandName);
    }

    private void initCommandMap() {
        Processor processor = new Processor();
        commandMap.put(GET_LAST_CMD_KEY, new MessageList(processor));
        commandMap.put(SEND_MESSAGE_CMD_KEY, new SendMessage(processor));
        commandMap.put(LOGIN_CMD_KEY, new Login(processor));
        commandMap.put(LOGOUT_CMD_KEY, new Logout(processor));
        commandMap.put(UNKICK_CMD_KEY, new Unkick(processor));
        commandMap.put(KICK_CMD_KEY, new Kick(processor));
        commandMap.put(GET_ALL_LOGGED_CMD_KEY, new LoggedList(processor));
        commandMap.put(GET_ALL_KICKED_CMD_KEY, new KickedList(processor));
    }

}
