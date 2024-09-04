package edu.visitor;

import static edu.ast.statements.Kind.CONST;

import edu.Operator;
import edu.OperatorProvider;
import edu.ast.AstVisitor;
import edu.ast.BlockNode;
import edu.ast.ProgramNode;
import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralBooleanNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.AssignmentNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.IfStatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.TemporalContext;
import edu.handlers.HandlerRegistry;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.reader.ReaderResult;
import edu.utils.HandlerResult;
import edu.utils.OperatorExecutor;

public class ExecutionVisitor implements AstVisitor {
  private InterpreterReader interpreterReader;
  private Inventory inventory;
  private HandlerRegistry handlerRegistry;

  public ExecutionVisitor(
      InterpreterReader interpreterReader, Inventory inventory, HandlerRegistry handlerRegistry) {
    this.interpreterReader = interpreterReader;
    this.inventory = inventory;
    this.handlerRegistry = handlerRegistry;
  }

  @Override
  public void visit(ProgramNode node) {
    for (StatementNode statement : node.getBody()) {
      statement.accept(this);
    }
  }

  @Override
  public void visit(VariableDeclarationNode node) {
    node.id().accept(this);

    if (node.init() != null) {
      node.init().accept(this);
    }
    String type;
    if (node.kind() == CONST) {
      type = "const";
    } else {
      type = "let";
    }

    HandlerResult result =
        handlerRegistry.getStatementHandler(type).handle(node, interpreterReader, inventory);
    interpreterReader = result.getInterpreterReader();
    inventory = result.getInventory();
  }

  @Override
  public void visit(AssignmentNode node) {
    node.id().accept(this);
    node.value().accept(this);

    String varName = interpreterReader.getIdentifier().getValue().toString();
    Object value = interpreterReader.getLiteral().getValue();

    HandlerResult result =
        handlerRegistry
            .getStatementHandler("assignation")
            .handle(node, interpreterReader, inventory);
    interpreterReader = result.getInterpreterReader();
    inventory = result.getInventory();
  }

  @Override
  public void visit(IfStatementNode node) {
    node.condition().accept(this);
    ReaderResult result = interpreterReader.read(inventory);
    interpreterReader = result.getReader();
    boolean condition = evaluateCondition(result.getValue());

    TemporalContext temporalContext = new TemporalContext();
    inventory = inventory.setTemporaryContext(temporalContext);

    if (condition) {
      node.thenDo().accept(this);
    } else if (node.elseDo() != null) {
      node.elseDo().accept(this);
    }

    HandlerResult result1 =
        handlerRegistry.getStatementHandler("if").handle(node, interpreterReader, inventory);
    interpreterReader = result1.getInterpreterReader();
    inventory = result1.getInventory();
  }

  private boolean evaluateCondition(Object value) {
    if (!(value instanceof Boolean)) {
      throw new RuntimeException(
          "La condición del if debe ser un valor booleano o una variable booleana.");
    }
    return (Boolean) value;
  }

  private static boolean isString(Object value) {
    return value instanceof String;
  }

  private static boolean isNumber(Object value) {
    return value instanceof Number;
  }

  @Override
  public void visit(ExpressionStatementNode node) {
    node.expression().accept(this);
  }

  @Override
  public void visit(CallExpressionNode node) {
    node.callee().accept(this);
    for (ExpressionNode arg : node.args()) {
      arg.accept(this);
    }
    // Todo: manejar que call es
    String callee = node.callee().name().toString();
    HandlerResult result1 =
        handlerRegistry.getExpressionHandler(callee).handle(node, interpreterReader, inventory);
    interpreterReader = result1.getInterpreterReader();
    inventory = result1.getInventory();
  }

  @Override
  public void visit(BinaryExpressionNode node) {
    node.left().accept(this);
    ReaderResult result1 = interpreterReader.read(inventory);
    Object left = result1.getValue();
    interpreterReader = result1.getReader();

    node.right().accept(this);
    ReaderResult result = interpreterReader.read(inventory);
    Object right = result.getValue();
    interpreterReader = result.getReader();

    String operatorSymbol = node.operator();
    Operator operator = OperatorProvider.getOperator(operatorSymbol);
    Object result2 = OperatorExecutor.execute(operator, left, right);

    interpreterReader = interpreterReader.addLiteral(result2);
  }

  @Override
  public void visit(IdentifierNode node) {
    InterpreterReader newInterpreterReader = interpreterReader.addIdentifier(node.name());
    interpreterReader = newInterpreterReader;
  }

  @Override
  public void visit(LiteralNumberNode node) {
    InterpreterReader newInterpreterReader = interpreterReader.addLiteral(node.value());
    interpreterReader = newInterpreterReader;
  }

  @Override
  public void visit(LiteralStringNode node) {
    InterpreterReader newInterpreterReader = interpreterReader.addLiteral(node.value());
    interpreterReader = newInterpreterReader;
  }

  @Override
  public void visit(BlockNode node) {
    // todo: falta algo?
    for (StatementNode statement : node.statements()) {
      statement.accept(this);
    }
  }

  @Override
  public void visit(LiteralBooleanNode node) {
    InterpreterReader newInterpreterReader = interpreterReader.addLiteral(node.value());
    interpreterReader = newInterpreterReader;
  }

  private boolean isPrint(ExpressionNode node) {
    if (node instanceof CallExpressionNode) {
      CallExpressionNode call = (CallExpressionNode) node;
      return "println".equals(call.callee().name());
    }
    return false;
  }

  public InterpreterReader getReader() {
    return interpreterReader;
  }

  public Inventory getInventory() {
    return inventory;
  }
}
