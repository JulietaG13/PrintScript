package edu.reader;

import edu.helpers.ReaderHelper;
import edu.inventory.Inventory;
import java.util.Stack;

public class InterpreterReader {
  private final Stack<String> identifiers;
  private final Stack<Object> literals;

  public InterpreterReader(Stack<String> identifiers, Stack<Object> literals) {
    this.identifiers = identifiers;
    this.literals = literals;
  }

  public InterpreterReader addIdentifier(String identifier) {
    Stack<String> newIdentifiers = (Stack<String>) identifiers.clone();
    newIdentifiers.add(identifier);
    return new InterpreterReader(newIdentifiers, literals);
  }

  public InterpreterReader addLiteral(Object literal) {
    Stack<Object> newLiterals = (Stack<Object>) literals.clone();
    newLiterals.add(literal);
    return new InterpreterReader(identifiers, newLiterals);
  }

  public ReaderResult getIdentifier() {
    Stack<String> newIdentifiers = (Stack<String>) identifiers.clone();
    String identifier = newIdentifiers.pop();
    InterpreterReader newInterpreterReader = new InterpreterReader(newIdentifiers, literals);
    return new ReaderResult(newInterpreterReader, identifier);
  }

  public ReaderResult getLiteral() {
    Stack<Object> newLiterals = (Stack<Object>) literals.clone();
    Object literal = newLiterals.pop();
    InterpreterReader newInterpreterReader = new InterpreterReader(identifiers, newLiterals);
    return new ReaderResult(newInterpreterReader, literal);
  }

  public boolean hasLiteral() {
    return !literals.isEmpty();
  }

  public boolean hasIdentifier() {
    return !identifiers.isEmpty();
  }

  public ReaderResult read(Inventory inventory) {
    if (hasLiteral()) {
      return getLiteral();
    }

    ReaderResult result = getIdentifier();
    String id = (String) result.getValue();
    InterpreterReader newInterpreterReader = result.getReader();

    ReaderHelper readerHelper = new ReaderHelper();
    return readerHelper
        .findVariableInContexts(inventory, id, newInterpreterReader)
        .orElseThrow(() -> new RuntimeException("Variable no definida: " + id));
  }
}
