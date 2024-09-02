package edu.handlers;

import edu.handlers.expressions.PrintExpressionHandler;
import edu.handlers.expressions.ReadEnvExpressionHandler;
import edu.handlers.expressions.ReadInputExpressionHandler;
import edu.handlers.statements.AssignmentHandler;
import edu.handlers.statements.IfStatementHandler;
import edu.handlers.statements.declaration.ConstantDeclarationHandler;
import edu.handlers.statements.declaration.VariableDeclarationHandler;
import edu.rules.Rule;
import edu.rules.RuleProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerRegistryV2 implements HandlerRegistry {
  private final Map<String, StatementHandler> statementHandlers = new HashMap<>();
  private final Map<String, ExpressionHandler> expressionHandlers = new HashMap<>();

  List<Rule> rulesDecl;
  List<Rule> rulesAss;

  public HandlerRegistryV2(RuleProvider ruleProvider) {
    this.rulesDecl = ruleProvider.getDeclarationRules();
    this.rulesAss = ruleProvider.getAssignmentRules();

    statementHandlers.put(
        "let", new VariableDeclarationHandler(ruleProvider.getDeclarationRules()));
    statementHandlers.put("assignation", new AssignmentHandler(ruleProvider.getAssignmentRules()));
    statementHandlers.put("if", new IfStatementHandler());
    statementHandlers.put(
        "const", new ConstantDeclarationHandler(ruleProvider.getDeclarationRules()));

    expressionHandlers.put("println", new PrintExpressionHandler());
    expressionHandlers.put("readInput", new ReadInputExpressionHandler());
    expressionHandlers.put("readEnv", new ReadEnvExpressionHandler());
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
