package ast.expressions;

import ast.interfaces.LiteralNode;
import utils.LexicalRange;

public record LiteralStringNode(LexicalRange start, LexicalRange end, String value) implements LiteralNode {
  
  @Override
  public LiteralType getType() {
    return LiteralType.STRING;
  }
}
