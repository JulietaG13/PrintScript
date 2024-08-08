package parser.ast.expressions;

import parser.ast.interfaces.ExpressionNode;
import interpreter.interfaces.*;
import lexer.utils.LexicalRange;

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
