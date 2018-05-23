package io.dropwizard.ornament.sample;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import org.glassfish.jersey.servlet.ServletProperties;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jersey.DropwizardResourceConfig;
import io.dropwizard.ornament.ServiceConfiguration;

public abstract class BaseSampleResourceTest extends JerseyTest {

  @Override
  protected TestContainerFactory getTestContainerFactory() {
    return new GrizzlyWebTestContainerFactory();
  }

  @Override
  protected DeploymentContext configureDeployment() {
    return ServletDeploymentContext.builder(new TestResourceConfig())
        .initParam(ServletProperties.JAXRS_APPLICATION_CLASS, TestResourceConfig.class.getName())
        .build();
  }

  protected static class TestResourceConfig extends DropwizardResourceConfig {

    public TestResourceConfig() {
      super(true, new MetricRegistry());

      final ObjectMapper mapper = Jackson.newObjectMapper(new YAMLFactory());
      final String ymlConfig = fixture("fixtures/test-configuration.yml");

      try {
        configuration = mapper.readValue(ymlConfig, ServiceConfiguration.class);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      register(new AuthDynamicFeature(new OAuthCredentialAuthFilter.Builder<Principal>()
          .setAuthenticator(new TestAuthenticator()).setPrefix("Bearer").buildAuthFilter()));
      register(new AuthValueFactoryProvider.Binder<>(Principal.class));
      register(new SampleResource(configuration));

    }

  }

  public static class TestAuthenticator implements Authenticator<String, Principal> {

    @Override
    public Optional<Principal> authenticate(String credentials) throws AuthenticationException {
      if (credentials.equals(AUTH_KEY))
        return Optional.of(new Principal() {

          @Override
          public String getName() {
            return "My Name is What!";
          }
        });
      return Optional.empty();

    }

    protected static final String AUTH_KEY = "foobar";
  }

  protected static ServiceConfiguration configuration;
}
