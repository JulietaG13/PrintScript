package edu.handlers;

import edu.ast.interfaces.StatementNode;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.utils.HandlerResult;

public interface StatementHandler {
  HandlerResult handle(
      StatementNode node, InterpreterReader interpreterReader, Inventory inventory);

  Class<? extends StatementNode> getHandledNodeClass();
}
