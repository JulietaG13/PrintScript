package edu.identifiers;

public class SnakeCase implements IdentifierType {
  @Override
  public boolean matches(String identifier) {
    return combinesLowerCaseAndUnderscores(identifier) && nonContinuousUnderscores(identifier);
  }

  private static boolean combinesLowerCaseAndUnderscores(String identifier) {
    return identifier.matches("^[a-z]+(_[a-z0-9]+)*$");
  }

  private static boolean nonContinuousUnderscores(String identifier) {
    return !identifier.contains("__");
  }
}
