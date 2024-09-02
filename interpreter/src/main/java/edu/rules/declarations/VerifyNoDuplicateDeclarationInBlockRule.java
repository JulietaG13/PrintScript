package edu.rules.declarations;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.TemporalContext;
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

    VariableDeclarationNode varNode = (VariableDeclarationNode) node;
    String varName = interpreterReader.getIdentifier().getValue().toString();

    TemporalContext temporalContext = inventory.getTemporaryContext();
    VariableContext variableContext = inventory.getVariableContext();

    boolean isDuplicateInBlock = temporalContext != null && temporalContext.hasValue(varName);
    boolean isDuplicateInContext =
        variableContext.hasNumberVariable(varName)
            || variableContext.hasStringVariable(varName)
            || variableContext.hasBooleanVariable(varName);

    boolean result = !isDuplicateInBlock && !isDuplicateInContext;

    if (!result) {
      throw new RuntimeException(
          "Variable or constant '" + varName + "' is already defined in this block or context.");
    }

    return new RuleResult(result, interpreterReader, inventory);
  }
}
