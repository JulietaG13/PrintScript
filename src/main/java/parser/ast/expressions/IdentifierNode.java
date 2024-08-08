package parser.ast.expressions;

import parser.ast.interfaces.ExpressionNode;
import interpreter.interfaces.*;
import lexer.utils.LexicalRange;

public record IdentifierNode(
    LexicalRange start,
    LexicalRange end,
    String name) implements ExpressionNode {
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
