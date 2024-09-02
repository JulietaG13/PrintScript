package edu.rules;

import edu.ast.interfaces.StatementNode;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;

public interface Rule {
  RuleResult apply(StatementNode node, InterpreterReader interpreterReader, Inventory inventory);
}
