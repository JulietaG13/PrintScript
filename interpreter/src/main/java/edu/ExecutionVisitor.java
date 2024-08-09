package edu;

import edu.ast.*;
import edu.ast.expressions.*;
import edu.ast.interfaces.*;
import edu.Parser.*;
import edu.ast.statements.*;

public class ExecutionVisitor implements ASTVisitor {
  private final Reader reader;

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
    String varName = node.id().name();
    node.init().accept(this);
    Object value = getValue(node.init().toString());
    if (node.kind() == Kind.LET) {
      if (value instanceof Number) {
        reader.setNumberVariable(varName, (Number) value);
      } else if (value instanceof String) {
        reader.setStringVariable(varName, (String) value);
      }
    } else {
      throw new RuntimeException("Tipo de variable no soportado: " + node.kind());
    }
  }

  @Override
  public void visit(AssignmentNode node) {
    String varName = node.id().name();
    node.value().accept(this);

    if (reader.hasNumberVariable(varName)) {
      reader.setNumberVariable(varName, reader.getNumberVariable(varName));
    } else if (reader.hasStringVariable(varName)) {
      reader.setStringVariable(varName, reader.getStringVariable(varName));
    } else {
      throw new RuntimeException("Variable no definida: " + varName);
    }
  }

  @Override
  public void visit(ExpressionStatementNode node) {
    node.expression().accept(this);
  }

  @Override
  public void visit(CallExpressionNode node) {
    if (isPrint(node)) {
      for (ExpressionNode arg : node.args()) {
        if (arg instanceof IdentifierNode identifier) {
          String varName = identifier.name();
          Object value = getValue(varName);
          System.out.println(value);
        } else {
          arg.accept(this);
          Object value = getValue(arg.toString());
          System.out.println(value);
        }
      }
    } else {
      throw new RuntimeException("Función no soportada: " + node.callee().name());
    }
  }

  @Override
  public void visit(BinaryExpressionNode node) {
    //TODO
  }

  @Override
  public void visit(IdentifierNode node) {
    //TODO
  }

  @Override
  public void visit(LiteralNumberNode node) {
    reader.setNumberVariable(node.toString(), node.value());
  }

  @Override
  public void visit(LiteralStringNode node) {
    reader.setStringVariable(node.toString(), node.value());
  }

  private boolean isPrint(ExpressionNode node) {
    return node instanceof CallExpressionNode call && "println".equals(call.callee().name());
  }

  private Object getValue(String varName) {
    return reader.hasNumberVariable(varName) ? reader.getNumberVariable(varName) : reader.getStringVariable(varName);
  }

}
