package edu.utils;

import edu.Operator;

public class OperatorExecutor {
  private static final BinaryOperations binaryOperations = new BinaryOperations();

  public static Object execute(Operator operator, Object left, Object right) {
    switch (operator) {
      case SUM:
        return binaryOperations.sum(left, right);
      case SUBTRACT:
        return binaryOperations.subtract(left, right);
      case MULTIPLY:
        return binaryOperations.multiply(left, right);
      case DIVIDE:
        return binaryOperations.divide(left, right);
      default:
        throw new RuntimeException("Operador no soportado: " + operator);
    }
  }
}
