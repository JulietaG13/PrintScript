package edu.handlers.expressions;

import edu.PrintEmitter;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.interfaces.ExpressionNode;
import edu.handlers.ExpressionHandler;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.reader.ReaderResult;
import edu.utils.HandlerResult;

public class PrintExpressionHandler implements ExpressionHandler {
  private final PrintEmitter printEmitter;

  public PrintExpressionHandler(PrintEmitter printEmitter) {
    this.printEmitter = printEmitter;
  }

  @Override
  public HandlerResult handle(ExpressionNode node, InterpreterReader reader, Inventory inventory) {
    CallExpressionNode callNode = (CallExpressionNode) node;
    ReaderResult result = reader.read(inventory);
    Object value = result.getValue();
    reader = result.getReader();

    printEmitter.print(value.toString());

    return new HandlerResult(reader, inventory);
  }

  @Override
  public String getHandledCallee() {
    return "println";
  }
}
