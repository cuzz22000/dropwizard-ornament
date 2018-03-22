package io.dropwizard.ornament.health;

import com.codahale.metrics.health.HealthCheck;



public class ServiceHeath extends HealthCheck {

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }

}
