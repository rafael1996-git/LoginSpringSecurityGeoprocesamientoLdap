package com.ine.cartografia;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;



//@ImportResource("classpath:web.xml")
@ComponentScan(basePackages = { "com.ine.cartografia.controllers"} )
@Component
@ImportResource({"classpath*:user-beans.xml"})
public class AppConfig implements Filter{
	
	protected static final Log logger = LogFactory.getLog(AppConfig.class.getName());

	    @Override
	    public void init(FilterConfig filterConfig) throws ServletException {

	    }

	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	      throws IOException, ServletException {
//	        logger.info("CustomFilter is invoked");
	        chain.doFilter(request, response);
	    }

		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			
		}

		

	

}