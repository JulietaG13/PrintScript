package edu;

import edu.ast.interfaces.Node;
import edu.rules.FormatterRuleProvider;

public class Formatter {
  private final FormatterRuleProvider ruleProvider;
  private final Parser parser;

  public Formatter(FormatterRuleProvider ruleProvider, Parser parser) {
    this.ruleProvider = ruleProvider;
    this.parser = parser;
  }

  public FormatterResult format() {
    FormatterVisitor visitor = new FormatterVisitor(ruleProvider);
    while (parser.hasNext()) {
      Node node = parser.next();
      node.accept(visitor);
    }
    return visitor.getResult();
  }
}
