package edu.exceptions;

import edu.tokens.Token;

public class UnexpectedTokenException extends RuntimeException {

  public UnexpectedTokenException(Token actual) {
    super("Unexpected token: [" + actual.getContent() + "] at " + actual.getStart());
  }

  public UnexpectedTokenException(Token actual, String expected) {
    super(
        "Unexpected token: ["
            + actual.getContent()
            + "]"
            + " at "
            + actual.getStart()
            + ", expected: ["
            + expected
            + "]");
  }
}
