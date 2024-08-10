package edu;

import java.util.Stack;

public class Reader {
  VariableContext variables = new VariableContext();
  Stack<String> identifiers = new Stack<>();
  Stack<Object> literals = new Stack<>();

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

  private boolean isStringVariable(String varName) {
    return variables.hasStringVariable(varName);
  }

  private boolean isNumberVariable(String varName) {
    return variables.hasNumberVariable(varName);
  }

}
