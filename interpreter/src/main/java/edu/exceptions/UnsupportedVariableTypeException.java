package edu.exceptions;

public class UnsupportedVariableTypeException extends RuntimeException {
  public UnsupportedVariableTypeException(Class<?> type) {
    super("Unsupported variable type: " + type.getSimpleName());
  }
}
