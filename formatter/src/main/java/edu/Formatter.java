package edu;

import edu.ast.ProgramNode;
import edu.rules.FormatterRuleProvider;

public class Formatter {
  private final FormatterRuleProvider ruleProvider;

  public Formatter(FormatterRuleProvider ruleProvider) {
    this.ruleProvider = ruleProvider;
  }

  public FormatterResult format(ProgramNode programNode) {
    FormatterVisitor visitor = new FormatterVisitor(ruleProvider);
    programNode.accept(visitor);
    return visitor.getResult();
  }
}
