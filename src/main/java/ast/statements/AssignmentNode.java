package ast.statements;

import ast.interfaces.ExpressionNode;
import ast.expressions.IdentifierNode;
import ast.interfaces.StatementNode;
import interpreter.interfaces.*;
import utils.LexicalRange;

public record AssignmentNode(
    LexicalRange start,
    LexicalRange end,
    String operator,
    IdentifierNode id,
    ExpressionNode value) implements StatementNode {

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
