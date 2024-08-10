package edu;

import java.util.*;

public class VariablesQueue {
  Queue<String> identifiers = new LinkedList<>();
  Queue<Object> literals = new LinkedList<>();



  public void addIdentifier(String identifier) {
    identifiers.add(identifier);
  }

  public void addLiteral(Object literal) {
    literals.add(literal);
  }

  public String getIdentifier() {
    return identifiers.poll();
  }

  public Object getLiteral() {
    return literals.poll();
  }

  public boolean hasLiteral() {
    return !literals.isEmpty();
  }

  public boolean hasIdentifier() {
    return !identifiers.isEmpty();
  }
}
