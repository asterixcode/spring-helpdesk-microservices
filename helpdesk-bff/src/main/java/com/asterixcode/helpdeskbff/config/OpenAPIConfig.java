package com.asterixcode.helpdeskbff.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

  @Bean
  public OpenAPI customOpenAPI(
      @Value("${springdoc.open-api.info.title}") String title,
      @Value("${springdoc.open-api.info.description}") String description,
      @Value("${springdoc.open-api.info.version}") String version) {
    return new OpenAPI()
        .info(new Info().title(title).description(description).version(version))
        .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
        .components(
            new Components()
                .addSecuritySchemes(
                    "bearer-jwt",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
  }
}
