package com.ine.cartografia.controllers;

import java.io.IOException;

import java.security.Principal;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.ine.cartografia.service.UserService;


@Controller
@EnableWebMvc
@Configuration
public class UsuariosControllers implements WebMvcConfigurer{
	
	@Autowired
	public UserService userService;
	protected static final Log logger = LogFactory.getLog(UsuariosControllers.class.getName());
	
	  @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/admin").setViewName("admin");
	        registry.addViewController("/agregar").setViewName("add");
	        registry.addViewController("/delete").setViewName("admin");
	        registry.addViewController("/viewAdmin").setViewName("admin");
	        registry.addViewController("/Avance").setViewName("mostrarAvance");
	        registry.addViewController("/MultiProcesos").setViewName("multiprocesos");
	        registry.addViewController("/register").setViewName("admin");
	        registry.addViewController("/welcome").setViewName("adminC");
	        registry.addViewController("/Error").setViewName("admin");
	        
	    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
 
	 @RequestMapping(value = {"/","/login"}, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout , HttpServletRequest request,
			HttpServletResponse response, Principal principal) throws IOException{
		ModelAndView model = new ModelAndView();
//		if(validator.isValidCaptcha(captcha)) {
//			logger.info("Mensaje de vista:******************************** Received request ");
//		} else{
//			model.addObject("error", "Please validate captcha first");
//		}
		if(principal != null) {
			model.addObject("info2", "Ya ha inciado sesión anteriormente");
		}

		if (error != null) {
			model.addObject("error", "Invalid username or passwor!");
		}

		if (logout != null) {
			model.addObject("message", " Sección  terminada Satisfactoriamente!");
		}
		logger.info("Mensaje de vista:******************************** Received request to show login page");
		model.setViewName("login");

		return model;
	}
	

}
