package edu;

import java.util.Stack;

public class Reader {
  private final VariableContext variables;
  private final Stack<String> identifiers = new Stack<>();
  private final Stack<Object> literals = new Stack<>();

  public Reader(VariableContext variables) {
    this.variables = variables;
  }

  public void addIdentifier(String identifier) {
    identifiers.add(identifier);
  }

  public void addLiteral(Object literal) {
    literals.add(literal);
  }

  public String getIdentifier() {
    return identifiers.pop();
  }

  public Object getLiteral() {
    return literals.pop();
  }

  public boolean hasLiteral() {
    return !literals.isEmpty();
  }

  public boolean hasIdentifier() {
    return !identifiers.isEmpty();
  }

  public Object read() {
    if (hasLiteral()) {
      return getLiteral();
    } else {
      String id = getIdentifier();
      if (isStringVariable(id)) {
        return variables.getStringVariable(id);
      } else if (isNumberVariable(id)) {
        return variables.getNumberVariable(id);
      } else {
        throw new RuntimeException("Variable no definida: " + id);
      }
    }
  }

  public void write(String varName, Object value) {
    if (value instanceof Number) {
      variables.setNumberVariable(varName, (Number) value);
    } else if (value instanceof String) {
      variables.setStringVariable(varName, (String) value);
    } else {
      throw new RuntimeException("Tipo de variable no soportado: " + value.getClass());
    }
  }

  public boolean isStringVariable(String varName) {
    return variables.hasStringVariable(varName);
  }

  public boolean isNumberVariable(String varName) {
    return variables.hasNumberVariable(varName);
  }




}
