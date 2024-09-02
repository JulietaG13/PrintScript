package edu.rules.assignments;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.AssignmentNode;
import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.Rule;
import edu.rules.RuleResult;

public class VerifyTypeCompatibilityRule implements Rule {

  @Override
  public RuleResult apply(
      StatementNode node, InterpreterReader interpreterReader, Inventory inventory) {
    AssignmentNode assignmentNode = (AssignmentNode) node;

    String varName = interpreterReader.getIdentifier().getValue().toString();
    Object value = interpreterReader.getLiteral().getValue();

    VariableContext variableContext = inventory.getVariableContext();

    boolean result = isCompatible(varName, value, variableContext);
    if (!result) {
      throw new RuntimeException(
          "Invalid assignment: Type mismatch or undefined variable: " + varName);
    }

    return new RuleResult(result, interpreterReader, inventory);
  }

  private boolean isCompatible(String varName, Object value, VariableContext variableContext) {
    if (variableContext.hasNumberVariable(varName) && value instanceof Number) {
      return true;
    } else if (variableContext.hasStringVariable(varName) && value instanceof String) {
      return true;
    } else if (variableContext.hasBooleanVariable(varName) && value instanceof Boolean) {
      return true;
    }
    return false;
  }
}