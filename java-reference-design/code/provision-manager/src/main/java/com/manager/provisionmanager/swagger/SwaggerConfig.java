/**
 * Copyright 2020 MapleLabs 
 * 
 * Author: Anchal Gupta (Anchal.Gupta@maplelabs.com)
 * 
 * Description: Swagger Configuration.
 **/

package com.manager.provisionmanager.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket postsApi() {
    return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").apiInfo(apiInfo()).select().paths(postPaths())
      .build();
  }

  private Predicate<String> postPaths() {
    return Predicates.and(PathSelectors.regex("/api/.*"), Predicates.not(PathSelectors.regex("/error.*")));
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Provisioner API").description("Provisioner API reference for developers.")
      .termsOfServiceUrl("http://www.maplelabs.com").license("MapleLabs License").licenseUrl("http://www.maplelabs.com")
      .version("1.0").build();
  }

}
