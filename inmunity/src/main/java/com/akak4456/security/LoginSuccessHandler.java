package com.akak4456.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;

@Component
@Log
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	/*
	 * @Override protected String determineTargetUrl(HttpServletRequest
	 * request,HttpServletResponse response) { Object dest =
	 * request.getSession().getAttribute("dest");
	 * 
	 * String nextURL = null;
	 * 
	 * if(dest != null) { request.getSession().removeAttribute("dest");
	 * 
	 * nextURL = (String)dest; }else { nextURL = super.determineTargetUrl(request,
	 * response); }
	 * 
	 * log.info("nextURL:"+nextURL); return nextURL; }
	 */
	
	public LoginSuccessHandler() {
        setDefaultTargetUrl("/");
    }
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
    		Authentication authentication) throws ServletException, IOException {
     
        HttpSession session = request.getSession();
        if (session != null) {
            String redirectUrl = (String) session.getAttribute("dest");
            if (redirectUrl != null) {
                session.removeAttribute("dest");
                log.info("REDIRECT URL AFTER LOGIN:"+redirectUrl);
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
