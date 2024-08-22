package edu;

import edu.commands.Command;
import edu.utils.CommandContext;
import edu.utils.CommandProvider;

public class Cli {
  // TODO: hacerlo un main
  private String[] args;
  private CommandContext commandContext;

  public Cli(String[] args) {
    this.args = args;
    this.commandContext = new CommandContext();
  }

  public void run() {
    Command command = getCommand();
    if (command == null) {
      System.out.println("Unknown operation: " + args[0]);
      return;
    }
    command.execute();
  }

  private Command getCommand() {
    String commandString = args[0];
    String filePath = args[1];
    String version = args[2];
    String additionalArg = args.length > 3 ? args[3] : null;
    CommandProvider commandProvider =
        new CommandProvider(commandString, filePath, additionalArg, commandContext);
    return commandProvider.getCommand(commandString);
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
