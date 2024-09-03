package edu.handlers.expressions;

import edu.ast.expressions.CallExpressionNode;
import edu.ast.interfaces.ExpressionNode;
import edu.handlers.ExpressionHandler;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.reader.ReaderResult;
import edu.utils.HandlerResult;

public class PrintExpressionHandler implements ExpressionHandler {
  @Override
  public HandlerResult handle(ExpressionNode node, InterpreterReader reader, Inventory inventory) {
    CallExpressionNode callNode = (CallExpressionNode) node;
    verifyPrint(callNode);
    ReaderResult result = reader.read(inventory);
    Object value = result.getValue();
    reader = result.getReader();

    System.out.println(value);

    return new HandlerResult(reader, inventory);
  }

  @Override
  public String getHandledCallee() {
    return "println";
  }

  private static void verifyPrint(CallExpressionNode callNode) {
    if (!"println".equals(callNode.callee().name())) {
      throw new RuntimeException("Unsupported function: " + callNode.callee().name());
    }
  }
}
