package io.dropwizard.ornament;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.ornament.health.ServiceHeath;
import io.dropwizard.ornament.sample.SampleResource;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class ServiceApplication extends Application<ServiceConfiguration> {

  @Override
  public String getName() {
    return "dropwizard-ornament";
  }

  @Override
  public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {

    // dropwizard-swagger
    bootstrap.addBundle(new SwaggerBundle<ServiceConfiguration>() {
      @Override
      protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
          ServiceConfiguration configuration) {
        return configuration.swaggerBundleConfiguration();
      }
    });

  }

  @Override
  public void run(ServiceConfiguration configuration, Environment environment) throws Exception {
    LOG.info("Service Name : {}", System.getenv("APPLICATION"));
    LOG.info("Environment : {}", System.getenv("ENVIRONMENT"));
    // default health check
    environment.healthChecks().register(getName() + " HealthCheck", new ServiceHeath());
    environment.jersey().register(new ServiceHeath());
    // sample resource
    environment.jersey().register(new SampleResource(configuration));
    // token authenticator
    environment.jersey()
        .register(new AuthDynamicFeature(new OAuthCredentialAuthFilter.Builder<Principal>()
            .setAuthenticator(new TokenAuthentication(configuration.authenticationToken()))
            .setPrefix("Bearer").buildAuthFilter()));
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(Principal.class));

  }

  public static void main(final String[] args) throws Exception {
    new ServiceApplication().run(args);
  }

  private static final Logger LOG = LoggerFactory.getLogger(ServiceApplication.class);
}
