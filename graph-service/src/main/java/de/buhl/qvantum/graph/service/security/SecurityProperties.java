package de.buhl.qvantum.graph.service.security;

import lombok.Data;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Value
@Validated
@ConstructorBinding
@ConfigurationProperties("buhl.security")
public class SecurityProperties {

  @NotNull
  Health health;
  @NotNull
  Info info;

  @Data
  @Validated
  public static class Health {
    @NotNull
    private List<String> allowedIpRanges;
  }

  @Data
  @Validated
  public static class Info {
    @NotNull
    private List<String> allowedIpRanges;
  }

}
