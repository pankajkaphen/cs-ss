package com.pankaj.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class HeaderFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String authHeader = request.getHeader("app-id");

		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(req, res);
		} else {
			if (authHeader == null || !authHeader.equals("figi")) {
				throw new ServletException("Required header key/value is incorrect, please try again!");
			}
		}
		chain.doFilter(req, res);

	}
}
