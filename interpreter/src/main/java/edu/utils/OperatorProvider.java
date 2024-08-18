package edu.utils;

import edu.common.Operator;

import java.util.*;

public class OperatorProvider {
  private static final Map<String, Operator> operators = new HashMap<>();

    static {
        operators.put("+", Operator.SUM);
        operators.put("-", Operator.SUBTRACT);
        operators.put("*", Operator.MULTIPLY);
        operators.put("/", Operator.DIVIDE);
    }

    public static Operator getOperator(String operatorName) {
        if (operators.containsKey(operatorName)) {
            return operators.get(operatorName);
        }
        throw new IllegalArgumentException("Unknown operator: " + operatorName);
    }


}
