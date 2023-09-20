package com.employeemanagement.utility;

import jakarta.servlet.http.HttpServletRequest;

public class RequestURL {

	public static String getSiteURL(HttpServletRequest request) {

		String siteURL = request.getRequestURL().toString();

		return siteURL.replace(request.getServletPath(), "");

	}

}
