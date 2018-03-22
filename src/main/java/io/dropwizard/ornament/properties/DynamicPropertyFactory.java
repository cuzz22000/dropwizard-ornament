package io.dropwizard.ornament.properties;

import java.util.Objects;
import java.util.stream.Stream;

import com.netflix.archaius.DefaultConfigLoader;
import com.netflix.archaius.DefaultPropertyFactory;
import com.netflix.archaius.api.Config;
import com.netflix.archaius.api.PropertyFactory;
import com.netflix.archaius.api.config.CompositeConfig;
import com.netflix.archaius.api.exceptions.ConfigException;
import com.netflix.archaius.cascade.ConcatCascadeStrategy;
import com.netflix.archaius.config.EnvironmentConfig;
import com.netflix.archaius.config.MapConfig;
import com.netflix.archaius.config.SystemConfig;

public final class DynamicPropertyFactory {

  public static PropertyFactory propertyFactory(final String applicationName) {
    try {
      final String environmentName = environmentName();

      final Config context = MapConfig.builder().put("environment", environmentName).build();

      final DefaultConfigLoader loader =
          DefaultConfigLoader.builder().withStrLookup(context).build();

      final CompositeConfig config =
          loader.newLoader().withCascadeStrategy(ConcatCascadeStrategy.from("${environment}"))
              .load(applicationName);

      config.addConfig("context", context);
      config.addConfig("system", EnvironmentConfig.INSTANCE);
      config.addConfig("env", EnvironmentConfig.INSTANCE);

      final PropertyFactory propertyFactory = DefaultPropertyFactory.from(config);
      return propertyFactory;
    } catch (final ConfigException ex) {
      throw new LoadConfigurationException(ex);
    }
  }

  private static String environmentName() {
    final Config sysConfig = SystemConfig.INSTANCE;
    // only environment is accepted
    final String sysEnvironment = sysConfig.getString("ENVIRONMENT", null);
    final Config envConfig = EnvironmentConfig.INSTANCE;
    final String envEnvironment = envConfig.getString("ENVIRONMENT", null);

    final String environment =
        Stream.of(sysEnvironment, envEnvironment, "LOCAL").filter(Objects::nonNull).findFirst()
            .get().toLowerCase();

    // some people like to type "production"
    if (environment.equalsIgnoreCase("production")) {
      return "prod";
    }

    System.out.println("EnvironmentName : " + environment);

    return environment;
  }

}
