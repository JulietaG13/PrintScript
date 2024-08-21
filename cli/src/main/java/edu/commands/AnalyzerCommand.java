package edu.commands;

import com.google.gson.JsonObject;
import edu.Linter;
import edu.Report;
import edu.ast.ProgramNode;
import edu.rules.RuleParserLinter;
import edu.rules.RuleProviderLinter;
import edu.utils.CommandContext;
import edu.utils.JsonConfigLoader;
import edu.utils.ProgramNodeUtils;

public class AnalyzerCommand implements Command {
  private String sourceFile;
  private CommandContext commandContext;
  private String rules;

  public AnalyzerCommand(String sourceFile, CommandContext commandContext, String rules) {
    this.sourceFile = sourceFile;
    this.commandContext = commandContext;
    this.rules = rules;
  }

  @Override
  public void execute() {
    ProgramNode programNode = ProgramNodeUtils.getOrCreateProgramNode(commandContext, sourceFile);
    Linter linter = getLinter();
    Report report = linter.analyze(programNode);
    commandContext.setLinterReport(report);

    System.out.println("Análisis completado. Reporte: " + report);
  }

  private Linter getLinter() {
    return new Linter(getRules());
  }

  private RuleProviderLinter getRules() {
    JsonObject config = JsonConfigLoader.loadFromString(rules);
    RuleProviderLinter ruleProvider = RuleParserLinter.parseRules(config);
    return ruleProvider;
  }
}
