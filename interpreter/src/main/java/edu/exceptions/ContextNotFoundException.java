package edu.exceptions;

public class ContextNotFoundException extends RuntimeException {
  public ContextNotFoundException(String contextName) {
    super(contextName + "context not found.");
  }
}
