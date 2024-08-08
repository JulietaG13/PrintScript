package ast.expressions;

import ast.interfaces.ExpressionNode;
import interpreter.interfaces.*;
import utils.LexicalRange;

public record IdentifierNode(
    LexicalRange start,
    LexicalRange end,
    String name) implements ExpressionNode {
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
