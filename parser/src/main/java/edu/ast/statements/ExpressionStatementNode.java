package edu.ast.statements;

import edu.ast.AstVisitor;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.utils.LexicalRange;

public record ExpressionStatementNode(
    LexicalRange start, LexicalRange end, ExpressionNode expression) implements StatementNode {

  @Override
  public void accept(AstVisitor visitor) {
    visitor.visit(this);
  }
}
