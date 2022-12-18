package de.buhl.qvantum.graph.service.security;

import de.buhl.qvantum.graph.service.integration.openapi.ApiVersion;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
public class WebSecurityConfiguration {

  @NonNull
  private final SecurityProperties securityProperties;

  @Bean
  public SecurityFilterChain filterChain(@NonNull final HttpSecurity http) throws Exception {
    return http
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authorizeRequests()
      .antMatchers("/").permitAll()
      .antMatchers(BackendResources.OPENAPI).permitAll()
      .antMatchers(BackendResources.SWAGGER_UI).permitAll()
      .antMatchers(BackendResources.SPRING_ACTUATOR).permitAll()
      .antMatchers(HttpMethod.PUT, ApiVersion.V1 + "/graph/**").permitAll()
      .requestMatchers(EndpointRequest.to(HealthEndpoint.class))
      .access(createHasIpRangeExpression(securityProperties.getHealth().getAllowedIpRanges()))
      .requestMatchers(EndpointRequest.to(InfoEndpoint.class))
      .access(createHasIpRangeExpression(securityProperties.getInfo().getAllowedIpRanges()))
      .anyRequest().permitAll()
      .and()
      .httpBasic()
      .and()
      .headers().frameOptions().sameOrigin()
      .and()
      .cors()
      .and()
      .csrf().disable()
      .build();
  }

  private String createHasIpRangeExpression(final List<String> ipRanges) {
    return ipRanges.stream().collect(
      Collectors.joining("') or hasIpAddress('", "hasIpAddress('", "')")
    );
  }

}
