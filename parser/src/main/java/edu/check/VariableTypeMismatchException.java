package edu.check;

import edu.ast.statements.Type;

public class VariableTypeMismatchException extends RuntimeException {
  public VariableTypeMismatchException(String name, Type expected, Type actual) {
    super("Variable " + name + " of type " + expected + " does not match " + actual);
  }
}
