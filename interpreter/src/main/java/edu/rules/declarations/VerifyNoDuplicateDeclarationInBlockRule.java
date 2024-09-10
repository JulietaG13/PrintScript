package edu.rules.declarations;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.Rule;
import edu.rules.RuleResult;

public class VerifyNoDuplicateDeclarationInBlockRule implements Rule {

  @Override
  public RuleResult apply(
      StatementNode node, InterpreterReader interpreterReader, Inventory inventory) {
    if (!(node instanceof VariableDeclarationNode)) {
      throw new IllegalArgumentException("Node is not of type VariableDeclarationNode");
    }

    String varName = interpreterReader.getIdentifier().getValue().toString();

    VariableContext variableContext = inventory.getVariableContext();

    boolean isDuplicateInContext =
        variableContext.hasNumberVariable(varName)
            || variableContext.hasStringVariable(varName)
            || variableContext.hasBooleanVariable(varName);

    boolean result = !isDuplicateInContext;

    if (!result) {
      throw new RuntimeException(
          "Variable or constant '" + varName + "' is already defined in this block or context.");
    }

    return new RuleResult(result, interpreterReader, inventory);
  }
}
