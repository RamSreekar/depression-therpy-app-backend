package com.had.depressiontherapyappbackend;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootApplication
@RestController
@ComponentScan({"com.had.depressiontherapyappbackend"})
public class DepressionTherapyAppBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DepressionTherapyAppBackendApplication.class, args);
	}

	@Configuration
	@EnableWebMvc
	public class CorsConfiguration implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedHeaders("*");
			//.allowCredentials(true);
		}
	}

	private void initializeFirebaseApp() throws Exception{
		InputStream serviceAccount = new FileInputStream("src/main/resources/better-u-1bfee-5b2cf3de3022.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		// FirebaseApp.initializeApp(options);
		
		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		this.initializeFirebaseApp();
		
		System.out.println("\n----------- Server started ----------------\n");
	}

	

}
