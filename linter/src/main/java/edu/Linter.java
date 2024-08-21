package edu;

import edu.ast.ProgramNode;
import edu.rules.RuleProvider;

public class Linter {
  private final RuleProvider ruleProvider;

  public Linter(RuleProvider ruleProvider) {
    this.ruleProvider = ruleProvider;
  }

  public Report analyze(ProgramNode programNode) {
    Report report = new Report();
    StaticCodeAnalyzer analyzer = new StaticCodeAnalyzer(report, ruleProvider);
    analyzer.visit(programNode);
    return report;
  }

}
