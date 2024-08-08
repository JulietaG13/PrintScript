package ast.expressions;

import ast.interfaces.ExpressionNode;
import interpreter.interfaces.*;
import utils.LexicalRange;

public record BinaryExpressionNode(
    LexicalRange start,
    LexicalRange end,
    String operator,
    ExpressionNode left,
    ExpressionNode right) implements ExpressionNode {
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
