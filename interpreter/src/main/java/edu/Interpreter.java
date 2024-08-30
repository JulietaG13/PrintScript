package edu;

import edu.ast.ProgramNode;
import edu.reader.InterpreterReader;
import edu.visitor.ExecutionVisitor;
import java.util.HashSet;

public class Interpreter {
  private final ExecutionVisitor visitor;

  public Interpreter() {
    this.visitor =
        new ExecutionVisitor(
            new InterpreterReader(
                new VariableContext(
                    new java.util.HashMap<>(),
                    new java.util.HashMap<>(),
                    new java.util.HashMap<>(),
                    new HashSet<>()),
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
