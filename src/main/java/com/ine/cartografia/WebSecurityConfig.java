package com.ine.cartografia;




import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.captcha.botdetect.web.servlet.CaptchaServlet;



@ComponentScan(basePackages = { "com.ine.cartografia"} )
@SpringBootConfiguration
@EnableAutoConfiguration
public class WebSecurityConfig extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdministracionDelGeoprocesamientoApplication.class);
	}
//	@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/core/css/");
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/core/js/");
//        registry.addResourceHandler("/funcion/**").addResourceLocations("classpath:/static/funcion/");
//        registry.addResourceHandler("/stilosjs/**").addResourceLocations("classpath:/static/stilosjs/");
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/stilosjs/css/");
//        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
//    }
	@Bean
    ServletRegistrationBean<CaptchaServlet> captchaServletRegistration () {
      ServletRegistrationBean<CaptchaServlet> srb = new ServletRegistrationBean<CaptchaServlet>();
      srb.setServlet(new CaptchaServlet());
      srb.addUrlMappings("/botdetectcaptcha");
      return srb;
    }
 @Bean
 public ServletContextInitializer initializer() {
     return new ServletContextInitializer() {

         @Override
         public void onStartup(ServletContext servletContext) throws ServletException {
             servletContext.setInitParameter("BDC_helpLinkMode", "IMAGE");
         }
     };
 }

	
}