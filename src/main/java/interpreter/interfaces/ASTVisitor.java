package interpreter.interfaces;

import ast.ProgramNode;
import ast.expressions.BinaryExpressionNode;
import ast.expressions.CallExpressionNode;
import ast.expressions.IdentifierNode;
import ast.expressions.LiteralNumberNode;
import ast.expressions.LiteralStringNode;
import ast.statements.AssignmentNode;
import ast.statements.ExpressionStatementNode;
import ast.statements.VariableDeclarationNode;

public interface ASTVisitor {
  void visit(ProgramNode node);
  void visit(AssignmentNode node);
  void visit(ExpressionStatementNode node);
  void visit(VariableDeclarationNode node);
  void visit(BinaryExpressionNode node);
  void visit(CallExpressionNode node);
  void visit(IdentifierNode node);
  void visit(LiteralNumberNode node);
  void visit(LiteralStringNode node);
}
