package edu.ast.expressions;

import edu.LexicalRange;
import edu.ast.AstVisitor;
import edu.ast.interfaces.LiteralNode;

public record LiteralBooleanNode(LexicalRange start, LexicalRange end, boolean value)
    implements LiteralNode {

  @Override
  public LiteralType getType() {
    return LiteralType.BOOLEAN;
  }

  @Override
  public void accept(AstVisitor visitor) { // TODO
    visitor.visit(this);
  }
}
