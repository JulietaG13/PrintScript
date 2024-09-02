package edu.ast.expressions;

import edu.LexicalRange;
import edu.ast.AstVisitor;
import edu.ast.interfaces.LiteralNode;
import java.math.BigDecimal;

public record LiteralNumberNode(LexicalRange start, LexicalRange end, BigDecimal value)
    implements LiteralNode {

  @Override
  public LiteralType getType() {
    return LiteralType.NUMBER;
  }

  @Override
  public void accept(AstVisitor visitor) {
    visitor.visit(this);
  }
}
