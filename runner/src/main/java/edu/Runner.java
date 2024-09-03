package edu;

import com.google.gson.JsonObject;
import edu.commands.AnalyzerCommand;
import edu.commands.Command;
import edu.commands.ExecutionCommand;
import edu.commands.FormattingCommand;
import edu.commands.ValidatorCommand;
import edu.utils.CommandContext;
import edu.utils.VersionFactory;
import java.util.Iterator;

public class Runner {
  public final VersionFactory versionFactory;
  public final CommandContext commandContext;
  public final String version;

  public Runner(String version) {
    this.version = version;
    this.versionFactory = new VersionFactory(version);
    this.commandContext = new CommandContext();
  }

  public void validate(Iterator<String> fileReader) {
    Command validatorCommand = new ValidatorCommand(fileReader, version);
    validatorCommand.run();
  }

  public void execute(Iterator<String> fileReader) {
    Command executionCommand = new ExecutionCommand(fileReader, version);
    executionCommand.run();
  }

  public void format(Iterator<String> fileReader, JsonObject rules) {
    Command formattingCommand = new FormattingCommand(fileReader, version, rules);
    formattingCommand.run();
  }

  public void analyze(Iterator<String> fileReader, JsonObject config) {
    Command analyzerCommand = new AnalyzerCommand(fileReader, version, config);
    analyzerCommand.run();
  }
}
