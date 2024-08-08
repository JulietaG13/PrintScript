package parser.parsers;

import ast.interfaces.StatementNode;
import lexer.Token;

import java.util.List;

public interface StatementParser {
  /*
   * Expects a ';' at the end
   */

  StatementNode parse(List<Token> tokens);

  boolean isXStatement(List<Token> tokens);
}
