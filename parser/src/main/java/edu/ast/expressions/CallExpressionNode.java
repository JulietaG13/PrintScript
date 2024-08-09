package edu.ast.expressions;

import edu.ast.ASTVisitor;
import edu.ast.interfaces.ExpressionNode;
import edu.utils.LexicalRange;

import java.util.List;

public record CallExpressionNode(
    LexicalRange start,
    LexicalRange end,
    IdentifierNode callee,
    List<ExpressionNode> args) implements ExpressionNode {
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
