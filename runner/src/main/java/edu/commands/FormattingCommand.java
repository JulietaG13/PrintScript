package edu.commands;

import com.google.gson.JsonObject;
import edu.Formatter;
import edu.FormatterResult;
import edu.ast.ProgramNode;
import edu.rules.FormatterRuleParser;
import edu.utils.CommandContext;
import edu.utils.ProgramNodeUtil;
import edu.utils.VersionFactory;
import java.util.Iterator;

public class FormattingCommand implements Command {
  private final CommandContext commandContext = new CommandContext();
  private final Iterator<String> fileReader;
  private final String version;
  private final JsonObject config;
  private final VersionFactory versionFactory;

  public FormattingCommand(Iterator<String> fileReader, String version, JsonObject config) {
    this.fileReader = fileReader;
    this.version = version;
    this.config = config;
    this.versionFactory = new VersionFactory(version);
  }

  public void run() {
    System.out.println("Reading file");
    ProgramNode programNode = ProgramNodeUtil.getProgramNode(fileReader, version);
    System.out.println("Parsing completed");
    Formatter formatter = versionFactory.createFormatter(FormatterRuleParser.parseRules(config));
    System.out.println("Formatting program");
    FormatterResult result = formatter.format(programNode);
    System.out.println("Formatting completed");
    commandContext.setFormatterResult(result);
    System.out.println("Formatted code" + result.getResult());
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
