package edu.identifiers;

public class LowerCamelCase implements IdentifierType {
  @Override
  public boolean matches(String identifier) {
    return combinesLowerAndUpperCases(identifier) && nonContinuousUppercase(identifier);
  }

  private static boolean combinesLowerAndUpperCases(String identifier) {
    return identifier.matches("^[a-z]+([A-Z][a-z]+)*$");
  }

  private static boolean nonContinuousUppercase(String identifier) {
    return !identifier.matches(".*[A-Z]{2,}.*");
  }
}
