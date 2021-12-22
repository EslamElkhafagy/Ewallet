package com.example.ewallet.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.ewallet.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Swagger configuration for application")
                .description("Implement e-wallet with REST API to create it, top it up, check its balance and withdraw funds using Spring (preferably Spring Boot).Funds on any wallet should not go below zero")
                .version("1.1.0")
                .contact(new Contact("Eslam Elkhafagy", "https://github.com/EslamElkhafagy/Ewallet", "eslamelkhafagy@gmail.com"))
                .build();
    }
}
