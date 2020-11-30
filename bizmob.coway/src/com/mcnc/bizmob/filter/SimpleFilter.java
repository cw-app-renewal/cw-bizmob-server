package com.mcnc.bizmob.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
 
public class SimpleFilter implements Filter{

	private FilterConfig fc;
	public void destroy() {
		this.fc = null; 
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new SimpleRequestWrapper((HttpServletRequest) req), resp);

	}

	public void init(FilterConfig fc) throws ServletException {
		this.fc = fc;
	}
}
