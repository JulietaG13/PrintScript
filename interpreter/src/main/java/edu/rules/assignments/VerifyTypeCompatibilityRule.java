package edu.rules.assignments;

import edu.ast.interfaces.StatementNode;
import edu.context.VariableContext;
import edu.exceptions.TypeMismatchException;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.Rule;

public class VerifyTypeCompatibilityRule implements Rule {

  @Override
  public boolean apply(
      StatementNode node, InterpreterReader interpreterReader, Inventory inventory) {
    String varName = interpreterReader.getIdentifier().getValue().toString();
    Object value = interpreterReader.getLiteral().getValue();

    VariableContext variableContext = inventory.getVariableContext();

    boolean result = isCompatible(varName, value, variableContext);
    if (!result) {
      throw new TypeMismatchException(
          varName, getExpectedType(varName, variableContext), value.getClass().getSimpleName());
    }
    return result;
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
