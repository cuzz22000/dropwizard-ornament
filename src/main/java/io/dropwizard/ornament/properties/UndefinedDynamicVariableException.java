package io.dropwizard.ornament.properties;

public class UndefinedDynamicVariableException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public UndefinedDynamicVariableException(final String errorMessage) {
    super(errorMessage);
  }
}
