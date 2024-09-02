package edu.ast.statements;

import edu.LexicalRange;
import edu.ast.AstVisitor;
import edu.ast.BlockNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;

public record IfStatementNode(
    LexicalRange start,
    LexicalRange end,
    ExpressionNode condition,
    BlockNode thenDo,
    BlockNode elseDo)
    implements StatementNode {

  @Override
  public void accept(AstVisitor visitor) { // TODO
    visitor.visit(this);
  }
}
