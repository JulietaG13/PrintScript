package edu.helpers;

import edu.context.VariableContext;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class IfHelper {

  private static final Map<Class<?>, BiConsumer<VariableContext, Map.Entry<String, Object>>>
      variableUpdaters = new HashMap<>();

  static {
    variableUpdaters.put(BigDecimal.class, IfHelper::updateNumberVariable);
    variableUpdaters.put(String.class, IfHelper::updateStringVariable);
    variableUpdaters.put(Boolean.class, IfHelper::updateBooleanVariable);
  }

  public static VariableContext mergeTemporaryContext(
      VariableContext temporalContext, VariableContext variableContext) {

    for (var entry : temporalContext.getAll().entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();

      if (variableContext.hasNumberVariable(key)
          || variableContext.hasStringVariable(key)
          || variableContext.hasBooleanVariable(key)) {
        BiConsumer<VariableContext, Map.Entry<String, Object>> updater =
            variableUpdaters.get(value.getClass());
        if (updater == null) {
          throw new RuntimeException("Unsupported type: " + value.getClass().getSimpleName());
        }

        updater.accept(variableContext, entry);
      }
    }

    return variableContext;
  }

  private static void updateNumberVariable(
      VariableContext variableContext, Map.Entry<String, Object> entry) {
    if (!(entry.getValue() instanceof BigDecimal)) {
      throw new RuntimeException(
          "Type mismatch: Expected a Number for variable '" + entry.getKey() + "'");
    }
    variableContext.setNumberVariable(entry.getKey(), (BigDecimal) entry.getValue());
  }

  private static void updateStringVariable(
      VariableContext variableContext, Map.Entry<String, Object> entry) {
    if (!(entry.getValue() instanceof String)) {
      throw new RuntimeException(
          "Type mismatch: Expected a String for variable '" + entry.getKey() + "'");
    }
    variableContext.setStringVariable(entry.getKey(), (String) entry.getValue());
  }

  private static void updateBooleanVariable(
      VariableContext variableContext, Map.Entry<String, Object> entry) {
    if (!(entry.getValue() instanceof Boolean)) {
      throw new RuntimeException(
          "Type mismatch: Expected a Boolean for variable '" + entry.getKey() + "'");
    }
    variableContext.setBooleanVariable(entry.getKey(), (Boolean) entry.getValue());
  }
}
