package edu;

import com.google.gson.JsonObject;
import edu.commands.AnalyzerCommand;
import edu.commands.Command;
import edu.commands.ExecutionCommand;
import edu.commands.FormattingCommand;
import edu.commands.ValidatorCommand;
import edu.utils.VersionFactory;
import java.io.InputStream;

public class Runner {
  public final VersionFactory versionFactory;
  public final String version;

  public Runner(String version) {
    this.version = version;
    this.versionFactory = new VersionFactory(version);
  }

  public void validate(InputStream inputStream) {
    Command validatorCommand = new ValidatorCommand(inputStream, version);
    validatorCommand.run();
  }

  public void execute(InputStream inputStream) {
    Command executionCommand = new ExecutionCommand(inputStream, version);
    executionCommand.run();
  }

  public FormatterResult format(InputStream inputStream, JsonObject rules) {
    Command formattingCommand = new FormattingCommand(inputStream, version, rules);
    formattingCommand.run();
    return formattingCommand.getCommandContext().getFormatterResult();
  }

  public Report analyze(InputStream inputStream, JsonObject config) {
    Command analyzerCommand = new AnalyzerCommand(inputStream, version, config);
    analyzerCommand.run();
    return analyzerCommand.getCommandContext().getLinterReport();
  }
}
