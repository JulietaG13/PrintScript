package edu.reader;

import edu.context.ConstantContext;
import edu.context.Context;
import edu.context.VariableContext;
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
    } else {
      ReaderResult result = getIdentifier();
      String id = (String) result.getValue();
      InterpreterReader newInterpreterReader = result.getReader();

      for (Context context : inventory.getContexts()) {
        if (context instanceof VariableContext) {
          VariableContext variableContext = (VariableContext) context;
          if (variableContext.hasStringVariable(id)) {
            return new ReaderResult(newInterpreterReader, variableContext.getStringVariable(id));
          } else if (variableContext.hasNumberVariable(id)) {
            return new ReaderResult(newInterpreterReader, variableContext.getNumberVariable(id));
          } else if (variableContext.hasBooleanVariable(id)) {
            return new ReaderResult(newInterpreterReader, variableContext.getBooleanVariable(id));
          }
        } else if (context instanceof ConstantContext) {
          ConstantContext constantContext = (ConstantContext) context;
          if (constantContext.hasConstant(id)) {
            return new ReaderResult(
                newInterpreterReader, id); // El valor de una constante puede ser el propio nombre
          }
        }
      }

      throw new RuntimeException("Variable no definida: " + id);
    }
  }
}
