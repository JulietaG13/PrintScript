package edu.handlers.expressions;

import edu.ast.expressions.CallExpressionNode;
import edu.ast.interfaces.ExpressionNode;
import edu.handlers.ExpressionHandler;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.utils.HandlerResult;
import java.util.Scanner;

public class ReadInputExpressionHandler implements ExpressionHandler {
  private final Scanner scanner = new Scanner(System.in);

  @Override
  public HandlerResult handle(ExpressionNode node, InterpreterReader reader, Inventory inventory) {
    CallExpressionNode callNode = (CallExpressionNode) node;

    if (!isReadInput(callNode)) {
      throw new RuntimeException("Unsupported function call: " + callNode.callee().name());
    }
    String message = reader.getLiteral().getValue().toString();
    System.out.print(message);
    // creo un nuevo scanner para cada input reading
    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();
    Object typedInput = determineInputType(input);

    reader = reader.addLiteral(typedInput);
    return new HandlerResult(reader, inventory);
  }

  @Override
  public String getHandledCallee() {
    return "readInput";
  }

  private boolean isReadInput(CallExpressionNode node) {
    return "readInput".equals(node.callee().name()) && node.args().size() == 1;
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
