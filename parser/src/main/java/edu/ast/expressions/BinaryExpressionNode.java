package edu.ast.expressions;

import edu.ast.AstVisitor;
import edu.ast.interfaces.ExpressionNode;
import edu.utils.LexicalRange;

public record BinaryExpressionNode(
    LexicalRange start,
    LexicalRange end,
    String operator,
    ExpressionNode left,
    ExpressionNode right)
    implements ExpressionNode {
  @Override
  public void accept(AstVisitor visitor) {
    visitor.visit(this);
  }
}
