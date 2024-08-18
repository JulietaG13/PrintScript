package edu.functions;

import edu.ast.expressions.CallExpressionNode;

import java.util.List;
import java.util.ArrayList;

public class And implements FunctionRule {
  private final List<FunctionRule> rules;

  public And(List<FunctionRule> rules) {
    this.rules = rules;
  }

  @Override
  public boolean matches(CallExpressionNode function) {
    for (FunctionRule rule : rules) {
      if (!rule.matches(function)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String getErrorMessage(CallExpressionNode function) {
    List<String> errorMessages = new ArrayList<>();

    for (FunctionRule rule : rules) {
      if (!rule.matches(function)) {
        errorMessages.add(rule.getErrorMessage(function));
      }
    }

    return String.join("\n", errorMessages);
  }
}
