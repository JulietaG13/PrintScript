package edu.ast.expressions;

import edu.ast.AstVisitor;
import edu.ast.interfaces.ExpressionNode;
import edu.utils.LexicalRange;
import java.util.List;

public record CallExpressionNode(
    LexicalRange start, LexicalRange end, IdentifierNode callee, List<ExpressionNode> args)
    implements ExpressionNode {
  @Override
  public void accept(AstVisitor visitor) {
    visitor.visit(this);
  }
}
