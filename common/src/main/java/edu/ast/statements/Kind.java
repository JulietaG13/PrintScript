package edu.ast.statements;

public enum Kind {
  LET("let"),
  CONST("const");

  Kind(String name) {
    this.name = name;
  }

  public final String name;
}
