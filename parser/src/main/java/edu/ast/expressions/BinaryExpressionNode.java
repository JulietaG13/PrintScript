package edu.ast.expressions;

import edu.ast.ASTVisitor;
import edu.ast.interfaces.ExpressionNode;
import edu.utils.LexicalRange;

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
