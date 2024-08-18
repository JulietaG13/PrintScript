package edu.utils;

public class BinaryOperations {
    public Object sum(Object left, Object right) {
        if (isaNumber(left, right)) {
        return ((Number) left).doubleValue() + ((Number) right).doubleValue();
        } else if (isaConcat(left, right)) {
        return left.toString() + right.toString();
        } else {
        throw new RuntimeException("Operación no soportada: " + left + " + " + right);
        }
    }

  public Object subtract(Object left, Object right) {
        if (isaNumber(left, right)) {
        return ((Number) left).doubleValue() - ((Number) right).doubleValue();
        } else {
        throw new RuntimeException("Operación no soportada: " + left + " - " + right);
        }
    }

    public Object multiply(Object left, Object right) {
        if (isaNumber(left, right)) {
        return ((Number) left).doubleValue() * ((Number) right).doubleValue();
        } else {
        throw new RuntimeException("Operación no soportada: " + left + " * " + right);
        }
    }

    public Object divide(Object left, Object right) {
        if (isaNumber(left, right)) {
        return ((Number) left).doubleValue() / ((Number) right).doubleValue();
        } else {
        throw new RuntimeException("Operación no soportada: " + left + " / " + right);
        }
    }

  private static boolean isaConcat(Object left, Object right) {
    return left instanceof String || right instanceof String;
  }

  private static boolean isaNumber(Object left, Object right) {
    return left instanceof Number && right instanceof Number;
  }
}
