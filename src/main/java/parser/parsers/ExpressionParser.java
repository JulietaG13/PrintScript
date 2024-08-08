package parser.parsers;

import parser.ast.interfaces.ExpressionNode;
import lexer.Token;

import java.util.List;

public interface ExpressionParser {
  /*
   * Does not expect a ';' at the end
   */

  ExpressionNode parse(List<Token> tokens);

  boolean isXExpression(List<Token> tokens);
}
