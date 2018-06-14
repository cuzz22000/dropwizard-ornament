package io.dropwizard.ornament;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class ServiceConfiguration extends Configuration {

  private final SwaggerBundleConfiguration swaggerBundleConfiguration;
  private final String authenticationToken;
  private final String environment;

  @JsonCreator
  public ServiceConfiguration(
      @JsonProperty(SWAGGER) final SwaggerBundleConfiguration swaggerBundleConfiguration,
      @JsonProperty(AUTH_TOKEN) final String authenticationToken,
      @JsonProperty(ENVIRONMENT) final String environment) {
    this.swaggerBundleConfiguration = swaggerBundleConfiguration;
    this.authenticationToken = authenticationToken;
    this.environment = environment;
  }

  @JsonProperty(SWAGGER)
  public SwaggerBundleConfiguration swaggerBundleConfiguration() {
    return this.swaggerBundleConfiguration;
  }


  @JsonProperty(AUTH_TOKEN)
  public String authenticationToken() {
    return this.authenticationToken;
  }

  @JsonProperty(ENVIRONMENT)
  public String environment() {
    return this.environment;
  }

  private static final String ENVIRONMENT = "environment";
  private static final String AUTH_TOKEN = "authenticationToken";
  private static final String SWAGGER = "swagger";

}
