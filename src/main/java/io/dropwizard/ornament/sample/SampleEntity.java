package io.dropwizard.ornament.sample;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @author Federico Recio, Chris Wilson
 */
public class SampleEntity {

  private final String name;
  private final int value;

  @JsonCreator
  public SampleEntity(@JsonProperty(NAME) final String name, @JsonProperty(VALUE) final int value) {
    this.name = name;
    this.value = value;
  }

  @JsonProperty(NAME)
  public String name() {
    return name;
  }

  @JsonProperty(VALUE)
  public int value() {
    return value;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name(),value());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != SampleEntity.class) {
      return false;
    }
    final SampleEntity objSampleEntity = (SampleEntity) obj;
    return Objects.equal(objSampleEntity.name(), name())
        && Objects.equal(objSampleEntity.value(), value());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(SampleEntity.class).add(NAME, name())
        .add(VALUE, value()).toString();
  }

  private static final String NAME = "name";
  private static final String VALUE = "value";

}
