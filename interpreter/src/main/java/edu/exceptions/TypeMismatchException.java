package edu.exceptions;

public class TypeMismatchException extends RuntimeException {
  public TypeMismatchException(String varName, String expectedType, String actualType) {
    super(
        "Type mismatch for variable '"
            + varName
            + "': expected "
            + expectedType
            + " but found "
            + actualType);
  }
}
