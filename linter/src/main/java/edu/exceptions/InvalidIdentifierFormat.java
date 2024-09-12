package edu.exceptions;

public class InvalidIdentifierFormat extends IllegalArgumentException {
  public InvalidIdentifierFormat(String format) {
    super("Invalid identifier format: " + format);
  }
}
