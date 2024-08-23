package edu.ast.statements;

import edu.LexicalRange;
import edu.ast.AstVisitor;
import edu.ast.expressions.IdentifierNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;

public record AssignmentNode(
    LexicalRange start, LexicalRange end, String operator, IdentifierNode id, ExpressionNode value)
    implements StatementNode {

  @Override
  public void accept(AstVisitor visitor) {
    visitor.visit(this);
  }
}
