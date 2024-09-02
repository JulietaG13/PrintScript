package edu.handlers;

import edu.handlers.expressions.PrintExpressionHandler;
import edu.handlers.statements.AssignmentHandler;
import edu.handlers.statements.declaration.VariableDeclarationHandler;
import edu.rules.RuleProvider;
import java.util.HashMap;
import java.util.Map;

public class HandlerRegistryV1 implements HandlerRegistry {
  private final Map<String, StatementHandler> statementHandlers = new HashMap<>();
  private final Map<String, ExpressionHandler> expressionHandlers = new HashMap<>();

  public HandlerRegistryV1(RuleProvider ruleProvider) {
    statementHandlers.put(
        "let", new VariableDeclarationHandler(ruleProvider.getDeclarationRules()));
    statementHandlers.put("assignation", new AssignmentHandler(ruleProvider.getAssignmentRules()));
    expressionHandlers.put("println", new PrintExpressionHandler());
  }

  @Override
  public StatementHandler getStatementHandler(String nodeClass) {
    return statementHandlers.get(nodeClass);
  }

  @Override
  public ExpressionHandler getExpressionHandler(String callee) {
    return expressionHandlers.get(callee);
  }
}
