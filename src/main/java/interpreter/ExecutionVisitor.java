package interpreter;

import ast.*;
import ast.expressions.*;
import ast.statements.*;
import interpreter.interfaces.*;

import java.util.HashMap;
import java.util.Map;

public class ExecutionVisitor implements ASTVisitor {
  private final Map<String, Double> numberVariables = new HashMap<>();
  private final Map<String, String> stringVariables = new HashMap<>();

  @Override
  public void visit(ProgramNode node) {
//    for(StatementNode statement : node.getStatements()) {
//      statement.accept(this);
//    }
  }

  @Override
  public void visit(AssignmentNode node) {

  }

  @Override
  public void visit(ExpressionStatementNode node) {

  }

  @Override
  public void visit(VariableDeclarationNode node) {

  }

  @Override
  public void visit(BinaryExpressionNode node) {

  }

  @Override
  public void visit(CallExpressionNode node) {

  }

  @Override
  public void visit(IdentifierNode node) {

  }

  @Override
  public void visit(LiteralNumberNode node) {

  }

  @Override
  public void visit(LiteralStringNode node) {

  }
}
