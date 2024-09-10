package edu.check;

import edu.ast.interfaces.Node;

public class InvalidIfConditionException extends RuntimeException {
  public InvalidIfConditionException(Node node) {
    super("Invalid If Condition [" + node.start() + "]");
  }
}
