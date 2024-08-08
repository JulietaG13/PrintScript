package parser.ast.expressions;

import parser.ast.interfaces.LiteralNode;
import interpreter.interfaces.*;
import lexer.utils.LexicalRange;

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
