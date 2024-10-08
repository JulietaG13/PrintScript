package edu.utils;

import edu.Operator;
import java.util.HashMap;
import java.util.Map;

public class OperatorProvider {
  private static final Map<String, Operator> operators = new HashMap<>();

  static {
    operators.put("+", Operator.SUM);
    operators.put("-", Operator.SUBTRACT);
    operators.put("*", Operator.MULTIPLY);
    operators.put("/", Operator.DIVIDE);
  }

  public static Operator getOperator(String operatorName) {
    if (operators.containsKey(operatorName)) {
      return operators.get(operatorName);
    }
    throw new IllegalArgumentException("Unknown operator: " + operatorName);
  }
}
