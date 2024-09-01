package edu;

import edu.parsers.ExpressionParser;
import edu.parsers.StatementParser;
import edu.parsers.expressions.ParseBinaryExpression;
import edu.parsers.expressions.ParseCallExpression;
import edu.parsers.expressions.ParseIdentifier;
import edu.parsers.expressions.ParseLiteral;
import edu.parsers.statements.ParseAssignation;
import edu.parsers.statements.ParseConst;
import edu.parsers.statements.ParseIf;
import edu.parsers.statements.ParseLet;
import edu.parsers.statements.ParseStatementExpression;
import java.util.List;

public class ParserFactory {

  public static Parser createParserV1(Lexer lexer) {
    List<StatementParser> statementParsers =
        List.of(new ParseAssignation(), new ParseLet(), new ParseStatementExpression());
    List<ExpressionParser> expressionParsers =
        List.of(
            new ParseBinaryExpression(),
            new ParseCallExpression(),
            new ParseIdentifier(),
            new ParseLiteral());
    return new Parser(lexer, statementParsers, expressionParsers);
  }

  public static Parser createParserV2(Lexer lexer) {
    List<StatementParser> statementParsers =
        List.of(
            new ParseAssignation(),
            new ParseLet(),
            new ParseConst(),
            new ParseStatementExpression(),
            new ParseIf());
    List<ExpressionParser> expressionParsers =
        List.of(
            new ParseBinaryExpression(),
            new ParseCallExpression(),
            new ParseIdentifier(),
            new ParseLiteral());
    return new Parser(lexer, statementParsers, expressionParsers);
  }
}
