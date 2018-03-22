package io.dropwizard.ornament.sample;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import java.io.IOException;

import org.glassfish.jersey.servlet.ServletProperties;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

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
        register(new SampleResource(configuration));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }

  }

  protected static ServiceConfiguration configuration;

}
