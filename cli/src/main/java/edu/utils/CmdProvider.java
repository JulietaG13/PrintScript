package edu.utils;

import com.github.ajalt.clikt.core.CliktCommand;
import edu.commands.AnalyzerCmd;
import edu.commands.ExecutionCmd;
import edu.commands.FormattingCmd;
import edu.commands.ValidatorCmd;
import java.util.HashMap;
import java.util.Map;

public class CmdProvider {
  Map<String, CliktCommand> commandsMap = new HashMap<>();

  public CmdProvider() {
    commandsMap.put("Validation", new ValidatorCmd());
    commandsMap.put("Execution", new ExecutionCmd());
    commandsMap.put("Formatting", new FormattingCmd());
    commandsMap.put("Analyzing", new AnalyzerCmd());
  }

  public CliktCommand getCommand(String operationName) {
    return commandsMap.get(operationName);
  }
}
