package edu.exceptions;

import edu.ast.statements.Type;

public class UnsupportedTypeOperationException extends RuntimeException {
  public UnsupportedTypeOperationException(Type left, Type right) {
    super("Cannot combine " + left + " and " + right);
  }
}
