package edu.commands;

import static edu.progress.ProgressInputStreamWrapper.setProgress;

import edu.InputProvider;
import edu.Interpreter;
import edu.Parser;
import edu.PrintEmitter;
import edu.utils.CommandContext;
import edu.utils.ProgramNodeUtil;
import edu.utils.VersionFactory;
import java.io.InputStream;

public class ExecutionCommand implements Command {
  private final CommandContext commandContext = new CommandContext();
  private final InputStream inputStream;
  private final String version;
  private final VersionFactory versionFactory;
  private final InputProvider inputProvider;
  private final PrintEmitter printEmitter;

  public ExecutionCommand(
      InputStream inputStream,
      String version,
      InputProvider inputProvider,
      PrintEmitter printEmitter) {
    this.inputStream = inputStream;
    this.version = version;
    this.versionFactory = new VersionFactory(version);
    this.inputProvider = inputProvider;
    this.printEmitter = printEmitter;
  }

  public void run() {
    try {
      InputStream input = setProgress(inputStream);
      Parser parser = ProgramNodeUtil.getParser(input, version);
      Interpreter interpreter =
          versionFactory.createInterpreter(parser, inputProvider, printEmitter);
      interpreter.interpret();
    } catch (Exception e) {
      commandContext.setHasError(true);
      throw new RuntimeException("Error during execution: " + e.getMessage(), e);
    }
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
