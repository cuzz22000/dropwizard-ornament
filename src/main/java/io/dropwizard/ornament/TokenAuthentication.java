package io.dropwizard.ornament;

import java.security.Principal;
import java.util.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class TokenAuthentication implements Authenticator<String, Principal> {

  private final String authToken;

  public TokenAuthentication(final String authToken) {
    this.authToken = authToken;
  }

  @Override
  public Optional<Principal> authenticate(String credentials) throws AuthenticationException {
    if (credentials.equals(this.authToken)) {
      return Optional.of(new Principal() {

        @Override
        public String getName() {
          return authToken;
        }

      });
    }
    return Optional.empty();
  }
}
