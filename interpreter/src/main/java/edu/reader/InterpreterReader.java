package edu.reader;

import edu.VariableContext;
import java.util.Stack;

public class InterpreterReader {
  private final VariableContext variables;
  private final Stack<String> identifiers;
  private final Stack<Object> literals;

  public InterpreterReader(
      VariableContext variables, Stack<String> identifiers, Stack<Object> literals) {
    this.variables = variables;
    this.identifiers = identifiers;
    this.literals = literals;
  }

  public InterpreterReader addIdentifier(String identifier) {
    Stack<String> newIdentifiers = (Stack<String>) identifiers.clone();
    newIdentifiers.add(identifier);
    return new InterpreterReader(variables, newIdentifiers, literals);
  }

  public InterpreterReader addLiteral(Object literal) {
    Stack<Object> newLiterals = (Stack<Object>) literals.clone();
    newLiterals.add(literal);
    return new InterpreterReader(variables, identifiers, newLiterals);
  }

  public ReaderResult getIdentifier() {
    Stack<String> newIdentifiers = (Stack<String>) identifiers.clone();
    String identifier = newIdentifiers.pop();
    InterpreterReader newInterpreterReader =
        new InterpreterReader(variables, newIdentifiers, literals);
    return new ReaderResult(newInterpreterReader, identifier);
  }

  public ReaderResult getLiteral() {
    Stack<Object> newLiterals = (Stack<Object>) literals.clone();
    Object literal = newLiterals.pop();
    InterpreterReader newInterpreterReader =
        new InterpreterReader(variables, identifiers, newLiterals);
    return new ReaderResult(newInterpreterReader, literal);
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
      InterpreterReader newInterpreterReader = result.getReader();
      if (isStringVariable(id)) {
        return new ReaderResult(newInterpreterReader, variables.getStringVariable(id));
      } else if (isNumberVariable(id)) {
        return new ReaderResult(newInterpreterReader, variables.getNumberVariable(id));
      } else if (variables.hasBooleanVariable(id)) {
        return new ReaderResult(newInterpreterReader, variables.getBooleanVariable(id));
      } else {
        throw new RuntimeException("Variable no definida: " + id);
      }
    }
  }

  public InterpreterReader write(String varName, Object value) {
    VariableContext newContext;
    if (value instanceof Number) {
      newContext = variables.setNumberVariable(varName, (Number) value);
    } else if (value instanceof String) {
      newContext = variables.setStringVariable(varName, (String) value);
    } else if (value instanceof Boolean) {
      newContext = variables.setBooleanVariable(varName, (Boolean) value);
    } else {
      throw new RuntimeException("Tipo de variable no soportado: " + value.getClass());
    }
    return new InterpreterReader(newContext, identifiers, literals);
  }

  public InterpreterReader writeConst(String varName) {
    VariableContext newContext;
    newContext = variables.setConstant(varName);
    return new InterpreterReader(newContext, identifiers, literals);
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
