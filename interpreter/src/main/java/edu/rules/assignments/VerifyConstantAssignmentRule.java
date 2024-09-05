package edu.rules.assignments;

import edu.ast.interfaces.StatementNode;
import edu.context.ConstantContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.Rule;
import edu.rules.RuleResult;

public class VerifyConstantAssignmentRule implements Rule {
  @Override
  public RuleResult apply(
      StatementNode node, InterpreterReader interpreterReader, Inventory inventory) {

    String varName = interpreterReader.getIdentifier().getValue().toString();
    ConstantContext constantContext = inventory.getConstantContext();

    boolean result = !constantContext.hasConstant(varName);
    if (!result) {
      throw new RuntimeException("Cannot assign to a constant variable: " + varName);
    }

    return new RuleResult(result, interpreterReader, inventory);
  }
}
