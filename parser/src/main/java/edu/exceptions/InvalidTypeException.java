package edu.exceptions;

import edu.ast.statements.Type;

public class InvalidTypeException extends RuntimeException {

  public InvalidTypeException(String type) {
    super("Invalid type " + type);
  }

  public InvalidTypeException(Type type) {
    super("Invalid type " + type.toString());
  }
}
