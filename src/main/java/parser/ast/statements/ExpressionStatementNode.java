package parser.ast.statements;

import parser.ast.interfaces.ExpressionNode;
import parser.ast.interfaces.StatementNode;
import interpreter.interfaces.*;
import lexer.utils.LexicalRange;

public record ExpressionStatementNode(
    LexicalRange start,
    LexicalRange end,
    ExpressionNode expression) implements StatementNode {

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
