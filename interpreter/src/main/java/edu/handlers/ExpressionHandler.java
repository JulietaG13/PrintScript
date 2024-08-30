package edu.handlers;

import edu.ast.interfaces.ExpressionNode;
import edu.reader.InterpreterReader;

public interface ExpressionHandler {
  Object handle(ExpressionNode node, InterpreterReader reader);
}
