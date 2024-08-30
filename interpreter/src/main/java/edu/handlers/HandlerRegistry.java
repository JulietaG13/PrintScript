package edu.handlers;

import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.AssignmentNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.handlers.expressions.BinaryExpressionHandler;
import edu.handlers.expressions.CallExpressionHandler;
import edu.handlers.expressions.IdentifierHandler;
import edu.handlers.expressions.LiteralNumberHandler;
import edu.handlers.expressions.LiteralStringHandler;
import edu.handlers.statements.AssignmentHandler;
import edu.handlers.statements.ExpressionStatementHandler;
import edu.handlers.statements.VariableDeclarationHandler;
import java.util.HashMap;
import java.util.Map;

public class HandlerRegistry {
  private static final Map<Class<? extends StatementNode>, StatementHandler> statementHandlers =
      new HashMap<>();
  private static final Map<Class<? extends ExpressionNode>, ExpressionHandler> expressionHandlers =
      new HashMap<>();

  static {
    // Register default statement handlers
    registerStatementHandler(VariableDeclarationNode.class, new VariableDeclarationHandler());
    registerStatementHandler(AssignmentNode.class, new AssignmentHandler());
    registerStatementHandler(ExpressionStatementNode.class, new ExpressionStatementHandler());

    // Register default expression handlers
    registerExpressionHandler(BinaryExpressionNode.class, new BinaryExpressionHandler());
    registerExpressionHandler(CallExpressionNode.class, new CallExpressionHandler());
    registerExpressionHandler(IdentifierNode.class, new IdentifierHandler());
    registerExpressionHandler(LiteralNumberNode.class, new LiteralNumberHandler());
    registerExpressionHandler(LiteralStringNode.class, new LiteralStringHandler());
  }

  public static void registerStatementHandler(
      Class<? extends StatementNode> clazz, StatementHandler handler) {
    statementHandlers.put(clazz, handler);
  }

  public static void registerExpressionHandler(
      Class<? extends ExpressionNode> clazz, ExpressionHandler handler) {
    expressionHandlers.put(clazz, handler);
  }

  public static StatementHandler getStatementHandler(StatementNode node) {
    return statementHandlers.get(node.getClass());
  }

  public static ExpressionHandler getExpressionHandler(ExpressionNode node) {
    return expressionHandlers.get(node.getClass());
  }
}
