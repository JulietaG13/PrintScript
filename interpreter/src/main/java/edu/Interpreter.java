package edu;

import edu.ast.*;

public class Interpreter {
  private final ExecutionVisitor visitor;

  public Interpreter() {
    this.visitor =
        new ExecutionVisitor(
            new Reader(
                new VariableContext(new java.util.HashMap<>(), new java.util.HashMap<>()),
                new java.util.Stack<>(),
                new java.util.Stack<>()));
  }

  public void interpret(ProgramNode program) {
    program.accept(visitor);
  }

  public ExecutionVisitor getVisitor() {
    return visitor;
  }
}
