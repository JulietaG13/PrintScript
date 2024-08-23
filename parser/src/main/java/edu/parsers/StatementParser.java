package edu.parsers;

import edu.ast.interfaces.StatementNode;
import edu.tokens.Token;
import java.util.List;

public interface StatementParser {
  /*
   * Expects a ';' at the end
   */

  StatementNode parse(List<Token> tokens);

  boolean isXstatement(List<Token> tokens);
}
