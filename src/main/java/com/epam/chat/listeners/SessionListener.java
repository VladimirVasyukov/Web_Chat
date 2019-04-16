package com.epam.chat.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * Implementation of session listener
 *
 */
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		throw new UnsupportedOperationException("Implement this method");		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		throw new UnsupportedOperationException("Implement this method");		
	}

}
