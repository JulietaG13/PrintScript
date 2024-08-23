package edu.ast.expressions;

import edu.LexicalRange;
import edu.ast.AstVisitor;
import edu.ast.interfaces.ExpressionNode;

public record IdentifierNode(LexicalRange start, LexicalRange end, String name)
    implements ExpressionNode {
  @Override
  public void accept(AstVisitor visitor) {
    visitor.visit(this);
  }
}
