package edu;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VariableContext {
  private final Map<String, Number> numberVariables;
  private final Map<String, String> stringVariables;

  public VariableContext(Map<String, Number> numberVariables, Map<String, String> stringVariables) {
    this.numberVariables = Collections.unmodifiableMap(new HashMap<>(numberVariables));
    this.stringVariables = Collections.unmodifiableMap(new HashMap<>(stringVariables));
  }

  public VariableContext setNumberVariable(String name, Number value) {
    Map<String, Number> newNumberVars = new HashMap<>(numberVariables);
    Map<String, String> newStringVars = new HashMap<>(stringVariables);
    newNumberVars.put(name, (Number) value);
    return new VariableContext(newNumberVars, newStringVars);
  }

  public VariableContext setStringVariable(String name, String value) {
    Map<String, Number> newNumberVars = new HashMap<>(numberVariables);
    Map<String, String> newStringVars = new HashMap<>(stringVariables);
    newStringVars.put(name, (String) value);
    return new VariableContext(newNumberVars, newStringVars);
  }

  public Number getNumberVariable(String name) {
    if (!hasNumberVariable(name)) {
      throw new RuntimeException("Number type variable not found: " + name);
    }
    return numberVariables.get(name);
  }

  public String getStringVariable(String name) {
    if (!hasStringVariable(name)) {
      throw new RuntimeException("Number type variable not found: " + name);
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
