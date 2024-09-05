package edu;

import edu.ast.BlockNode;
import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralBooleanNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.AssignmentNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.IfStatementNode;
import edu.ast.statements.VariableDeclarationNode;
import edu.rules.RuleProviderLinter;

public class StaticCodeAnalyzer {
  private Report report;
  private final RuleProviderLinter ruleProvider;

  public StaticCodeAnalyzer(Report report, RuleProviderLinter ruleProvider) {
    this.report = report;
    this.ruleProvider = ruleProvider;
  }

  public void analyze(StatementNode node) {
    switch (node) {
      case AssignmentNode assignmentNode:
        analyze(assignmentNode);
        break;
      case VariableDeclarationNode variableDeclarationNode:
        analyze(variableDeclarationNode);
        break;
      case ExpressionStatementNode expressionStatementNode:
        analyze(expressionStatementNode);
        break;
      case IfStatementNode ifStatementNode:
        analyze(ifStatementNode);
        break;
      default:
        throw new IllegalArgumentException("Unexpected statement node: " + node);
    }
  }

  private void analyze(ExpressionStatementNode node) {
    analyze(node.expression());
  }

  private void analyze(ExpressionNode node) {
    switch (node) {
      case BinaryExpressionNode binaryExpressionNode:
        analyze(binaryExpressionNode);
        break;
      case CallExpressionNode callExpressionNode:
        analyze(callExpressionNode);
        break;
      case IdentifierNode identifierNode:
        analyze(identifierNode);
        break;
      case LiteralNumberNode literalNumberNode:
        break;
      case LiteralStringNode literalStringNode:
        break;
      case LiteralBooleanNode literalBooleanNode:
        break;
      default:
        throw new IllegalArgumentException("Unexpected expression node: " + node);
    }
  }

  private void analyze(AssignmentNode node) {
    analyze(node.id());
    analyze(node.value());
  }

  private void analyze(IfStatementNode node) {
    analyze(node.elseDo());
    analyze(node.thenDo());
  }

  private void analyze(BlockNode node) {
    for (StatementNode statement : node.statements()) {
      analyze(statement);
    }
  }

  private void analyze(VariableDeclarationNode node) {
    analyze(node.id());
    if (node.init() != null) {
      analyze(node.init());
    }
  }

  private void analyze(BinaryExpressionNode node) {
    analyze(node.left());
    analyze(node.right());
  }

  private void analyze(CallExpressionNode node) {
    if (!ruleProvider.getFunctionRules().matches(node)) {
      report.addMessage(ruleProvider.getFunctionRules().getErrorMessage(node));
    }
    analyze(node.callee());
    for (ExpressionNode arg : node.args()) {
      analyze(arg);
    }
  }

  private void analyze(IdentifierNode node) {
    String variableName = node.name();
    if (!ruleProvider.getPossibleIdentifiers().matches(variableName)) {
      report.addMessage(
          "Invalid identifier name: " + variableName + " at position " + node.start().toString());
    }
  }
}
