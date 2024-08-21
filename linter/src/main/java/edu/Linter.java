package edu;

import edu.ast.ProgramNode;
import edu.rules.RuleProviderLinter;

public class Linter {
  private final RuleProviderLinter ruleProvider;

  public Linter(RuleProviderLinter ruleProvider) {
    this.ruleProvider = ruleProvider;
  }

  public Report analyze(ProgramNode programNode) {
    Report report = new Report();
    StaticCodeAnalyzer analyzer = new StaticCodeAnalyzer(report, ruleProvider);
    analyzer.visit(programNode);
    return report;
  }

}
