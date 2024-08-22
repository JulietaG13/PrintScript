package edu.functions;

import edu.ast.expressions.CallExpressionNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class And implements FunctionRule {
  private final Set<FunctionRule> rules;

  public And(Set<FunctionRule> rules) {
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
