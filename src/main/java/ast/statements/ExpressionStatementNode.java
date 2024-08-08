package ast.statements;

import ast.interfaces.ExpressionNode;
import ast.interfaces.StatementNode;
import interpreter.interfaces.*;
import utils.LexicalRange;

public record ExpressionStatementNode(
    LexicalRange start,
    LexicalRange end,
    ExpressionNode expression) implements StatementNode {

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
