package edu.handlers;

import edu.ast.interfaces.ExpressionNode;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.utils.HandlerResult;

public interface ExpressionHandler {
  HandlerResult handle(ExpressionNode node, InterpreterReader reader, Inventory inventory);

  String getHandledCallee();
}
