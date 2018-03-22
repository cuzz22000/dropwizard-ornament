package io.dropwizard.ornament.properties;

import static com.google.common.base.Preconditions.checkNotNull;

import java.text.MessageFormat;

import org.apache.commons.lang3.text.StrLookup;

import com.netflix.archaius.api.Property;
import com.netflix.archaius.api.PropertyContainer;
import com.netflix.archaius.api.PropertyFactory;

public class DynamicVariableLookup extends StrLookup<Object> {
  private final PropertyFactory propertyFactory;
  private final boolean strict;

  public DynamicVariableLookup(final PropertyFactory propertyFactory, boolean strict) {
    checkNotNull(propertyFactory, "propertyFactory cannot be null");

    this.propertyFactory = propertyFactory;
    this.strict = strict;
  }

  @Override
  public String lookup(final String key) {
    final PropertyContainer propertyContainer = propertyFactory.getProperty(key);
    final Property<String> property = propertyContainer.asString(null);
    final String value = property.get();

    if (value == null && strict) {
      final String message =
          MessageFormat
              .format(
                  "The dynamic variable \"{0}\" is not defined; could not substitute the expression \"$'{'{0}'}'\"",
                  key);
      throw new UndefinedDynamicVariableException(message);
    }

    return value;
  }
}
