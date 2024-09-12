package edu.rules.declarations;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.VariableContext;
import edu.exceptions.TypeMismatchException;
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
      throw new TypeMismatchException(
          varName,
          getExpectedType(varName, inventory.getVariableContext()),
          value.getClass().getSimpleName());
    }

    return result;
  }

  private String getExpectedType(String varName, VariableContext variableContext) {
    if (variableContext.hasNumberVariable(varName)) {
      return "Number";
    } else if (variableContext.hasStringVariable(varName)) {
      return "String";
    } else if (variableContext.hasBooleanVariable(varName)) {
      return "Boolean";
    }
    return "Unknown";
  }
}
