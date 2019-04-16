package com.epam.chat.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * Implementation of servlet filter
 *
 */
public class ChatFilter implements Filter {

	@Override
	public void destroy() {
		//TODO
	}

	/**
	 * Applying filter to request
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		throw new UnsupportedOperationException("Implement this method");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		//TODO
	}

}
