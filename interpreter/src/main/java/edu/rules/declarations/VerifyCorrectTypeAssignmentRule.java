package edu.rules.declarations;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.Rule;
import java.math.BigDecimal;

public class VerifyCorrectTypeAssignmentRule implements Rule {

  @Override
  public boolean apply(
      StatementNode node, InterpreterReader interpreterReader, Inventory inventory) {
    if (!(node instanceof VariableDeclarationNode)) {
      throw new IllegalArgumentException("Node is not of type VariableDeclarationNode");
    }

    VariableDeclarationNode varNode = (VariableDeclarationNode) node;
    String varName = interpreterReader.getIdentifier().getValue().toString();
    if (varNode.init() == null) {
      return true;
    }
    Object value = interpreterReader.getLiteral().getValue();

    boolean result;
    switch (varNode.type()) {
      case NUMBER:
        result = value instanceof BigDecimal;
        break;
      case STRING:
        result = value instanceof String;
        break;
      case BOOLEAN:
        result = value instanceof Boolean;
        break;
      default:
        result = false;
    }

    if (!result) {
      throw new RuntimeException(
          "Type mismatch: Expected type '" + varNode.type() + "' for variable '" + varName + "'");
    }

    return result;
  }
}
