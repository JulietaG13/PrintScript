package edu.commands;

import edu.Interpreter;
import edu.Parser;
import edu.handlers.expressions.InputProvider;
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

  public ExecutionCommand(InputStream inputStream, String version, InputProvider inputProvider) {
    this.inputStream = inputStream;
    this.version = version;
    this.versionFactory = new VersionFactory(version);
    this.inputProvider = inputProvider;
  }

  public void run() {
    Parser parser = ProgramNodeUtil.getParser(inputStream, version);
    Interpreter interpreter = versionFactory.createInterpreter(parser, inputProvider);
    interpreter.interpret();
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
