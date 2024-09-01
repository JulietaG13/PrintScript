package edu;

import edu.commands.AnalyzerCommand;
import edu.commands.Command;
import edu.commands.ExecutionCommand;
import edu.commands.FormattingCommand;
import edu.commands.ValidatorCommand;
import edu.utils.CommandContext;
import edu.utils.VersionFactory;

import java.io.IOException;

public class Runner {
  public final VersionFactory versionFactory;
  public final CommandContext commandContext;
  public final String version;

  public Runner(String version) {
    this.version = version;
    this.versionFactory = new VersionFactory(version);
    this.commandContext = new CommandContext();
  }

  public void validate(String sourceFile) throws IOException {
    Command validatorCommand = new ValidatorCommand(sourceFile, version);
    validatorCommand.run();
  }

  public void execute(String sourceFile) throws IOException {
    Command executionCommand = new ExecutionCommand(sourceFile, version);
    executionCommand.run();
  }

  public void format(String sourceFile, String configFile) throws IOException {
    Command formattingCommand = new FormattingCommand(sourceFile, version, configFile);
    formattingCommand.run();
  }

  public void analyze(String sourceFile, String configFile) throws IOException {
    Command analyzerCommand = new AnalyzerCommand(sourceFile, version, configFile);
    analyzerCommand.run();
  }
}
