package edu.exceptions;

import edu.LexicalRange;
import edu.ast.statements.Type;

public class InvalidTypeException extends RuntimeException {

  public InvalidTypeException(String type, LexicalRange lexicalRange) {
    super("Invalid type " + type + " at " + lexicalRange.toString());
  }

  public InvalidTypeException(Type type, LexicalRange lexicalRange) {
    super("Invalid type " + type.toString() + " at " + lexicalRange.toString());
  }
}
