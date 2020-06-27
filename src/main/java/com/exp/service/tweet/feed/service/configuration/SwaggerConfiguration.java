package com.exp.service.tweet.feed.service.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.function.Predicate;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean("controllerPackagePath")
    public String getSwaggerControllerPath(@Value("${io.swagger.controller.package:com.exp.service.tweet.feed.service.controller}") String path) {
        return path;
    }

    @Bean
    public ApiInfo getApiInfo(@Value("${spring.application.name:Application API}") String apiName) {
        return new ApiInfo(apiName, "Sample API",
                "1.0", "Free to use",
                new springfox.documentation.service.Contact("Saurabh Belwal", "http://a.b.com", "a@b.com"),
                "", "", Collections.emptyList());
    }

    @Bean
    public Docket api(@Qualifier("controllerPackagePath") String path, ApiInfo apiInfo) {
        Predicate<RequestHandler> requestHandlerPredicate = StringUtils.isEmpty(path) ? RequestHandlerSelectors.any()::apply : RequestHandlerSelectors.basePackage(path)::apply;
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(requestHandlerPredicate::test)
                .build()
                .apiInfo(apiInfo);
    }
}
