package edu.commands;

import static edu.progress.ProgressInputStreamWrapper.setProgress;

import com.google.gson.JsonObject;
import edu.Formatter;
import edu.FormatterResult;
import edu.Lexer;
import edu.Parser;
import edu.rules.FormatterRuleParser;
import edu.utils.CommandContext;
import edu.utils.VersionFactory;
import java.io.InputStream;

public class FormattingCommand implements Command {
  private final CommandContext commandContext = new CommandContext();
  private final InputStream inputStream;
  private final String version;
  private final JsonObject config;
  private final VersionFactory versionFactory;

  public FormattingCommand(InputStream inputStream, String version, JsonObject config) {
    this.inputStream = inputStream;
    this.version = version;
    this.config = config;
    this.versionFactory = new VersionFactory(version);
  }

  public void run() {
    InputStream input = setProgress(inputStream);
    Lexer lexer = versionFactory.createLexer(input);
    Parser parser = versionFactory.createParser(lexer);
    Formatter formatter =
        versionFactory.createFormatter(FormatterRuleParser.parseRules(config), parser);
    FormatterResult result = formatter.format();
    commandContext.setFormatterResult(result);
    System.out.println("Formatted code" + result.getResult());
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
