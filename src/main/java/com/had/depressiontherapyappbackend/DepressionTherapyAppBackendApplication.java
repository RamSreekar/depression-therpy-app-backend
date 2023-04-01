package com.had.depressiontherapyappbackend;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.had.depressiontherapyappbackend.payloads.ApiResponse;
import com.had.depressiontherapyappbackend.repositories.UserRepo;
import com.had.depressiontherapyappbackend.serviceImpl.UserServiceImpl;

@SpringBootApplication
@RestController
@ComponentScan({"com.had.depressiontherapyappbackend"})
public class DepressionTherapyAppBackendApplication implements CommandLineRunner {

	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(DepressionTherapyAppBackendApplication.class, args);
	}

	// @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// 	return new WebMvcConfigurerAdapter() {
	// 		@Override
	// 		public void addCorsMappings(CorsRegistry registry) {
	// 			registry.addMapping("/**").allowedOrigins("*");
	// 		}
	// 	};
	// }

	@Configuration
	@EnableWebMvc
	public class CorsConfiguration implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowedHeaders("*");
			//.allowCredentials(true);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		System.out.println("\n-----------------------------------------\n");
		System.out.println(pwdEncoder.encode("doctor"));
		System.out.println("\n-----------------------------------------\n");
		pwdEncoder = null;

		// ResponseEntity<?> response = this.userServiceImpl.createUserWithoutApi();
		// ApiResponse apiResponse = (ApiResponse) response.getBody();
		// System.out.println(apiResponse.getMessage());

		// UserServiceImpl userServiceImpl = new UserServiceImpl(this.userRepo);
		// userServiceImpl.createUserWithoutApi();
	}

	

}
