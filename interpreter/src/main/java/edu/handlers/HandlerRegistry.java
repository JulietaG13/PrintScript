package edu.handlers;

public interface HandlerRegistry {
  StatementHandler getStatementHandler(String nodeClass);

  ExpressionHandler getExpressionHandler(String callee);
}
