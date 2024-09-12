package edu;

import edu.ast.AstVisitor;
import edu.ast.BlockNode;
import edu.ast.ProgramNode;
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
import edu.rules.FormatterRuleProvider;
import edu.utils.TypeProvider;
import java.util.List;

public class FormatterVisitor implements AstVisitor {
  private static final String equals = "=";
  private static final String colon = ":";
  private static final String openParen = "(";
  private static final String closeParen = ")";
  private static final String comma = ",";
  private static final String quotes = "\"";
  private static final String openBracket = "{";
  private static final String closeBracket = "}";

  private final FormatterResult result;
  private final FormatterRuleProvider ruleProvider;

  private Operator lastOperator;
  private int level;

  public FormatterVisitor(FormatterRuleProvider ruleProvider) {
    this.result = new FormatterResult();
    this.ruleProvider = ruleProvider;
    this.level = 0;
  }

  public FormatterResult getResult() {
    return result;
  }

  @Override
  public void visit(ProgramNode node) {
    List<StatementNode> statements = node.getBody();
    for (StatementNode statement : statements) {
      statement.accept(this);
    }
  }

  @Override
  public void visit(AssignmentNode node) {
    indent();
    node.id().accept(this);
    writeSymbol(equals);
    node.value().accept(this);
    result.endLine();
  }

  @Override
  public void visit(ExpressionStatementNode node) {
    indent();
    node.expression().accept(this);
    result.endLine();
  }

  @Override
  public void visit(VariableDeclarationNode node) {
    indent();

    result.write(node.kind().toString().toLowerCase());
    result.writeSpace();

    node.id().accept(this);

    writeSymbol(colon);

    String type = TypeProvider.getName(node.type());
    result.write(type);

    if (node.init() == null) {
      result.endLine();
      return;
    }

    writeSymbol(equals);

    node.init().accept(this);

    result.endLine();
  }

  @Override
  public void visit(BinaryExpressionNode node) {
    Operator operator = OperatorProvider.getOperator(node.operator());
    boolean parens = false;
    if (lastOperator != null) {
      parens = requiresParens(operator, lastOperator);
    }

    if (parens) {
      result.write(openParen);
    }

    lastOperator = operator;
    node.left().accept(this);

    result.writeSpace();
    result.write(node.operator());
    result.writeSpace();

    lastOperator = operator;
    node.right().accept(this);

    if (parens) {
      result.write(closeParen);
    }
    // lastOperator = null;
  }

  private boolean requiresParens(Operator newOperator, Operator lastOperator) {
    return newOperator.priority < lastOperator.priority;
  }

  @Override
  public void visit(CallExpressionNode node) {
    String name = node.callee().name();

    result.lineSeparator(ruleProvider.newLineBefore(name), getIndent());

    node.callee().accept(this);

    result.write(openParen);

    List<ExpressionNode> args = node.args();
    if (args.isEmpty()) {
      result.write(closeParen);
      return;
    }
    args.getFirst().accept(this);

    for (int i = 1; i < node.args().size(); i++) {
      writeSymbol(comma);
      args.get(i).accept(this);
    }

    result.write(closeParen);
  }

  @Override
  public void visit(IdentifierNode node) {
    result.write(node.name());
  }

  @Override
  public void visit(LiteralNumberNode node) {
    result.write(node.value().toString());
  }

  @Override
  public void visit(LiteralStringNode node) {
    result.write(quotes);
    result.write(node.value());
    result.write(quotes);
  }

  @Override
  public void visit(IfStatementNode node) {
    indent();

    result.write("if ");
    result.write(openParen);
    node.condition().accept(this);
    result.write(closeParen);

    result.writeSpace();
    result.write(openBracket);
    result.lineSeparator(0);

    node.thenDo().accept(this);

    indent();
    result.write(closeBracket);

    if (isElseEmpty(node.elseDo())) {
      result.lineSeparator(0);
      return;
    }

    result.write(" else ");
    result.write(openBracket);
    result.lineSeparator(0);

    node.elseDo().accept(this);

    indent();
    result.write(closeBracket);
    result.lineSeparator(0);
  }

  private boolean isElseEmpty(BlockNode block) {
    return block.start().getOffset() == block.end().getOffset();
  }

  @Override
  public void visit(BlockNode node) {
    List<StatementNode> statements = node.statements();
    level++;
    for (StatementNode statement : statements) {
      statement.accept(this);
    }
    level--;
  }

  @Override
  public void visit(LiteralBooleanNode node) {
    result.write(String.valueOf(node.value()));
  }

  private void writeSymbol(String symbol) {
    if (ruleProvider.hasSpaceBefore(symbol)) {
      result.writeSpace();
    }
    result.write(symbol);
    if (ruleProvider.hasSpaceAfter(symbol)) {
      result.writeSpace();
    }
  }

  private void indent() {
    result.write(" ".repeat(getIndent()));
  }

  private int getIndent() {
    return ruleProvider.getIndentLevel() * level;
  }
}
