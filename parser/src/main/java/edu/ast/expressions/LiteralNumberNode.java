package edu.ast.expressions;

import edu.ast.ASTVisitor;
import edu.ast.interfaces.LiteralNode;
import edu.utils.LexicalRange;

/**
 * @param value TODO(Number)
 */
public record LiteralNumberNode(LexicalRange start, LexicalRange end, Double value)
    implements LiteralNode {

  @Override
  public LiteralType getType() {
    return LiteralType.NUMBER;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
