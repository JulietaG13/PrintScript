package edu.ast.statements;

import edu.ast.AstVisitor;
import edu.ast.expressions.IdentifierNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.utils.LexicalRange;

public record VariableDeclarationNode(
    LexicalRange start,
    LexicalRange end,
    IdentifierNode id,
    Type type,
    Kind kind,
    ExpressionNode init)
    implements StatementNode {

  @Override
  public void accept(AstVisitor visitor) {
    visitor.visit(this);
  }
}
