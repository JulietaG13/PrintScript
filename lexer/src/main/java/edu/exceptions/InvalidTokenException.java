package edu.exceptions;

import edu.LexicalRange;

public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException(String actual, LexicalRange lexicalRange) {
    super("Unexpected token: " + actual + " at " + lexicalRange.toString());
  }
}
