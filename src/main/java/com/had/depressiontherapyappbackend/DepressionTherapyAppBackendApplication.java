package com.had.depressiontherapyappbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@RestController
@ComponentScan({"com.had.depressiontherapyappbackend"})
public class DepressionTherapyAppBackendApplication implements CommandLineRunner {

	//private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(DepressionTherapyAppBackendApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		System.out.println("\n-----------------------------------------\n");
		System.out.println(pwdEncoder.encode("qwertyui"));
		System.out.println("\n-----------------------------------------\n");
		pwdEncoder = null;
	}

	

}
