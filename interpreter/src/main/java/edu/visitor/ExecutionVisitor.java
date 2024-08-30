package edu.visitor;

import edu.Operator;
import edu.OperatorProvider;
import edu.ast.AstVisitor;
import edu.ast.ProgramNode;
import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.AssignmentNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.handlers.HandlerRegistry;
import edu.reader.InterpreterReader;
import edu.reader.ReaderResult;
import edu.utils.OperatorExecutor;

public class ExecutionVisitor implements AstVisitor {
  private InterpreterReader interpreterReader;

  public ExecutionVisitor(InterpreterReader interpreterReader) {
    this.interpreterReader = interpreterReader;
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
    node.init().accept(this);
    InterpreterReader newInterpreterReader =
        (InterpreterReader)
            HandlerRegistry.getStatementHandler(node).handle(node, interpreterReader);
    interpreterReader = newInterpreterReader;
  }

  @Override
  public void visit(AssignmentNode node) {
    node.id().accept(this);
    node.value().accept(this);

    String varName = interpreterReader.getIdentifier().getValue().toString();
    Object value = interpreterReader.getLiteral().getValue();

    if (isNumberVariable(varName) && isNumber(value)) {
      InterpreterReader newInterpreterReader = interpreterReader.write(varName, value);
      interpreterReader = newInterpreterReader;
    } else if (isStringVariable(varName) && isString(value)) {
      InterpreterReader newInterpreterReader = interpreterReader.write(varName, value);
      interpreterReader = newInterpreterReader;
    } else {
      throw new RuntimeException("Variable no definida: " + varName);
    }
  }

  private boolean isStringVariable(String varName) {
    return interpreterReader.isStringVariable(varName);
  }

  private boolean isNumberVariable(String varName) {
    return interpreterReader.isNumberVariable(varName);
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
    int argumentsCount = node.args().size();
    for (ExpressionNode arg : node.args()) {
      arg.accept(this);
    }
    if (isPrint(node)) {
      ReaderResult result = interpreterReader.read();
      Object value = result.getValue();
      interpreterReader = result.getReader();
      ReaderResult result1 = interpreterReader.getIdentifier();
      interpreterReader = result1.getReader();
      System.out.println(value);
    } else {
      throw new RuntimeException("Unsupported function: " + node.callee().name());
    }
  }

  @Override
  public void visit(BinaryExpressionNode node) {
    node.left().accept(this);
    ReaderResult resultLeft = interpreterReader.read();
    Object left = resultLeft.getValue();
    interpreterReader = resultLeft.getReader();

    node.right().accept(this);
    ReaderResult resultRight = interpreterReader.read();
    Object right = resultRight.getValue();
    interpreterReader = resultRight.getReader();

    String operatorSymbol = node.operator();
    Operator operator = OperatorProvider.getOperator(operatorSymbol);

    Object result = OperatorExecutor.execute(operator, left, right);
    interpreterReader = interpreterReader.addLiteral(result);
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
}
