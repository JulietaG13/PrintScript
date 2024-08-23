package edu;

public enum Operator {
  SUM("+", 1),
  SUBTRACT("-", 1),
  MULTIPLY("*", 2),
  DIVIDE("/", 2);

  Operator(String symbol, int priority) {
    this.symbol = symbol;
    this.priority = priority;
  }

  public final String symbol;
  public final int priority;
}
