package com.ine.cartografia.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;

public class CustomAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
private String accessDeniedUrl;

    public CustomAccessDeniedHandler() {
    }

    public CustomAccessDeniedHandler(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }

    @Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		  response.sendRedirect(accessDeniedUrl);
	        request.getSession().setAttribute("CustomSessionAttribute", "!!");
		
	}

    public String getAccessDeniedUrl() {
        return accessDeniedUrl;
    }

    public void setAccessDeniedUrl(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }

	
}