package edu.rules.declarations;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.ConstantContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.Rule;
import edu.rules.RuleResult;

public class VerifyConstantNotRedefinedRule implements Rule {

  @Override
  public RuleResult apply(
      StatementNode node, InterpreterReader interpreterReader, Inventory inventory) {
    if (!(node instanceof VariableDeclarationNode)) {
      throw new IllegalArgumentException("Node is not of type VariableDeclarationNode");
    }

    VariableDeclarationNode varNode = (VariableDeclarationNode) node;
    String varName = interpreterReader.getIdentifier().getValue().toString();
    ConstantContext constantContext = inventory.getConstantContext();

    boolean result = !constantContext.hasConstant(varName);
    if (!result) {
      throw new RuntimeException(
          "Constant '" + varName + "' is already defined in the current context.");
    }

    return new RuleResult(result, interpreterReader, inventory);
  }
}
