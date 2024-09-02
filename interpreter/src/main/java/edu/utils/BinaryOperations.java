package edu.utils;

import java.math.BigDecimal;
import java.math.MathContext;

public class BinaryOperations {
  public Object sum(Object left, Object right) {
    if (isaNumber(left, right)) {
      return ((BigDecimal) left).add((BigDecimal) right);
    } else if (isaConcat(left, right)) {
      return left.toString() + right.toString();
    } else {
      throw new RuntimeException("Unsupported operation: " + left + " + " + right);
    }
  }

  public Object subtract(Object left, Object right) {
    if (isaNumber(left, right)) {
      return ((BigDecimal) left).subtract((BigDecimal) right);
    } else {
      throw new RuntimeException("Unsupported operation: " + left + " - " + right);
    }
  }

  public Object multiply(Object left, Object right) {
    if (isaNumber(left, right)) {
      return ((BigDecimal) left).multiply((BigDecimal) right);
    } else {
      throw new RuntimeException("Unsupported operation: " + left + " * " + right);
    }
  }

  public Object divide(Object left, Object right) {
    if (isaNumber(left, right)) {
      return ((BigDecimal) left).divide((BigDecimal) right, MathContext.DECIMAL32);
    } else {
      throw new RuntimeException("Unsupported operation: " + left + " / " + right);
    }
  }

  private static boolean isaConcat(Object left, Object right) {
    return left instanceof String || right instanceof String;
  }

  private static boolean isaNumber(Object left, Object right) {
    return left instanceof BigDecimal && right instanceof BigDecimal;
  }
}
