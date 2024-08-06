package parser.parsers;

import ast.interfaces.ExpressionNode;
import lexer.Token;
import parser.parsers.expressions.ParseBinaryExpression;
import parser.parsers.expressions.ParseCallExpression;
import parser.parsers.expressions.ParseLiteral;

import java.util.List;

import static parser.utils.ParserUtil.*;

public class ParseExpression {
  /*
   * Does not expect a ';' at the end
   */

  public static ExpressionNode parse(List<Token> tokens) {

    if (isLiteralExpression(tokens)) {
      return ParseLiteral.parse(tokens.getFirst());
    }

    if (isIdentifierExpression(tokens)) {
      return getIdentifier(tokens.getFirst());
    }

    if (isCallExpression(tokens)) {
      return ParseCallExpression.parse(tokens);
    }

    if (isBinaryExpression(tokens)) {
      // TODO(not implemented)
      return ParseBinaryExpression.parse(tokens);
    }

    return null;
  }

  private static boolean isLiteralExpression(List<Token> tokens) {
    return tokens.size() == 1 && isLiteral(tokens, 0);
  }

  private static boolean isIdentifierExpression(List<Token> tokens) {
    return tokens.size() == 1 && isIdentifier(tokens, 0);
  }

  private static boolean isCallExpression(List<Token> tokens) {
    int balance = 0;

    for (int i = 1; i < tokens.size() - 1; i++) {
      if (isOpenParen(tokens, i)) {
        balance++;
      } else if (isCloseParen(tokens, i)) {
        balance--;
      }

      if (balance == 0) {
        return false;
      }
      if (balance < 0) {
        throw new RuntimeException(); // TODO()
      }
    }

    if (isCloseParen(tokens, tokens.size() - 1)) {
      balance--;
    }

    if (balance > 0) {
      throw new RuntimeException(); // TODO()
    }

    return true;
  }

  private static boolean isBinaryExpression(List<Token> tokens) {
    //TODO(isBinaryExpression)
    return false;
  }
}
