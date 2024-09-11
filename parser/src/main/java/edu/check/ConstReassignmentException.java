package edu.check;

import edu.ast.expressions.IdentifierNode;

public class ConstReassignmentException extends RuntimeException {
  public ConstReassignmentException(IdentifierNode identifier) {
    super("Constant " + identifier.name() + " at " + identifier.start() + " cannot be reassigned");
  }
}
