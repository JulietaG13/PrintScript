package edu.exceptions;

import edu.LexicalRange;

public class AssignmentException extends RuntimeException {
  public AssignmentException(String varName, String message, LexicalRange start, LexicalRange end) {
    super(
        "Variable assignation error at '"
            + varName
            + "': "
            + message
            + " between "
            + start.toString()
            + " and "
            + end.toString());
  }
}
