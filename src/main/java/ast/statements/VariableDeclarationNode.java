package ast.statements;

import ast.interfaces.ExpressionNode;
import ast.expressions.IdentifierNode;
import ast.interfaces.StatementNode;
import interpreter.interfaces.*;
import utils.LexicalRange;

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
