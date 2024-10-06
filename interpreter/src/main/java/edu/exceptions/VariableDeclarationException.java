package edu.exceptions;

import edu.LexicalRange;

public class VariableDeclarationException extends RuntimeException {
  public VariableDeclarationException(
      String varName, String message, LexicalRange start, LexicalRange end) {
    super(
        "Variable declaration error '"
            + varName
            + "': "
            + message
            + " at "
            + start.toString()
            + " to "
            + end.toString());
  }
}
