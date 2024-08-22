package edu.commands;

import com.google.gson.JsonObject;
import edu.Formatter;
import edu.FormatterResult;
import edu.ast.ProgramNode;
import edu.rules.FormatterRuleParser;
import edu.rules.FormatterRuleProvider;
import edu.utils.CommandContext;
import edu.utils.JsonConfigLoader;
import edu.utils.ProgramNodeUtils;

public class FormattingCommand implements Command {
  private CommandContext commandContext;
  private String sourcePath;
  private String configPath;

  public FormattingCommand(CommandContext commanContext, String sourcePath, String configPath) {
    this.commandContext = commanContext;
    this.sourcePath = sourcePath;
    this.configPath = configPath;
  }

  @Override
  public void execute() {
    ProgramNode programNode = ProgramNodeUtils.getOrCreateProgramNode(commandContext, sourcePath);
    Formatter formatter = getFormatter();
    FormatterResult result = formatter.format(programNode);
    commandContext.setFormatterResult(result);
  }

  private Formatter getFormatter() {
    return new Formatter(getRules());
  }

  private FormatterRuleProvider getRules() {
    JsonObject config = JsonConfigLoader.loadFromString(configPath);
    System.out.println("Config JSON: " + config.toString());
    FormatterRuleProvider ruleProvider = FormatterRuleParser.parseRules(config);
    return ruleProvider;
  }
}
