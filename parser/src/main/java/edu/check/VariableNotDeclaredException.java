package edu.check;

public class VariableNotDeclaredException extends RuntimeException {
  public VariableNotDeclaredException(String name) {
    super("Variable " + name + " is not declared");
  }
}
