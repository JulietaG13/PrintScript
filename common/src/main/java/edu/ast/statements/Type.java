package edu.ast.statements;

public enum Type {
  NUMBER,
  STRING;

  public static Type combine(Type left, Type right) {
    return left == NUMBER && right == NUMBER ? NUMBER : STRING;
  }
}
