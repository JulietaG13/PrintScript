package edu.commands;

import edu.utils.CommandContext;

public interface Command {
  void run();

  CommandContext getCommandContext();
}
