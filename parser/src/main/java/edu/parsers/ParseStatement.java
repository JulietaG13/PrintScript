package edu.parsers;

import edu.ast.interfaces.StatementNode;
import edu.parsers.statements.ParseAssignation;
import edu.parsers.statements.ParseLet;
import edu.parsers.statements.ParseStatementExpression;
import edu.tokens.Token;
import java.util.List;

public class ParseStatement {
  /*
   * Expects a ';' at the end
   */

  private static final List<StatementParser> parsers;

  static {
    parsers =
        List.of(
            new ParseAssignation(),
            new ParseLet(),
            new ParseStatementExpression()); // TODO(changeable)
  }

  public static StatementNode parse(List<Token> tokens) {
    for (StatementParser parser : parsers) {
      if (parser.isXstatement(tokens)) {
        return parser.parse(tokens);
      }
    }

    throw new RuntimeException(); // TODO
  }
}
