package edu.commands;

import com.google.gson.JsonObject;
import edu.Formatter;
import edu.FormatterResult;
import edu.ast.ProgramNode;
import edu.rules.FormatterRuleParser;
import edu.rules.FormatterRuleProvider;
import edu.utils.CommandContext;
import edu.utils.JsonConfigLoader;
import edu.utils.ProgramNodeUtil;
import edu.utils.VersionFactory;
import java.io.IOException;
import java.util.Iterator;

public class FormattingCommand implements Command {
  private final CommandContext commandContext = new CommandContext();
  private final Iterator<String> fileReader;
  private final String version;
  private final String configFile;
  private final VersionFactory versionFactory;

  public FormattingCommand(Iterator<String> fileReader, String version, String configFile) {
    this.fileReader = fileReader;
    this.version = version;
    this.configFile = configFile;
    this.versionFactory = new VersionFactory(version);
  }

  public void run() throws IOException {
    System.out.println("Reading file");
    ProgramNode programNode = ProgramNodeUtil.getProgramNode(fileReader, version);
    System.out.println("Parsing completed");
    FormatterRuleProvider rules = getRules(configFile);
    Formatter formatter = versionFactory.createFormatter(rules);
    System.out.println("Formatting program");
    FormatterResult result = formatter.format(programNode);
    System.out.println("Formatting completed");
    commandContext.setFormatterResult(result);
    System.out.println("Formatted code" + result.getResult());
  }

  private FormatterRuleProvider getRules(String configFile) throws IOException {
    JsonObject config = JsonConfigLoader.loadFromFile(configFile);
    return FormatterRuleParser.parseRules(config);
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
