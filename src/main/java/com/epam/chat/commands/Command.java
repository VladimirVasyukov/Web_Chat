package com.epam.chat.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Interface for commands
 *
 */
public interface Command {
	
	/**
	 * Perform action provided by command
	 * @param req
	 * @param resp
	 */
	void execute(HttpServletRequest req, HttpServletResponse resp);

}
