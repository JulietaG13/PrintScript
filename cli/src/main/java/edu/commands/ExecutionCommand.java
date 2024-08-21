package edu.commands;

import edu.Interpreter;
import edu.ast.ProgramNode;
import edu.utils.CommandContext;
import edu.utils.ProgramNodeUtils;

public class ExecutionCommand implements Command {
  private CommandContext commandContext;
  private String sourceFile;

  public ExecutionCommand(String sourceFile, CommandContext commandContext) {
    this.commandContext = commandContext;
    this.sourceFile = sourceFile;
  }

  @Override
  public void execute() {
    ProgramNode programNode = ProgramNodeUtils.getOrCreateProgramNode(commandContext, sourceFile);
    Interpreter interpreter = new Interpreter();
    interpreter.interpret(programNode);
  }
}
