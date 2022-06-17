package com.ine.cartografia;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;





@Configuration
public class WebApp {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
