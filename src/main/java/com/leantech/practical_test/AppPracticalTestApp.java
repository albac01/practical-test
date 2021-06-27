package com.leantech.practical_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Application entry point
 * 
 * @author abaquero
 */
@SpringBootApplication
public class AppPracticalTestApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AppPracticalTestApp.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppPracticalTestApp.class);
	}
}
