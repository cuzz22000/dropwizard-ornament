package io.dropwizard.ornament;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class ServiceConfiguration extends Configuration {

  private final SwaggerBundleConfiguration swaggerBundleConfiguration;
  private final String configuredProperty;
  private final String authenticationToken;

  @JsonCreator
  public ServiceConfiguration(
      @JsonProperty(SWAGGER) final SwaggerBundleConfiguration swaggerBundleConfiguration,
      @JsonProperty(CONFIGURED_PROPERTY_NAME) final String configuredProperty,
      @JsonProperty(AUTH_TOKEN) final String authenticationToken) {
    this.swaggerBundleConfiguration = swaggerBundleConfiguration;
    this.configuredProperty = configuredProperty;
    this.authenticationToken = authenticationToken;
  }

  @JsonProperty(SWAGGER)
  public SwaggerBundleConfiguration swaggerBundleConfiguration() {
    return this.swaggerBundleConfiguration;
  }

  @JsonProperty(CONFIGURED_PROPERTY_NAME)
  public String configuredProperty() {
    return this.configuredProperty;
  }

  @JsonProperty(AUTH_TOKEN)
  public String authenticationToken() {
    return this.authenticationToken;
  }

  private static final String AUTH_TOKEN = "authenticationToken";
  private static final String SWAGGER = "swagger";
  private static final String CONFIGURED_PROPERTY_NAME = "configuredProperty";

}
