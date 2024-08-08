package parser.ast.expressions;

import parser.ast.interfaces.ExpressionNode;
import interpreter.interfaces.*;
import lexer.utils.LexicalRange;

public record BinaryExpressionNode(
    LexicalRange start,
    LexicalRange end,
    String operator,
    ExpressionNode left,
    ExpressionNode right) implements ExpressionNode {
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
