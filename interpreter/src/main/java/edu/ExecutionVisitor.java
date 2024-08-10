package edu;

import edu.ast.*;
import edu.ast.expressions.*;
import edu.ast.interfaces.*;
import edu.Parser.*;
import edu.ast.statements.*;

public class ExecutionVisitor implements ASTVisitor {
  private final Reader reader;
  private final VariablesQueue queues;

  public ExecutionVisitor(Reader reader) {
    this.reader = reader;
    this.queues = new VariablesQueue();
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

    String varName = queues.getIdentifier();
    Object value = queues.getLiteral();

    if (node.kind() == Kind.LET) {
      if (value instanceof Number) {
        reader.setNumberVariable(varName, (Number) value);
      } else if (isString(value)) {
        reader.setStringVariable(varName, (String) value);
      }
    } else {
      throw new RuntimeException("Tipo de variable no soportado: " + node.kind());
    }
  }

  @Override
  public void visit(AssignmentNode node) {
    node.id().accept(this);
    node.value().accept(this);
    
    String varName = queues.getIdentifier();
    Object value = queues.getLiteral();
    
    if (isNumberVariable(varName) && isNumber(value)) {
      reader.setNumberVariable(varName, (Number) value);
    } else if (isStringVariable(varName) && isString(value)) {
      reader.setStringVariable(varName, (String) value);
    } else {
      throw new RuntimeException("Variable no definida: " + varName);
    }
  }

  private boolean isStringVariable(String varName) {
    return reader.hasStringVariable(varName);
  }

  private boolean isNumberVariable(String varName) {
    return reader.hasNumberVariable(varName);
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
      Object value = getValueForPrint();
      System.out.println(value);
    } else {
      throw new RuntimeException("Función no soportada: " + node.callee().name());
    }
  }

  @Override
  public void visit(BinaryExpressionNode node) {
    node.left().accept(this);
    node.right().accept(this);

    Object left = queues.getLiteral();
    Object right = queues.getLiteral();
    String operator = node.operator();

    if (isNumber(left) && isNumber(right)) {
      Number leftNumber = (Number) left;
      Number rightNumber = (Number) right;
      switch (operator) {
        case "+":
          queues.addLiteral(leftNumber.doubleValue() + rightNumber.doubleValue());
          break;
        case "-":
          queues.addLiteral(leftNumber.doubleValue() - rightNumber.doubleValue());
          break;
        case "*":
          queues.addLiteral(leftNumber.doubleValue() * rightNumber.doubleValue());
          break;
        case "/":
          queues.addLiteral(leftNumber.doubleValue() / rightNumber.doubleValue());
          break;
        default:
          throw new RuntimeException("Operador no soportado: " + operator);
      }
    } else {
      throw new RuntimeException("Operación no soportada: " + left + " " + operator + " " + right);
    }

  }

  @Override
  public void visit(IdentifierNode node) {
    queues.addIdentifier(node.name());
  }

  @Override
  public void visit(LiteralNumberNode node) {
    queues.addLiteral(node.value());
  }

  @Override
  public void visit(LiteralStringNode node) {
    queues.addLiteral(node.value());
  }



  private boolean isPrint(ExpressionNode node) {
    return node instanceof CallExpressionNode call && "println".equals(call.callee().name());
  }

  private Object getValueForPrint() {
    if(queues.hasLiteral()) {
      queues.getIdentifier();
      return queues.getLiteral();
    } else {
      queues.getIdentifier();
      String id =  queues.getIdentifier();
      if (isStringVariable(id)) {
        return reader.getStringVariable(id);
      } else if (isNumberVariable(id)) {
        return reader.getNumberVariable(id);
      } else {
        throw new RuntimeException("Variable no definida: " + id);
      }
    }
  }

}
