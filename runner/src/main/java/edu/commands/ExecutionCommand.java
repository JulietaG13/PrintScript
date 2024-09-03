package edu.commands;

import edu.Interpreter;
import edu.Parser;
import edu.utils.CommandContext;
import edu.utils.ProgramNodeUtil;
import edu.utils.VersionFactory;
import java.util.Iterator;

public class ExecutionCommand implements Command {
  private final CommandContext commandContext = new CommandContext();
  private final Iterator<String> fileReader;
  private final String version;
  private final VersionFactory versionFactory;

  public ExecutionCommand(Iterator<String> fileReader, String version) {
    this.fileReader = fileReader;
    this.version = version;
    this.versionFactory = new VersionFactory(version);
  }

  public void run() {
    Parser parser = ProgramNodeUtil.getParser(fileReader, version);
    Interpreter interpreter = versionFactory.createInterpreter(parser);
    interpreter.interpret();
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
