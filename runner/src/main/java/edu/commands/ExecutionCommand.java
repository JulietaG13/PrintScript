package edu.commands;

import edu.Interpreter;
import edu.Parser;
import edu.utils.CommandContext;
import edu.utils.ProgramNodeUtil;
import edu.utils.VersionFactory;
import java.io.IOException;

public class ExecutionCommand implements Command {
  private final CommandContext commandContext = new CommandContext();
  private final String sourceFile;
  private final String version;
  private final VersionFactory versionFactory;

  public ExecutionCommand(String sourceFile, String version) {
    this.sourceFile = sourceFile;
    this.version = version;
    this.versionFactory = new VersionFactory(version);
  }

  public void run() throws IOException {
    Parser parser = ProgramNodeUtil.getParser(sourceFile, version);
    Interpreter interpreter = versionFactory.createInterpreter(parser);
    interpreter.interpret();
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
