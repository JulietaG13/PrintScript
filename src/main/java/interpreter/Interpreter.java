package interpreter;

import ast.*;

public class Interpreter {
  private final ExecutionVisitor visitor;

  public Interpreter() {
    this.visitor = new ExecutionVisitor();
  }

  public void interpret(ProgramNode program) {
    //program.accept(visitor);
  }

  public ExecutionVisitor getVisitor() {
    return visitor;
  }

}
