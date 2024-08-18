package edu.identifiers;

public class UpperCamelCase implements IdentifierType {
  @Override
  public boolean matches(String identifier) {
    return combinesUpperAndLowerCases(identifier) && nonContinuousUppercase(identifier);
  }

  private static boolean combinesUpperAndLowerCases(String identifier) {
    return identifier.matches("^[A-Z][a-z]+([A-Z][a-z]+)*$");
  }

  private static boolean nonContinuousUppercase(String identifier) {
    return !identifier.matches(".*[A-Z]{2,}.*");
  }
}
