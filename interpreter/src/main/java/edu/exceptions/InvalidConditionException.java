package edu.exceptions;

public class InvalidConditionException extends RuntimeException {
  public InvalidConditionException(String conditionType) {
    super(
        "La condición del if debe ser un valor booleano o una variable booleana, se encontró: "
            + conditionType);
  }
}
