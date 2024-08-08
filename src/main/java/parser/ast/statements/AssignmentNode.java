package parser.ast.statements;

import parser.ast.interfaces.ExpressionNode;
import parser.ast.expressions.IdentifierNode;
import parser.ast.interfaces.StatementNode;
import interpreter.interfaces.*;
import lexer.utils.LexicalRange;

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
