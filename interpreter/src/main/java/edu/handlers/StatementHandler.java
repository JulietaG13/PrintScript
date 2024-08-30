package edu.handlers;

import edu.ast.interfaces.StatementNode;
import edu.reader.InterpreterReader;

public interface StatementHandler {
  Object handle(StatementNode node, InterpreterReader interpreterReader);
}
