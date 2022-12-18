package de.buhl.qvantum.graph.service.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BackendResources   {

  public static final String[] OPENAPI = {
    "/v3/api-docs",
    "/v3/api-docs.yml"
  };
  public static final String[] SWAGGER_UI = {
    "/swagger-ui.html",
    "/swagger-ui/*",
    "/v3/api-docs/swagger-config"
  };
  public static final String[] SPRING_ACTUATOR = {
    "/actuator/**"
  };

}
