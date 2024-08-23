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
import edu.ast.statements.Kind;
import edu.ast.statements.VariableDeclarationNode;
import edu.reader.Reader;
import edu.reader.ReaderResult;
import edu.utils.OperatorExecutor;

public class ExecutionVisitor implements AstVisitor {
  private Reader reader;

  public ExecutionVisitor(Reader reader) {
    this.reader = reader;
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

    ReaderResult resultId = reader.getIdentifier();
    Reader newReader = resultId.getReader();
    reader = newReader;
    String varName = resultId.getValue().toString();
    ReaderResult resultValue = reader.getLiteral();
    Reader newReader1 = resultValue.getReader();
    reader = newReader1;
    Object value = resultValue.getValue();

    if (node.kind() == Kind.LET) {
      Reader newReader3 = reader.write(varName, value);
      reader = newReader3;
    } else {
      throw new RuntimeException("Tipo de variable no soportado: " + node.kind());
    }
  }

  @Override
  public void visit(AssignmentNode node) {
    node.id().accept(this);
    node.value().accept(this);

    String varName = reader.getIdentifier().getValue().toString();
    Object value = reader.getLiteral().getValue();

    if (isNumberVariable(varName) && isNumber(value)) {
      Reader newReader = reader.write(varName, value);
      reader = newReader;
    } else if (isStringVariable(varName) && isString(value)) {
      Reader newReader = reader.write(varName, value);
      reader = newReader;
    } else {
      throw new RuntimeException("Variable no definida: " + varName);
    }
  }

  private boolean isStringVariable(String varName) {
    return reader.isStringVariable(varName);
  }

  private boolean isNumberVariable(String varName) {
    return reader.isNumberVariable(varName);
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
      ReaderResult result = reader.read();
      Object value = result.getValue();
      reader = result.getReader();
      ReaderResult result1 = reader.getIdentifier();
      reader = result1.getReader();
      System.out.println(value);
    } else {
      throw new RuntimeException("Unsupported function: " + node.callee().name());
    }
  }

  @Override
  public void visit(BinaryExpressionNode node) {
    node.left().accept(this);
    ReaderResult resultLeft = reader.read();
    Object left = resultLeft.getValue();
    reader = resultLeft.getReader();

    node.right().accept(this);
    ReaderResult resultRight = reader.read();
    Object right = resultRight.getValue();
    reader = resultRight.getReader();

    String operatorSymbol = node.operator();
    Operator operator = OperatorProvider.getOperator(operatorSymbol);

    Object result = OperatorExecutor.execute(operator, left, right);
    reader = reader.addLiteral(result);
  }

  @Override
  public void visit(IdentifierNode node) {
    Reader newReader = reader.addIdentifier(node.name());
    reader = newReader;
  }

  @Override
  public void visit(LiteralNumberNode node) {
    Reader newReader = reader.addLiteral(node.value());
    reader = newReader;
  }

  @Override
  public void visit(LiteralStringNode node) {
    Reader newReader = reader.addLiteral(node.value());
    reader = newReader;
  }

  private boolean isPrint(ExpressionNode node) {
    if (node instanceof CallExpressionNode) {
      CallExpressionNode call = (CallExpressionNode) node;
      return "println".equals(call.callee().name());
    }
    return false;
  }

  public Reader getReader() {
    return reader;
  }
}
