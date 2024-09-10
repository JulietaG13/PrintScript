package edu.exceptions;

import edu.tokens.Token;

public class InvalidStatementException extends RuntimeException {
  public InvalidStatementException(Token start, Token end) {
    super("Invalid statement from " + start.getStart() + " to " + end.getEnd());
  }
}
