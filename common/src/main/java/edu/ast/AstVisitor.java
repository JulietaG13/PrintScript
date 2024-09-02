package edu.ast;

import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralBooleanNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.statements.AssignmentNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.IfStatementNode;
import edu.ast.statements.VariableDeclarationNode;

public interface AstVisitor {
  void visit(ProgramNode node);

  void visit(AssignmentNode node);

  void visit(ExpressionStatementNode node);

  void visit(VariableDeclarationNode node);

  void visit(BinaryExpressionNode node);

  void visit(CallExpressionNode node);

  void visit(IdentifierNode node);

  void visit(LiteralNumberNode node);

  void visit(LiteralStringNode node);

  void visit(IfStatementNode node);

  void visit(BlockNode node);

  void visit(LiteralBooleanNode node);
}
