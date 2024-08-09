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
    return numberVariables.get(name);
  }

  public String getStringVariable(String name) {
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
