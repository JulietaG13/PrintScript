package parser.parsers;

import parser.ast.interfaces.ExpressionNode;
import lexer.Token;
import parser.parsers.expressions.ParseBinaryExpression;
import parser.parsers.expressions.ParseCallExpression;
import parser.parsers.expressions.ParseIdentifier;
import parser.parsers.expressions.ParseLiteral;
import java.util.List;

public class ParseExpression {
  /*
   * Does not expect a ';' at the end
   */

  private static final List<ExpressionParser> parsers;

  static {
    parsers = List.of(
        new ParseBinaryExpression(),
        new ParseCallExpression(),
        new ParseIdentifier(),
        new ParseLiteral()
    ); // TODO(changeable)
  }

  public static ExpressionNode parse(List<Token> tokens) {
    for (ExpressionParser parser : parsers) {
      if (parser.isXExpression(tokens)) {
        return parser.parse(tokens);
      }
    }
    throw new RuntimeException(); // TODO
  }
}
