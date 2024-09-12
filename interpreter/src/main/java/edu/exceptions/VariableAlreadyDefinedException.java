package edu.exceptions;

public class VariableAlreadyDefinedException extends RuntimeException {
  public VariableAlreadyDefinedException(String varName) {
    super("Variable '" + varName + "' is already defined in the current context.");
  }
}
