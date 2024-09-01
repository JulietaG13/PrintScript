package edu;

import edu.rules.RuleProviderLinter;

public class Linter {
  private final RuleProviderLinter ruleProvider;
  private final Parser parser;

  public Linter(RuleProviderLinter ruleProvider, Parser parser) {
    this.ruleProvider = ruleProvider;
    this.parser = parser;
  }

  public Report analyze() {
    Report report = new Report();
    StaticCodeAnalyzer analyzer = new StaticCodeAnalyzer(report, ruleProvider);
    while (parser.hasNext()) {
      analyzer.analyze(parser.next());
    }
    return report;
  }
}
