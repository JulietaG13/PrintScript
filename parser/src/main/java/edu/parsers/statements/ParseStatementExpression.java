package edu.parsers.statements;

import edu.Token;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.parsers.ExpressionParser;
import edu.parsers.StatementParser;
import edu.parsers.expressions.ParseCallExpression;
import edu.utils.LexicalRange;
import java.util.List;

public class ParseStatementExpression implements StatementParser {

  private final List<ExpressionParser> parsers;

  public ParseStatementExpression(List<ExpressionParser> parsers) {
    this.parsers = parsers;
  }

  public ParseStatementExpression() {
    this(List.of(new ParseCallExpression()));
  }

  @Override
  public StatementNode parse(List<Token> tokens) {
    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getFirst().getEnd();
    List<Token> expressionPart = tokens.subList(0, tokens.size() - 1); // [ id(...) ] ;

    for (ExpressionParser parser : parsers) {
      if (parser.isXexpression(expressionPart)) {
        return new ExpressionStatementNode(start, end, parser.parse(expressionPart));
      }
    }

    throw new RuntimeException(); // Should never happen
  }

  @Override
  public boolean isXstatement(List<Token> tokens) {
    List<Token> expressionPart = tokens.subList(0, tokens.size() - 1); // [ id(...) ] ;

    for (ExpressionParser parser : parsers) {
      if (parser.isXexpression(expressionPart)) {
        return true;
      }
    }
    return false;
  }
}
