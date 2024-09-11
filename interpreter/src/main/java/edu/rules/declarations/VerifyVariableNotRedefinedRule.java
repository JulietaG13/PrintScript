package edu.rules.declarations;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.Rule;

public class VerifyVariableNotRedefinedRule implements Rule {

  @Override
  public boolean apply(
      StatementNode node, InterpreterReader interpreterReader, Inventory inventory) {
    if (!(node instanceof VariableDeclarationNode)) {
      throw new IllegalArgumentException("Node is not of type VariableDeclarationNode");
    }

    String varName = interpreterReader.getIdentifier().getValue().toString();
    VariableContext variableContext = inventory.getVariableContext();

    boolean result =
        !variableContext.hasNumberVariable(varName)
            && !variableContext.hasStringVariable(varName)
            && !variableContext.hasBooleanVariable(varName);

    if (!result) {
      throw new RuntimeException(
          "Variable '" + varName + "' is already defined in the current context.");
    }

    return result;
  }
}
