package edu.identifiers;

import java.util.List;

public class Or implements IdentifierType {
  private final List<IdentifierType> identifierTypes;

  public Or(List<IdentifierType> identifierTypes) {
    this.identifierTypes = identifierTypes;
  }

  @Override
  public boolean matches(String identifier) {
    return identifierTypes.stream().anyMatch(type -> type.matches(identifier));
  }
}
