package edu;

import edu.handlers.ExpressionHandler;
import edu.handlers.HandlerRegistry;
import edu.handlers.StatementHandler;
import edu.handlers.expressions.PrintExpressionHandler;
import edu.handlers.expressions.ReadEnvExpressionHandler;
import edu.handlers.expressions.ReadInputExpressionHandler;
import edu.handlers.statements.AssignmentHandler;
import edu.handlers.statements.declaration.ConstantDeclarationHandler;
import edu.handlers.statements.declaration.VariableDeclarationHandler;
import edu.rules.RuleProvider;
import edu.rules.RuleProviderV1;
import edu.rules.RuleProviderV2;
import java.util.HashMap;
import java.util.Map;

public class InterpreterFactory {

  public static Interpreter createInterpreterV1(Parser parser, PrintEmitter printEmitter) {
    RuleProvider ruleProvider = new RuleProviderV1();
    HandlerRegistry handlerRegistry = createHandlerRegistryV1(ruleProvider, printEmitter);
    return new Interpreter(handlerRegistry, parser);
  }

  public static Interpreter createInterpreterV2(
      Parser parser, InputProvider inputProvider, PrintEmitter printEmitter) {
    RuleProvider ruleProvider = new RuleProviderV2();
    HandlerRegistry handlerRegistry =
        createHandlerRegistryV2(ruleProvider, inputProvider, printEmitter);
    return new Interpreter(handlerRegistry, parser);
  }

  public static HandlerRegistry createHandlerRegistryV2(
      RuleProvider ruleProvider, InputProvider inputProvider, PrintEmitter printEmitter) {
    Map<String, StatementHandler> statementHandlers = new HashMap<>();
    Map<String, ExpressionHandler> expressionHandlers = new HashMap<>();
    statementHandlers.put(
        "let", new VariableDeclarationHandler(ruleProvider.getDeclarationRules()));
    statementHandlers.put("assignation", new AssignmentHandler(ruleProvider.getAssignmentRules()));
    statementHandlers.put(
        "const", new ConstantDeclarationHandler(ruleProvider.getDeclarationRules()));
    expressionHandlers.put("println", new PrintExpressionHandler(printEmitter));
    expressionHandlers.put("readInput", new ReadInputExpressionHandler(inputProvider));
    expressionHandlers.put("readEnv", new ReadEnvExpressionHandler());
    return new HandlerRegistry(statementHandlers, expressionHandlers);
  }

  public static HandlerRegistry createHandlerRegistryV1(
      RuleProvider ruleProvider, PrintEmitter printEmitter) {
    Map<String, StatementHandler> statementHandlers = new HashMap<>();
    Map<String, ExpressionHandler> expressionHandlers = new HashMap<>();
    statementHandlers.put(
        "let", new VariableDeclarationHandler(ruleProvider.getDeclarationRules()));
    statementHandlers.put("assignation", new AssignmentHandler(ruleProvider.getAssignmentRules()));
    expressionHandlers.put("println", new PrintExpressionHandler(printEmitter));
    return new HandlerRegistry(statementHandlers, expressionHandlers);
  }
}
