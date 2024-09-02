package edu.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VariableContext implements Context {
  private final Map<String, Number> numberVariables;
  private final Map<String, String> stringVariables;
  private final Map<String, Boolean> booleanVariables;

  public VariableContext(
      Map<String, Number> numberVariables,
      Map<String, String> stringVariables,
      Map<String, Boolean> booleanVariables) {
    this.numberVariables = Collections.unmodifiableMap(new HashMap<>(numberVariables));
    this.stringVariables = Collections.unmodifiableMap(new HashMap<>(stringVariables));
    this.booleanVariables = Collections.unmodifiableMap(new HashMap<>(booleanVariables));
  }

  public VariableContext setNumberVariable(String name, Number value) {
    Map<String, Number> newNumberVars = new HashMap<>(numberVariables);
    Map<String, String> newStringVars = new HashMap<>(stringVariables);
    Map<String, Boolean> newBooleanVars = new HashMap<>(booleanVariables);
    newNumberVars.put(name, (Number) value);
    return new VariableContext(newNumberVars, newStringVars, newBooleanVars);
  }

  public VariableContext setStringVariable(String name, String value) {
    Map<String, Number> newNumberVars = new HashMap<>(numberVariables);
    Map<String, String> newStringVars = new HashMap<>(stringVariables);
    Map<String, Boolean> newBooleanVars = new HashMap<>(booleanVariables);
    newStringVars.put(name, (String) value);
    return new VariableContext(newNumberVars, newStringVars, newBooleanVars);
  }

  public VariableContext setBooleanVariable(String name, Boolean value) {
    Map<String, Number> newNumberVars = new HashMap<>(numberVariables);
    Map<String, String> newStringVars = new HashMap<>(stringVariables);
    Map<String, Boolean> newBooleanVars = new HashMap<>(booleanVariables);
    newBooleanVars.put(name, (Boolean) value);
    return new VariableContext(newNumberVars, newStringVars, newBooleanVars);
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

  public Boolean getBooleanVariable(String name) {
    if (!hasBooleanVariable(name)) {
      throw new RuntimeException("Boolean type variable not found: " + name);
    }
    return booleanVariables.get(name);
  }

  public boolean hasNumberVariable(String name) {
    return numberVariables.containsKey(name);
  }

  public boolean hasStringVariable(String name) {
    return stringVariables.containsKey(name);
  }

  public boolean hasBooleanVariable(String id) {
    return booleanVariables.containsKey(id);
  }

  public Map<String, Number> getNumberVariables() {
    return numberVariables;
  }

  public Map<String, String> getStringVariables() {
    return stringVariables;
  }

  public Map<String, Boolean> getBooleanVariables() {
    return booleanVariables;
  }

  public VariableContext write(String name, Object value) {
    if (value instanceof Number) {
      return setNumberVariable(name, (Number) value);
    } else if (value instanceof String) {
      return setStringVariable(name, (String) value);
    } else if (value instanceof Boolean) {
      return setBooleanVariable(name, (Boolean) value);
    } else {
      throw new RuntimeException("Unsupported variable type: " + value.getClass());
    }
  }
}