package edu.check;

import edu.ast.expressions.IdentifierNode;

public class VariableAlreadyDeclaredException extends RuntimeException {
  public VariableAlreadyDeclaredException(IdentifierNode identifier) {
    super("Variable " + identifier.name() + " at " + identifier.start() + " already declared");
  }
}
