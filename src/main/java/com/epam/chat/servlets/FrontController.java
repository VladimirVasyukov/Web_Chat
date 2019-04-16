package com.epam.chat.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.chat.commands.Command;

/**
 * 
 * Front controller implementation
 *
 */
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Processing GET requests
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new UnsupportedOperationException("Implement this method");
	}

	/**
	 * Processing POST requests
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		throw new UnsupportedOperationException("Implement this method");
	}
	
	/**
	 * Define command by her name from request
	 * @param commandName
	 * @return chosen command
	 */
	private Command defineCommand(String commandName) {
		throw new UnsupportedOperationException("Implement this method");
	}

}
