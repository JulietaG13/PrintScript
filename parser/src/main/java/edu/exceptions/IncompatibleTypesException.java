package edu.exceptions;

import edu.LexicalRange;

public class IncompatibleTypesException extends RuntimeException {
  public IncompatibleTypesException(String type, LexicalRange lexicalRange) {
    super("Incompatible type at " + lexicalRange.toString() + " expected " + type);
  }
}
