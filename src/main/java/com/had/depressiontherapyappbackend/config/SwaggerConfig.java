package com.had.depressiontherapyappbackend.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInformation())
        .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    private ApiInfo getInformation() {
        String title = "Depression Therapy Application - API";
        String description = "";
        String version = "1.0.0";
        String termsOfServiceUrl = "";
        String license = "";
        String licenseUrl = "";
        
        return new ApiInfo(title, description, version, termsOfServiceUrl, 
                            new Contact("", "", ""), 
                            license, licenseUrl, Collections.emptyList()
                        );
    }

}
