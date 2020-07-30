package com.spring.jwt.config;

import com.spring.jwt.security.service.UserDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.spring.jwt.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(UserDetailsImpl.class)
                .securitySchemes(Arrays.asList(apiKey()));

    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Spring Boot Jwt Auth REST API")
                .description("\"Spring Boot Jwt Auth REST API for Crud Operation\"")
                .version("1.0.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("jwtToken", "Authorization", "header");
    }

}