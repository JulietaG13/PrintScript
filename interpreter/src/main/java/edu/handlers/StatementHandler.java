package edu.handlers;

import edu.ast.interfaces.StatementNode;
import edu.exceptions.RuleFailedException;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.utils.HandlerResult;

public interface StatementHandler {
  HandlerResult handle(StatementNode node, InterpreterReader interpreterReader, Inventory inventory)
      throws RuleFailedException;

  Class<? extends StatementNode> getHandledNodeClass();
}
