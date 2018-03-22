package io.dropwizard.ornament.sample;


import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import io.dropwizard.jackson.Jackson;
import io.dropwizard.ornament.sample.SampleEntity;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SampleEntityTest {

  @Test
  public void toJson() throws JsonProcessingException {
    final SampleEntity sampleEntity = new SampleEntity("john", 123456789);
    String actual = MAPPER.writeValueAsString(sampleEntity);
    String expected = fixture("fixtures/sample-entity.json");
    assertThat(actual, equalTo(expected));
  }

  @Test
  public void fromJson() throws IOException {
    final String json = fixture("fixtures/sample-entity.json");
    final SampleEntity actual = MAPPER.readValue(json, SampleEntity.class);
    final SampleEntity expected = new SampleEntity("john", 123456789);
    assertThat(actual, equalTo(expected));
  }

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

}
