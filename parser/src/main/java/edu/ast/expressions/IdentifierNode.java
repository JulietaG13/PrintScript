package edu.ast.expressions;

import edu.ast.ASTVisitor;
import edu.ast.interfaces.ExpressionNode;
import edu.utils.LexicalRange;

public record IdentifierNode(
    LexicalRange start,
    LexicalRange end,
    String name) implements ExpressionNode {
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
