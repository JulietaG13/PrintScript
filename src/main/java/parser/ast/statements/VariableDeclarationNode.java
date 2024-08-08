package parser.ast.statements;

import parser.ast.interfaces.ExpressionNode;
import parser.ast.expressions.IdentifierNode;
import parser.ast.interfaces.StatementNode;
import interpreter.interfaces.*;
import lexer.utils.LexicalRange;

public record VariableDeclarationNode(
    LexicalRange start,
    LexicalRange end,
    IdentifierNode id,
    Type type,
    Kind kind,
    ExpressionNode init) implements StatementNode {

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
