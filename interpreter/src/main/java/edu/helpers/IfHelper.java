package edu.helpers;

import edu.context.VariableContext;
import java.math.BigDecimal;

public class IfHelper {
  public static VariableContext mergeTemporaryContext(
      VariableContext temporalContext, VariableContext variableContext) {
    for (var entry : temporalContext.getAll().entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();

      if (variableContext.hasNumberVariable(key)) {
        if (value instanceof BigDecimal) {
          variableContext = variableContext.setNumberVariable(key, (BigDecimal) value);
        } else {
          throw new RuntimeException(
              "Type mismatch: Expected a Number for variable '"
                  + key
                  + "', but got "
                  + value.getClass().getSimpleName());
        }
      } else if (variableContext.hasStringVariable(key)) {
        if (value instanceof String) {
          variableContext = variableContext.setStringVariable(key, (String) value);
        } else {
          throw new RuntimeException(
              "Type mismatch: Expected a String for variable '"
                  + key
                  + "', but got "
                  + value.getClass().getSimpleName());
        }
      } else if (variableContext.hasBooleanVariable(key)) {
        if (value instanceof Boolean) {
          variableContext = variableContext.setBooleanVariable(key, (Boolean) value);
        } else {
          throw new RuntimeException(
              "Type mismatch: Expected a Boolean for variable '"
                  + key
                  + "', but got "
                  + value.getClass().getSimpleName());
        }
      }
    }
    return variableContext;
  }
}
