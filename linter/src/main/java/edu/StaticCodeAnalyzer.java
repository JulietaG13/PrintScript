package edu;

import edu.ast.AstVisitor;
import edu.ast.ProgramNode;
import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.AssignmentNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.rules.RuleProvider;

public class StaticCodeAnalyzer implements AstVisitor {
  private Report report;
  private final RuleProvider ruleProvider;

  public StaticCodeAnalyzer(
    Report report, RuleProvider ruleProvider) {
    this.report = report;
    this.ruleProvider = ruleProvider;
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
    if (!ruleProvider.getFunctionRules().matches(node)) {
      report.addMessage(ruleProvider.getFunctionRules().getErrorMessage(node));
    }
    node.callee().accept(this);
    for (ExpressionNode arg : node.args()) {
      arg.accept(this);
    }
  }

  @Override
  public void visit(IdentifierNode node) {
    String variableName = node.name();
    if (!ruleProvider.getPossibleIdentifiers().matches(variableName)) {
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
