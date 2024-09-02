package edu.handlers.statements;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.IfStatementNode;
import edu.context.TemporalContext;
import edu.context.VariableContext;
import edu.handlers.StatementHandler;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.utils.HandlerResult;

public class IfStatementHandler implements StatementHandler {

  @Override
  public HandlerResult handle(
      StatementNode node, InterpreterReader interpreterReader, Inventory inventory) {
    if (!(node instanceof IfStatementNode)) {
      throw new IllegalArgumentException("Invalid node type for IfStatementHandler");
    }

    IfStatementNode ifNode = (IfStatementNode) node;

    VariableContext updatedVariableContext =
        mergeTemporaryContext(inventory.getTemporaryContext(), inventory.getVariableContext());

    inventory = inventory.setVariableContext(updatedVariableContext);
    inventory = inventory.removeTemporaryContext();

    return new HandlerResult(interpreterReader, inventory);
  }

  @Override
  public Class<? extends StatementNode> getHandledNodeClass() {
    return IfStatementNode.class;
  }

  private VariableContext mergeTemporaryContext(
      TemporalContext temporalContext, VariableContext variableContext) {
    for (var entry : temporalContext.getAllValues().entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();

      if (variableContext.hasNumberVariable(key)) {
        if (value instanceof Number) {
          variableContext = variableContext.setNumberVariable(key, (Number) value);
        } else {
          throw new RuntimeException(
              "Type mismatch: Expected a Number for variable '"
                  + key
                  + "', but got "
                  + value.getClass().getSimpleName());
        }
      } else if (variableContext.hasStringVariable(key)) {
        if (value instanceof String) {
          variableContext = variableContext.setStringVariable(key, (String) value);
        } else {
          throw new RuntimeException(
              "Type mismatch: Expected a String for variable '"
                  + key
                  + "', but got "
                  + value.getClass().getSimpleName());
        }
      } else if (variableContext.hasBooleanVariable(key)) {
        if (value instanceof Boolean) {
          variableContext = variableContext.setBooleanVariable(key, (Boolean) value);
        } else {
          throw new RuntimeException(
              "Type mismatch: Expected a Boolean for variable '"
                  + key
                  + "', but got "
                  + value.getClass().getSimpleName());
        }
      }
    }
    return variableContext;
  }
}
