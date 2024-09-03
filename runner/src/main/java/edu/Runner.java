package edu;

import edu.commands.AnalyzerCommand;
import edu.commands.Command;
import edu.commands.ExecutionCommand;
import edu.commands.FormattingCommand;
import edu.commands.ValidatorCommand;
import edu.utils.CommandContext;
import edu.utils.VersionFactory;
import java.io.IOException;
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

  public void validate(Iterator<String> fileReader) throws IOException {
    Command validatorCommand = new ValidatorCommand(fileReader, version);
    validatorCommand.run();
  }

  public void execute(Iterator<String> fileReader) throws IOException {
    Command executionCommand = new ExecutionCommand(fileReader, version);
    executionCommand.run();
  }

  public void format(Iterator<String> fileReader, String configFile) throws IOException {
    Command formattingCommand = new FormattingCommand(fileReader, version, configFile);
    formattingCommand.run();
  }

  public void analyze(Iterator<String> fileReader, String configFile) throws IOException {
    Command analyzerCommand = new AnalyzerCommand(fileReader, version, configFile);
    analyzerCommand.run();
  }
}
