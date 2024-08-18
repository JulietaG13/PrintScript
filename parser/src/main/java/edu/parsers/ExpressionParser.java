package edu.parsers;

import edu.Token;
import edu.ast.interfaces.ExpressionNode;
import java.util.List;

public interface ExpressionParser {
  /*
   * Does not expect a ';' at the end
   */

  ExpressionNode parse(List<Token> tokens);

  boolean isXexpression(List<Token> tokens);
}
