package edu;

import java.util.HashMap;
import java.util.Map;

public class VariableContext {
  private final Map<String, Number> numberVariables = new HashMap<>();
  private final Map<String, String> stringVariables = new HashMap<>();

  public void setNumberVariable(String name, Number value) {
    numberVariables.put(name, value);
  }

  public void setStringVariable(String name, String value) {
    stringVariables.put(name, value);
  }

  public Number getNumberVariable(String name) {
    if (!hasNumberVariable(name)) {
      throw new RuntimeException("Variable numérica no encontrada: " + name);
    }
    return numberVariables.get(name);
  }

  public String getStringVariable(String name) {
    if (!hasStringVariable(name)) {
      throw new RuntimeException("Variable numérica no encontrada: " + name);
    }
    return stringVariables.get(name);
  }

  public boolean hasNumberVariable(String name) {
    return numberVariables.containsKey(name);
  }

  public boolean hasStringVariable(String name) {
    return stringVariables.containsKey(name);
  }

  public Map<String, Number> getNumberVariables() {
    return numberVariables;
  }

  public Map<String, String> getStringVariables() {
    return stringVariables;
  }
}
