package edu.reader;

import edu.VariableContext;
import java.util.Stack;

public class Reader {
  private final VariableContext variables;
  private final Stack<String> identifiers;
  private final Stack<Object> literals;

  public Reader(VariableContext variables, Stack<String> identifiers, Stack<Object> literals) {
    this.variables = variables;
    this.identifiers = identifiers;
    this.literals = literals;
  }

  public Reader addIdentifier(String identifier) {
    Stack<String> newIdentifiers = (Stack<String>) identifiers.clone();
    newIdentifiers.add(identifier);
    return new Reader(variables, newIdentifiers, literals);
  }

  public Reader addLiteral(Object literal) {
    Stack<Object> newLiterals = (Stack<Object>) literals.clone();
    newLiterals.add(literal);
    return new Reader(variables, identifiers, newLiterals);
  }

  public ReaderResult getIdentifier() {
    Stack<String> newIdentifiers = (Stack<String>) identifiers.clone();
    String identifier = newIdentifiers.pop();
    Reader newReader = new Reader(variables, newIdentifiers, literals);
    return new ReaderResult(newReader, identifier);
  }

  public ReaderResult getLiteral() {
    Stack<Object> newLiterals = (Stack<Object>) literals.clone();
    Object literal = newLiterals.pop();
    Reader newReader = new Reader(variables, identifiers, newLiterals);
    return new ReaderResult(newReader, literal);
  }

  public boolean hasLiteral() {
    return !literals.isEmpty();
  }

  public boolean hasIdentifier() {
    return !identifiers.isEmpty();
  }

  public ReaderResult read() {
    if (hasLiteral()) {
      return getLiteral();
    } else {
      ReaderResult result = getIdentifier();
      String id = (String) result.getValue();
      Reader newReader = result.getReader();
      if (isStringVariable(id)) {
        return new ReaderResult(newReader, variables.getStringVariable(id));
      } else if (isNumberVariable(id)) {
        return new ReaderResult(newReader, variables.getNumberVariable(id));
      } else {
        throw new RuntimeException("Variable no definida: " + id);
      }
    }
  }

  public Reader write(String varName, Object value) {
    VariableContext newContext;
    if (value instanceof Number) {
      newContext = variables.setNumberVariable(varName, (Number) value);
    } else if (value instanceof String) {
      newContext = variables.setStringVariable(varName, (String) value);
    } else {
      throw new RuntimeException("Tipo de variable no soportado: " + value.getClass());
    }
    return new Reader(newContext, identifiers, literals);
  }

  public boolean isStringVariable(String varName) {
    return variables.hasStringVariable(varName);
  }

  public boolean isNumberVariable(String varName) {
    return variables.hasNumberVariable(varName);
  }

  public VariableContext getVariables() {
    return variables;
  }
}
