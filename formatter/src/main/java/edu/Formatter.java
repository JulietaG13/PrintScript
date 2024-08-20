package edu;

import edu.ast.ProgramNode;
import edu.rules.RuleProvider;

public class Formatter {
  private final RuleProvider ruleProvider;

  public Formatter(RuleProvider ruleProvider) {
    this.ruleProvider = ruleProvider;
  }

  public FormatterResult format(ProgramNode programNode) {
    FormatterVisitor visitor = new FormatterVisitor(ruleProvider);
    programNode.accept(visitor);
    return visitor.getResult();
  }
}
