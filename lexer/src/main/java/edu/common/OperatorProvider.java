package edu.common;

import java.util.HashMap;
import java.util.Map;

import static edu.common.Operator.*;

public class OperatorProvider {
  private final static Map<String, Operator> operators = new HashMap<>();
  private final static Map<Operator, String> strings = new HashMap<>();

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
