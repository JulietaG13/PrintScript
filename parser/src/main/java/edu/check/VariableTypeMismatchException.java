package edu.check;

import edu.LexicalRange;
import edu.ast.statements.Type;

public class VariableTypeMismatchException extends RuntimeException {
  public VariableTypeMismatchException(
      String name, Type expected, Type actual, LexicalRange range) {
    super(
        "Variable "
            + name
            + " of type "
            + expected
            + " does not match "
            + actual
            + " at "
            + range.toString());
  }
}
