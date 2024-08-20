package edu;

import edu.ast.AstVisitor;
import edu.ast.ProgramNode;
import edu.ast.expressions.*;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.AssignmentNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.functions.FunctionRule;
import edu.identifiers.IdentifierType;

public class StaticCodeAnalyzer implements AstVisitor {
  private IdentifierType possibleIdentifier;
  private Report report;
  private FunctionRule functionRules;

  public StaticCodeAnalyzer(
      Report report, IdentifierType possibleIdentifier, FunctionRule functionRules) {
    this.possibleIdentifier = possibleIdentifier;
    this.report = report;
    this.functionRules = functionRules;
  }

  @Override
  public void visit(ProgramNode node) {
    for (StatementNode statement : node.getBody()) {
      statement.accept(this);
    }
  }

  @Override
  public void visit(AssignmentNode node) {
    node.id().accept(this);
    node.value().accept(this);
  }

  @Override
  public void visit(ExpressionStatementNode node) {
    node.expression().accept(this);
  }

  @Override
  public void visit(VariableDeclarationNode node) {
    node.id().accept(this);
    if (node.init() != null) {
      node.init().accept(this);
    }
  }

  @Override
  public void visit(BinaryExpressionNode node) {
    node.left().accept(this);
    node.right().accept(this);
  }

  @Override
  public void visit(CallExpressionNode node) {
    if (!functionRules.matches(node)) {
      report.addMessage(functionRules.getErrorMessage(node));
    }
    node.callee().accept(this);
    for (ExpressionNode arg : node.args()) {
      arg.accept(this);
    }
  }

  @Override
  public void visit(IdentifierNode node) {
    String variableName = node.name();
    if (!possibleIdentifier.matches(variableName)) {
      report.addMessage(
          "Invalid identifier name: " + variableName + " at position " + node.start().toString());
    }
  }

  @Override
  public void visit(LiteralNumberNode node) {
    /* do nothing */
  }

  @Override
  public void visit(LiteralStringNode node) {
    /* do nothing */
  }
}
