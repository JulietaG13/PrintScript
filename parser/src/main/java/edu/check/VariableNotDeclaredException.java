package edu.check;

import edu.LexicalRange;

public class VariableNotDeclaredException extends RuntimeException {
  public VariableNotDeclaredException(String name, LexicalRange range) {
    super("Variable " + name + " is not declared at " + range.toString());
  }
}
