package edu.exceptions;

public class InvalidFunctionRule extends IllegalArgumentException {
  public InvalidFunctionRule(String rule) {
    super("Invalid function rule: " + rule);
  }
}
