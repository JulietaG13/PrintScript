package edu.exceptions;

public class AssignmentException extends RuntimeException {
  public AssignmentException(String varName, String message) {
    super("Error en la asignación de la variable '" + varName + "': " + message);
  }
}
