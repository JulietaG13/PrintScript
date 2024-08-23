package edu.parsers;

import edu.ast.interfaces.ExpressionNode;
import edu.tokens.Token;
import java.util.List;

public interface ExpressionParser {
  /*
   * Does not expect a ';' at the end
   */

  ExpressionNode parse(List<Token> tokens);

  boolean isXexpression(List<Token> tokens);
}
