package edu.parsers;

import edu.Token;
import edu.ast.interfaces.ExpressionNode;
import edu.parsers.expressions.ParseBinaryExpression;
import edu.parsers.expressions.ParseCallExpression;
import edu.parsers.expressions.ParseIdentifier;
import edu.parsers.expressions.ParseLiteral;
import java.util.List;

public class ParseExpression {
  /*
   * Does not expect a ';' at the end
   */

  private static final List<ExpressionParser> parsers;

  static {
    parsers =
        List.of(
            new ParseBinaryExpression(),
            new ParseCallExpression(),
            new ParseIdentifier(),
            new ParseLiteral()); // TODO(changeable)
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
