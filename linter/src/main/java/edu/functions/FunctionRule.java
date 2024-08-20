package edu.functions;

import edu.ast.expressions.CallExpressionNode;

public interface FunctionRule {
  boolean matches(CallExpressionNode function);

  String getErrorMessage(CallExpressionNode function);
}
