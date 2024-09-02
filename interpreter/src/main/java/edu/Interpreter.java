package edu;

import edu.ast.interfaces.StatementNode;
import edu.context.ConstantContext;
import edu.context.VariableContext;
import edu.handlers.HandlerRegistry;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.visitor.ExecutionVisitor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Interpreter {
  // Todo: apicar interpret a el parser iterator
  private final ExecutionVisitor visitor;
  Parser parser;

  public Interpreter(HandlerRegistry handlerRegistry, Parser parser) {
    this.visitor =
        new ExecutionVisitor(
            new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>()),
            new Inventory(
                new ArrayList<>(
                    Arrays.asList(
                        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>()),
                        new ConstantContext(new HashSet<>())))),
            handlerRegistry);
    this.parser = parser;
  }

  public void interpret() {
    while (parser.hasNext()) {
      StatementNode program = parser.next();
      program.accept(visitor);
    }
  }

  public ExecutionVisitor getVisitor() {
    return visitor;
  }
}
