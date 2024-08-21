package edu.utils;

import edu.commands.AnalyzerCommand;
import edu.commands.Command;
import edu.commands.ExecutionCommand;
import edu.commands.FormattingCommand;
import edu.commands.ValidatorCommand;
import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
  Map<String, Command> commandsMap = new HashMap<>();

  public CommandProvider(
      String operationName, String sourceFile, String args, CommandContext commandContext) {
    commandsMap.put("Validation", new ValidatorCommand(sourceFile, commandContext));
    commandsMap.put("Execution", new ExecutionCommand(sourceFile, commandContext));
    commandsMap.put("Formatting", new FormattingCommand(commandContext, sourceFile, args));
    commandsMap.put("Analyzing", new AnalyzerCommand(sourceFile, commandContext, args));
  }

  public Command getCommand(String operationName) {
    return commandsMap.get(operationName);
  }
}
