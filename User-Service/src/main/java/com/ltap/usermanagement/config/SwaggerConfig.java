package com.ltap.usermanagement.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
    @EnableSwagger2
    public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(Sets.newHashSet("application/json" /*, "application/xml"*/))
                .consumes(Sets.newHashSet("application/json" /*, "application/xml"*/))
                .protocols(Sets.newHashSet("http", "https"))
                .forCodeGeneration(true)
                .select()
                .paths(regex(".*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("LTAP")
                .description("LTAP API reference for developers")
                .termsOfServiceUrl("http://www.ltap.com")
                .version("1.0")
                .build();
    }

}

