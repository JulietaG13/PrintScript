package edu.handlers;

import java.util.Map;

public class HandlerRegistry {
  private final Map<String, StatementHandler> statementHandlers;
  private final Map<String, ExpressionHandler> expressionHandlers;

  public HandlerRegistry(
      Map<String, StatementHandler> statementHandlers,
      Map<String, ExpressionHandler> expressionHandlers) {
    this.statementHandlers = statementHandlers;
    this.expressionHandlers = expressionHandlers;
  }

  public StatementHandler getStatementHandler(String nodeClass) {
    return statementHandlers.get(nodeClass);
  }

  public ExpressionHandler getExpressionHandler(String callee) {
    return expressionHandlers.get(callee);
  }
}
