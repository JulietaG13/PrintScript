package edu;

import edu.ast.*;
import edu.ast.expressions.*;
import edu.ast.interfaces.*;
import edu.ast.statements.*;

public class ExecutionVisitor implements ASTVisitor {
  private final Reader reader;
  private final VariableContext variables;

  public ExecutionVisitor(Reader reader, VariableContext variableContext) {
    this.reader = reader;
    this.variables = variableContext;
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

    String varName = reader.getIdentifier();
    Object value = reader.getLiteral();

    if (node.kind() == Kind.LET) {
      if (value instanceof Number) {
        variables.setNumberVariable(varName, (Number) value);
      } else if (isString(value)) {
        variables.setStringVariable(varName, (String) value);
      }
    } else {
      throw new RuntimeException("Tipo de variable no soportado: " + node.kind());
    }
  }

  @Override
  public void visit(AssignmentNode node) {
    node.id().accept(this);
    node.value().accept(this);
    
    String varName = reader.getIdentifier();
    Object value = reader.getLiteral();
    
    if (isNumberVariable(varName) && isNumber(value)) {
      variables.setNumberVariable(varName, (Number) value);
    } else if (isStringVariable(varName) && isString(value)) {
      variables.setStringVariable(varName, (String) value);
    } else {
      throw new RuntimeException("Variable no definida: " + varName);
    }
  }

  private boolean isStringVariable(String varName) {
    return variables.hasStringVariable(varName);
  }

  private boolean isNumberVariable(String varName) {
    return variables.hasNumberVariable(varName);
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
      Object value = getValueFromStacks();
      reader.getIdentifier();
      System.out.println(value);
    } else {
      throw new RuntimeException("Función no soportada: " + node.callee().name());
    }
  }

  @Override
  public void visit(BinaryExpressionNode node) {
    node.left().accept(this);
    Object left = getValueFromStacks();
    node.right().accept(this);
    Object right = getValueFromStacks();
    String operator = node.operator();

    if (isNumber(left) && isNumber(right)) {
      Number leftNumber = (Number) left;
      Number rightNumber = (Number) right;
      switch (operator) {
        case "+":
          reader.addLiteral(leftNumber.doubleValue() + rightNumber.doubleValue());
          break;
        case "-":
          reader.addLiteral(leftNumber.doubleValue() - rightNumber.doubleValue());
          break;
        case "*":
          reader.addLiteral(leftNumber.doubleValue() * rightNumber.doubleValue());
          break;
        case "/":
          reader.addLiteral(leftNumber.doubleValue() / rightNumber.doubleValue());
          break;
        default:
          throw new RuntimeException("Operador no soportado: " + operator);
      }
    } else if ((isString(left) || isString(right)) && "+".equals(operator)) {
      String leftString = left.toString();
      String rightString = right.toString();
      reader.addLiteral(leftString + rightString);
    } else {
      throw new RuntimeException("Operación no soportada: " + left + " " + operator + " " + right);
    }
  }

  private Object getValueFromStacks() {
    if (reader.hasLiteral()) {
      return reader.getLiteral();
    } else {
      String id = reader.getIdentifier();
      if (isStringVariable(id)) {
        return variables.getStringVariable(id);
      } else if (isNumberVariable(id)) {
        return variables.getNumberVariable(id);
      } else {
        throw new RuntimeException("Variable no definida: " + id);
      }
    }
  }


  @Override
  public void visit(IdentifierNode node) {
    reader.addIdentifier(node.name());
  }

  @Override
  public void visit(LiteralNumberNode node) {
    reader.addLiteral(node.value());
  }

  @Override
  public void visit(LiteralStringNode node) {
    reader.addLiteral(node.value());
  }

  private boolean isPrint(ExpressionNode node) {
    return node instanceof CallExpressionNode call && "println".equals(call.callee().name());
  }
}
