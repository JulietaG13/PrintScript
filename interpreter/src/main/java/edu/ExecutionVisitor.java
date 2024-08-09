package edu;

import edu.ast.ASTVisitor;
import edu.ast.ProgramNode;
import edu.ast.expressions.*;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.*;

import java.util.*;

public class ExecutionVisitor implements ASTVisitor {
  private final Map<String, Number> numberVariables = new HashMap<>();
  private final Map<String, String> stringVariables = new HashMap<>();

  public ExecutionVisitor() {
  }

  @Override
  public void visit(ProgramNode node) {
    for(StatementNode statement : node.getBody()) {
      statement.accept(this);
    }
  }

  @Override
  public void visit(VariableDeclarationNode node) {
    String varName = node.id().name();
    Object value = node.init() != null ? evaluate(node.init()) : null;
    if (node.kind() == Kind.LET) {
      if (value instanceof Number) {
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
    Object value = evaluate(node.value());

    if (value instanceof Number) {
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
    evaluate(node.expression());
  }

  @Override
  public void visit(CallExpressionNode node) {
    if ("println".equals(node.callee().name())) {
      for (ExpressionNode arg : node.args()) {
        Object value = evaluate(arg);
        System.out.println(value);
      }
    } else {
      throw new RuntimeException("Función no soportada: " + node.callee().name());
    }
  }

  @Override
  public void visit(BinaryExpressionNode node) {
    //como guardar en el mapa de number variables la operacion
//    Object leftValue = evaluate(node.left());
//    Object rightValue = evaluate(node.right());
//
//    if (leftValue instanceof Double && rightValue instanceof Double) {
//      double left = (Double) leftValue;
//      double right = (Double) rightValue;
//      switch (node.operator()) {
//        case "+" -> numberVariables.put(node.operator(), left + right);
//        case "-" -> numberVariables.put(node.operator(), left - right);
//        case "*" -> numberVariables.put(node.operator(), left * right);
//        case "/" -> numberVariables.put(node.operator(), left / right);
//        default -> throw new UnsupportedOperationException("Unsupported operator: " + node.operator());
//      }
//    } else {
//      throw new UnsupportedOperationException("Binary operations are only supported for numbers.");
//    }
  }


  @Override
  public void visit(IdentifierNode node) {
    throw new UnsupportedOperationException("Los identificadores deben ser evaluados.");
  }

  @Override
  public void visit(LiteralNumberNode node) {
    throw new UnsupportedOperationException("Los literales deben ser evaluados.");
  }

  @Override
  public void visit(LiteralStringNode node) {
    throw new UnsupportedOperationException("Los literales deben ser evaluados.");
  }

  private Object evaluate(ExpressionNode node) {
    if (node instanceof IdentifierNode) {
      String varName = ((IdentifierNode) node).name();
      if (numberVariables.containsKey(varName)) {
        return numberVariables.get(varName);
      } else if (stringVariables.containsKey(varName)) {
        return stringVariables.get(varName);
      } else {
        throw new RuntimeException("Variable not found: " + varName);
      }
    }
    if (node instanceof LiteralNumberNode) {
      return ((LiteralNumberNode) node).value();
    }
    if (node instanceof LiteralStringNode) {
      return ((LiteralStringNode) node).value();
    }
    if (node instanceof BinaryExpressionNode) {
      //TODO
      //manejar como identificar la operacion en el mapa de operaciones y ejecutarla
      //se me ocurre :
      //visit((BinaryExpressionNode) node);
      //return numberVariables.get(((BinaryExpressionNode) node).operator());
      return null;
    }
    if (node instanceof CallExpressionNode) {
      visit((CallExpressionNode) node);
      return null;
      //TODO
      //verificar si deberia return null
    }
    throw new UnsupportedOperationException("Unsupported expression type: " + node.getClass().getName());
  }

  public Map getStringVariables() {
    return stringVariables;
  }
  public Map getNumberVariables() {
    return numberVariables;
  }
}
