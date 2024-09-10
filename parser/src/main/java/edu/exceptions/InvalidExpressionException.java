package edu.exceptions;

import edu.tokens.Token;

public class InvalidExpressionException extends RuntimeException {
  public InvalidExpressionException(Token start, Token end) {
    super("Invalid expression from " + start.getStart() + " to " + end.getEnd());
  }
}
