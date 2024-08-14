package edu.common;

import static edu.common.Operator.*;

import java.util.HashMap;
import java.util.Map;

public class OperatorProvider {
  private static final Map<String, Operator> operators = new HashMap<>();
  private static final Map<Operator, String> strings = new HashMap<>();

  static {
    add(SUM.symbol, SUM);
    add(SUBTRACT.symbol, SUBTRACT);
    add(MULTIPLY.symbol, MULTIPLY);
    add(DIVIDE.symbol, DIVIDE);
  }

  public static Operator getOperator(String name) {
    return operators.get(name);
  }

  public static String getName(Operator operator) {
    return strings.get(operator);
  }

  private static void add(String name, Operator operator) {
    operators.put(name, operator);
    strings.put(operator, name);
  }
}
