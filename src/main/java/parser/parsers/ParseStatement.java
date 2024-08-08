package parser.parsers;

import ast.interfaces.StatementNode;
import lexer.Token;
import parser.parsers.statements.ParseAssignation;
import parser.parsers.statements.ParseLet;
import parser.parsers.statements.ParseStatementExpression;

import java.util.List;

public class ParseStatement {
  /*
   * Expects a ';' at the end
   */

  private static final List<StatementParser> parsers;

  static {
    parsers = List.of(
        new ParseAssignation(),
        new ParseLet(),
        new ParseStatementExpression()
    ); // TODO(changeable)
  }

  public static StatementNode parse(List<Token> tokens) {
    for (StatementParser parser : parsers) {
      if (parser.isXStatement(tokens)) {
        return parser.parse(tokens);
      }
    }

    throw new RuntimeException();   // TODO
  }
}
