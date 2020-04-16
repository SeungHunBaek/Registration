package com.round1usa.registrationadminweb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	{
		String ContextPath = request.getContextPath();
		HttpSession session = request.getSession(false);
		System.out.println("interceptor");
		try{
			if(session == null ){
				System.out.println("session null");
				response.sendRedirect(ContextPath+"/login");
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
