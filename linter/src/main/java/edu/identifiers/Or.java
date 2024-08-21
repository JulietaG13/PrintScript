package edu.identifiers;

import java.util.Set;

public class Or implements IdentifierType {
  private final Set<IdentifierType> identifierTypes;

  public Or(Set<IdentifierType> identifierTypes) {
    this.identifierTypes = identifierTypes;
  }

  @Override
  public boolean matches(String identifier) {
    return identifierTypes.stream().anyMatch(type -> type.matches(identifier));
  }
}
