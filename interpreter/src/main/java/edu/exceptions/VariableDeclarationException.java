package edu.exceptions;

public class VariableDeclarationException extends RuntimeException {
  public VariableDeclarationException(String varName, String message) {
    super("Error en la declaración de la variable '" + varName + "': " + message);
  }
}
