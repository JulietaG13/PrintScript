package interpreter;

import ast.*;
import ast.expressions.*;
import ast.interfaces.*;
import ast.statements.*;
import interpreter.interfaces.*;
import utils.*;

import java.util.*;

public class ExecutionVisitor implements ASTVisitor {
  private final Map<String, Number> numberVariables = new HashMap<>();
  private final Map<String, String> stringVariables = new HashMap<>();
  private final ExpressionEvaluator expressionEvaluator;

  public ExecutionVisitor() {
    this.expressionEvaluator = new ExpressionEvaluator(numberVariables, stringVariables);
  }
  @Override
  public void visit(ProgramNode node) {
//    for(StatementNode statement : node.getStatements()) {
//      statement.accept(this);
//    }
  }

  @Override
  public void visit(VariableDeclarationNode node) {
    String varName = node.id().name();
    Object value = node.init() != null ? evaluateExpression(node.init()) : null;
    if (node.kind() == Kind.LET) {
      if (value instanceof Double || value instanceof Integer) {
        numberVariables.put(varName, (Number) value);
      } else if (value instanceof String) {
        stringVariables.put(varName, (String) value);
      }
    } else {
        throw new RuntimeException("Tipo de variable no soportado: " + node.kind());
    }
  }

  @Override
  public void visit(AssignmentNode node) {
    String varName = node.id().name();
    Object value = evaluateExpression(node.value());

    if (value instanceof Double || value instanceof Integer) {
      if (numberVariables.containsKey(varName)) {
        numberVariables.put(varName, ((Number) value));
      } else {
        throw new RuntimeException("Variable no definida: " + varName);
      }
    } else if (value instanceof String) {
      if (stringVariables.containsKey(varName)) {
        stringVariables.put(varName, (String) value);
      } else {
        throw new RuntimeException("Variable no definida: " + varName);
      }
    } else {
      throw new RuntimeException("Tipo de valor no soportado: " + value);
    }
  }

  @Override
  public void visit(ExpressionStatementNode node) {
    evaluateExpression(node.expression());
  }

  @Override
  public void visit(BinaryExpressionNode node) {

  }

  @Override
  public void visit(CallExpressionNode node) {
    if ("println".equals(node.callee().name())) {
      for (ExpressionNode arg : node.args()) {
        Object value = evaluateExpression(arg);
        System.out.println(value);
      }
    } else {
      throw new RuntimeException("Función no soportada: " + node.callee().name());
    }
  }


  private Object evaluateExpression(ExpressionNode init) {
    return null;
  }



  @Override
  public void visit(IdentifierNode node) {

  }

  @Override
  public void visit(LiteralNumberNode node) {

  }

  @Override
  public void visit(LiteralStringNode node) {

  }
}
