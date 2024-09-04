package edu;

import edu.handlers.HandlerRegistry;
import edu.handlers.HandlerRegistryV1;
import edu.handlers.HandlerRegistryV2;
import edu.handlers.expressions.InputProvider;
import edu.rules.RuleProvider;
import edu.rules.RuleProviderV1;
import edu.rules.RuleProviderV2;

public class InterpreterFactory {

  public static Interpreter createInterpreterV1(Parser parser) {
    RuleProvider ruleProvider = new RuleProviderV1();
    HandlerRegistry handlerRegistry = new HandlerRegistryV1(ruleProvider);
    return new Interpreter(handlerRegistry, parser);
  }

  public static Interpreter createInterpreterV2(Parser parser, InputProvider inputProvider) {
    RuleProvider ruleProvider = new RuleProviderV2();
    HandlerRegistry handlerRegistry = new HandlerRegistryV2(ruleProvider, inputProvider);
    return new Interpreter(handlerRegistry, parser);
  }
}
