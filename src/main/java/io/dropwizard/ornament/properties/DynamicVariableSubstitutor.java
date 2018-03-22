package io.dropwizard.ornament.properties;

import org.apache.commons.lang3.text.StrSubstitutor;

public class DynamicVariableSubstitutor extends StrSubstitutor {

  public DynamicVariableSubstitutor(final String applicationName, final boolean strict,
      final boolean substitutionInVariables) {
    super(
        new DynamicVariableLookup(DynamicPropertyFactory.propertyFactory(applicationName), strict));
    this.setEnableSubstitutionInVariables(substitutionInVariables);
  }

}
