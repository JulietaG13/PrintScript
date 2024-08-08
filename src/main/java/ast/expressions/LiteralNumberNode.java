package ast.expressions;

import ast.interfaces.LiteralNode;
import interpreter.interfaces.*;
import utils.LexicalRange;

/**
 * @param value TODO(Number)
 */
public record LiteralNumberNode(LexicalRange start, LexicalRange end, Double value) implements LiteralNode {
  
  @Override
  public LiteralType getType() {
    return LiteralType.NUMBER;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
