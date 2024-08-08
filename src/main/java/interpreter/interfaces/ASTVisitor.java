package interpreter.interfaces;

import parser.ast.ProgramNode;
import parser.ast.expressions.BinaryExpressionNode;
import parser.ast.expressions.CallExpressionNode;
import parser.ast.expressions.IdentifierNode;
import parser.ast.expressions.LiteralNumberNode;
import parser.ast.expressions.LiteralStringNode;
import parser.ast.statements.AssignmentNode;
import parser.ast.statements.ExpressionStatementNode;
import parser.ast.statements.VariableDeclarationNode;

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
