package parser.ast.expressions;

import parser.ast.interfaces.LiteralNode;
import interpreter.interfaces.*;
import lexer.utils.LexicalRange;

public record LiteralStringNode(LexicalRange start, LexicalRange end, String value) implements LiteralNode {
  
  @Override
  public LiteralType getType() {
    return LiteralType.STRING;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
