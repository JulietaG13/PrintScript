package edu;


import edu.ast.*;

public class Interpreter {
  private final ExecutionVisitor visitor;

  public Interpreter() {
    this.visitor = new ExecutionVisitor(new Reader(new VariableContext()));
  }

  public void interpret(ProgramNode program) {
    program.accept(visitor);
  }

  public ExecutionVisitor getVisitor() {
    return visitor;
  }

}
