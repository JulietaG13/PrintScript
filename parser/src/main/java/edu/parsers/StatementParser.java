package edu.parsers;

import edu.Token;
import edu.ast.interfaces.StatementNode;
import java.util.List;

public interface StatementParser {
  /*
   * Expects a ';' at the end
   */

  StatementNode parse(List<Token> tokens);

  boolean isXStatement(List<Token> tokens);
}
