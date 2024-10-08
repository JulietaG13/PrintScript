package edu.handlers.expressions;

import edu.ast.expressions.CallExpressionNode;
import edu.ast.interfaces.ExpressionNode;
import edu.handlers.ExpressionHandler;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.reader.ReaderResult;
import edu.utils.HandlerResult;

public class ReadEnvExpressionHandler implements ExpressionHandler {
  @Override
  public HandlerResult handle(ExpressionNode node, InterpreterReader reader, Inventory inventory) {
    CallExpressionNode callNode = (CallExpressionNode) node;
    ReaderResult envVarNameResult = reader.read(inventory);
    reader = envVarNameResult.getReader();
    String envVarName = envVarNameResult.getValue().toString();
    String envValue = System.getenv(envVarName);

    if (envValue == null) {
      throw new RuntimeException("Environment variable not found: " + envVarName);
    }

    Object typedValue = determineInputType(envValue);

    reader = reader.addLiteral(typedValue);

    return new HandlerResult(reader, inventory);
  }

  @Override
  public String getHandledCallee() {
    return "readEnv";
  }

  private Object determineInputType(String input) {
    try {
      return Double.parseDouble(input);
    } catch (NumberFormatException e1) {
      if ("true".equalsIgnoreCase(input) || "false".equalsIgnoreCase(input)) {
        return Boolean.parseBoolean(input);
      } else {
        return input;
      }
    }
  }
}
