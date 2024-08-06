package interpreter;

import ast.expressions.*;
import ast.interfaces.*;

import java.util.*;

public class ExpressionEvaluator {
  private final Map<String, Number> numberVariables;
  private final Map<String, String> stringVariables;

  public ExpressionEvaluator(Map<String, Number> numberVariables, Map<String, String> stringVariables) {
    this.numberVariables = numberVariables;
    this.stringVariables = stringVariables;
  }

  public Object evaluate(ExpressionNode node) {
    if (node instanceof LiteralNumberNode) {
      return ((LiteralNumberNode) node).value();
    } else if (node instanceof LiteralStringNode) {
      return ((LiteralStringNode) node).value();
    } else if (node instanceof IdentifierNode) {
      String varName = ((IdentifierNode) node).name();
      if (numberVariables.containsKey(varName)) {
        return numberVariables.get(varName);
      } else if (stringVariables.containsKey(varName)) {
        return stringVariables.get(varName);
      } else {
        throw new RuntimeException("Variable no definida: " + varName);
      }
    } else if (node instanceof BinaryExpressionNode) {
      return evaluateBinaryExpression((BinaryExpressionNode) node);
    }
    throw new RuntimeException("Tipo de expresión no soportada: " + node.getClass().getSimpleName());
  }

  private Object evaluateBinaryExpression(BinaryExpressionNode node) {
    Object leftValue = evaluate(node.left());
    Object rightValue = evaluate(node.right());
    if (leftValue instanceof Number && rightValue instanceof Number) {
      Double leftDouble = ((Number) leftValue).doubleValue();
      Double rightDouble = ((Number) rightValue).doubleValue();
      switch (node.operator()) {
        case "+":
          return leftDouble + rightDouble;
        case "-":
          return leftDouble - rightDouble;
        case "*":
          return leftDouble * rightDouble;
        case "/":
          return leftDouble / rightDouble;
      }
    } else if (leftValue instanceof String || rightValue instanceof String) {
      // concatenacion de string y number que queda string
      return leftValue.toString() + rightValue.toString();
    }
    throw new RuntimeException("Operador no soportado: " + node.operator());
  }
}

