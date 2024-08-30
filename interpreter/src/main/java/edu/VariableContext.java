package edu;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VariableContext {
  private final Map<String, Number> numberVariables;
  private final Map<String, String> stringVariables;
  private final Map<String, Boolean> booleanVariables;
  private final Set<String> constants;

  public VariableContext(
      Map<String, Number> numberVariables,
      Map<String, String> stringVariables,
      Map<String, Boolean> booleanVariables,
      Set<String> constants) {
    this.numberVariables = Collections.unmodifiableMap(new HashMap<>(numberVariables));
    this.stringVariables = Collections.unmodifiableMap(new HashMap<>(stringVariables));
    this.booleanVariables = Collections.unmodifiableMap(new HashMap<>(booleanVariables));
    this.constants = Collections.unmodifiableSet(new HashSet<>(constants));
  }

  public VariableContext setNumberVariable(String name, Number value) {
    Map<String, Number> newNumberVars = new HashMap<>(numberVariables);
    Map<String, String> newStringVars = new HashMap<>(stringVariables);
    Map<String, Boolean> newBooleanVars = new HashMap<>(booleanVariables);
    Set<String> newConstants = new HashSet<>(constants);
    newNumberVars.put(name, (Number) value);
    return new VariableContext(newNumberVars, newStringVars, newBooleanVars, newConstants);
  }

  public VariableContext setStringVariable(String name, String value) {
    Map<String, Number> newNumberVars = new HashMap<>(numberVariables);
    Map<String, String> newStringVars = new HashMap<>(stringVariables);
    Map<String, Boolean> newBooleanVars = new HashMap<>(booleanVariables);
    Set<String> newConstants = new HashSet<>(constants);
    newStringVars.put(name, (String) value);
    return new VariableContext(newNumberVars, newStringVars, newBooleanVars, newConstants);
  }

  public VariableContext setBooleanVariable(String name, Boolean value) {
    Map<String, Number> newNumberVars = new HashMap<>(numberVariables);
    Map<String, String> newStringVars = new HashMap<>(stringVariables);
    Map<String, Boolean> newBooleanVars = new HashMap<>(booleanVariables);
    Set<String> newConstants = new HashSet<>(constants);
    newBooleanVars.put(name, (Boolean) value);
    return new VariableContext(newNumberVars, newStringVars, newBooleanVars, newConstants);
  }

  public VariableContext setConstant(String name) {
    Map<String, Number> newNumberVars = new HashMap<>(numberVariables);
    Map<String, String> newStringVars = new HashMap<>(stringVariables);
    Map<String, Boolean> newBooleanVars = new HashMap<>(booleanVariables);
    Set<String> newConstants = new HashSet<>(constants);
    newConstants.add(name);
    return new VariableContext(newNumberVars, newStringVars, newBooleanVars, newConstants);
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

  public boolean isConstant(String name) {
    return constants.contains(name);
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
}
