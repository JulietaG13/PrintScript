package edu;

import edu.ast.*;
import edu.ast.expressions.*;
import edu.ast.interfaces.*;
import edu.ast.statements.*;

public class ExecutionVisitor implements ASTVisitor {
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
      throw new RuntimeException("Función no soportada: " + node.callee().name());
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

    String operator = node.operator();

    if (isNumber(left) && isNumber(right)) {
      Number leftNumber = (Number) left;
      Number rightNumber = (Number) right;
      switch (operator) {
        case "+":
          reader = reader.addLiteral(leftNumber.doubleValue() + rightNumber.doubleValue());
          break;
        case "-":
          reader = reader.addLiteral(leftNumber.doubleValue() - rightNumber.doubleValue());
          break;
        case "*":
          reader = reader.addLiteral(leftNumber.doubleValue() * rightNumber.doubleValue());
          break;
        case "/":
          reader = reader.addLiteral(leftNumber.doubleValue() / rightNumber.doubleValue());
          break;
        default:
          throw new RuntimeException("Operador no soportado: " + operator);
      }
    } else if ((isString(left) || isString(right)) && "+".equals(operator)) {
      // Asegurarse de concatenar en el orden correcto
      String concatenatedResult = left.toString() + right.toString();
      reader = reader.addLiteral(concatenatedResult);
    } else {
      throw new RuntimeException("Operación no soportada: " + left + " " + operator + " " + right);
    }
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
    return node instanceof CallExpressionNode call && "println".equals(call.callee().name());
  }

  public Reader getReader() {
    return reader;
  }
}
