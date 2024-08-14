package edu.ast.expressions;

import edu.ast.ASTVisitor;
import edu.ast.interfaces.LiteralNode;
import edu.utils.LexicalRange;

public record LiteralStringNode(LexicalRange start, LexicalRange end, String value)
    implements LiteralNode {

  @Override
  public LiteralType getType() {
    return LiteralType.STRING;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
