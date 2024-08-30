package edu.functions;

import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.LiteralNode;

public class NonExpression implements FunctionRule {
  private final String functionName;

  public NonExpression(String function) {
    this.functionName = function;
  }

  @Override
  public boolean matches(CallExpressionNode function) {
    if (isFunctionType(function)) {
      for (ExpressionNode arg : function.args()) {
        if (isExpression(arg)) {
          return false;
        }
      }
      return true;
    }
    return true;
  }

  private static boolean isExpression(ExpressionNode arg) {
    return !(arg instanceof IdentifierNode || arg instanceof LiteralNode);
  }

  private boolean isFunctionType(CallExpressionNode function) {
    return functionName.equals(function.callee().name());
  }

  @Override
  public String getErrorMessage(CallExpressionNode function) {
    StringBuilder errorMessage = new StringBuilder();
    errorMessage.append(
        "Error in println function: "
            + "The "
            + functionName
            + " function only accepts identifiers or literals as arguments.");
    for (int i = 0; i < function.args().size(); i++) {
      ExpressionNode arg = function.args().get(i);
      if (isExpression(arg)) {
        addArgumentToError(errorMessage, i, arg);
      }
    }
    return errorMessage.toString();
  }

  private void addArgumentToError(StringBuilder errorMessage, int i, ExpressionNode arg) {
    errorMessage
        .append("\n")
        .append("Argument ")
        .append(i + 1)
        .append(" is invalid:")
        .append("\n - Type: ")
        .append(arg.getClass().getSimpleName())
        .append("\n - Position: ")
        .append(arg.start().toString())
        .append("\n - Content: ")
        .append(formatArgumentContent(arg));
  }

  private String formatArgumentContent(ExpressionNode arg) {
    if (arg instanceof BinaryExpressionNode binary) {
      String leftContent = simplifyExpressionContent(binary.left());
      String rightContent = simplifyExpressionContent(binary.right());
      return leftContent + " " + binary.operator() + " " + rightContent;
    } else if (arg instanceof CallExpressionNode) {
      CallExpressionNode call = (CallExpressionNode) arg;
      String calleeName = call.callee().name();
      String arguments =
          call.args().stream()
              .map(this::simplifyExpressionContent)
              .reduce((a, b) -> a + ", " + b)
              .orElse("");
      return calleeName + "(" + arguments + ")";
    }
    return arg.toString();
  }

  private String simplifyExpressionContent(ExpressionNode expression) {
    if (expression instanceof LiteralStringNode) {
      LiteralStringNode literal = (LiteralStringNode) expression;
      return "\"" + literal.value() + "\"";
    }
    if (expression instanceof IdentifierNode) {
      IdentifierNode identifier = (IdentifierNode) expression;
      return identifier.name();
    }
    if (expression instanceof CallExpressionNode) {
      return formatArgumentContent(expression);
    }
    return expression.toString();
  }
}
