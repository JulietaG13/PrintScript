package edu.exceptions;

public class VariableNotFoundException extends RuntimeException {
  public VariableNotFoundException(String variableName, String variableType) {
    super("Variable of type '" + variableType + "' not found: " + variableName);
  }
}
