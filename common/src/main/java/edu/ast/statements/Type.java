package edu.ast.statements;

import edu.exceptions.UnsupportedTypeOperationException;

public enum Type {
  NUMBER,
  STRING,
  BOOLEAN;

  public static Type combine(Type left, Type right) {
    if (left == BOOLEAN || right == BOOLEAN) {
      throw new UnsupportedTypeOperationException(left, right);
    }
    return left == NUMBER && right == NUMBER ? NUMBER : STRING;
  }
}
