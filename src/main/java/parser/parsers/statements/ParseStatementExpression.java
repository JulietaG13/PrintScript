package parser.parsers.statements;

import parser.ast.interfaces.StatementNode;
import parser.ast.statements.ExpressionStatementNode;
import lexer.Token;
import parser.parsers.ExpressionParser;
import parser.parsers.StatementParser;
import parser.parsers.expressions.ParseCallExpression;
import lexer.utils.LexicalRange;

import java.util.List;

public class ParseStatementExpression implements StatementParser {

  private final List<ExpressionParser> parsers;

  public ParseStatementExpression(List<ExpressionParser> parsers) {
    this.parsers = parsers;
  }

  public ParseStatementExpression() {
    this(
        List.of(
            new ParseCallExpression()
        )
    );
  }

  @Override
  public StatementNode parse(List<Token> tokens) {
    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getFirst().getEnd();
    List<Token> expressionPart = tokens.subList(0, tokens.size() - 1);  // [ id(...) ] ;

    for (ExpressionParser parser : parsers) {
      if (parser.isXExpression(expressionPart)) {
        return new ExpressionStatementNode(
            start,
            end,
            parser.parse(expressionPart)
        );
      }
    }

    throw new RuntimeException(); // Should never happen
  }

  @Override
  public boolean isXStatement(List<Token> tokens) {
    List<Token> expressionPart = tokens.subList(0, tokens.size() - 1);  // [ id(...) ] ;

    for (ExpressionParser parser : parsers) {
      if (parser.isXExpression(expressionPart)) {
        return true;
      }
    }
    return false;
  }
}
