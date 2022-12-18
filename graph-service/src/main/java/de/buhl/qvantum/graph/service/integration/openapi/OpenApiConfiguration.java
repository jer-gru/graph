package de.buhl.qvantum.graph.service.integration.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OpenApiConfiguration {

  @Bean
  public OpenAPI openAPI(final Optional<BuildProperties> buildProperties) {
    return new OpenAPI()
      .info(new Info()
        .title("Test Task - Simple Graph API")
        .description("Allows to create a directed graph and performing topological sorting.")
        .contact(new Contact().name("Jeremy Grube"))
        .version(buildProperties
          .map(BuildProperties::getVersion)
          .orElse("0.0.1-DEV")));
  }

}
