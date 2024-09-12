package edu.exceptions;

public class ConstantAssignmentException extends RuntimeException {
  public ConstantAssignmentException(String variableName) {
    super("Cannot assign to a constant variable: " + variableName);
  }
}
