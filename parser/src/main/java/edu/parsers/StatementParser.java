package edu.parsers;

import edu.Parser;
import edu.ast.interfaces.StatementNode;
import edu.tokens.Token;
import java.util.List;

public interface StatementParser {
  /*
   * Expects ';' or '}' at the end
   */

  StatementNode parse(List<Token> tokens, Parser parser);

  boolean isXstatement(List<Token> tokens);

  boolean isFinished(List<Token> tokens, Token next);
}
