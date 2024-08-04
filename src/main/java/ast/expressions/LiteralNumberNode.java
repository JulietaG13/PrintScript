package ast.expressions;

import ast.interfaces.LiteralNode;
import utils.LexicalRange;

/**
 * @param value TODO(Number)
 */
public record LiteralNumberNode(LexicalRange start, LexicalRange end, Double value) implements LiteralNode {
  
  @Override
  public LiteralType getType() {
    return LiteralType.NUMBER;
  }
}
