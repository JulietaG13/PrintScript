package edu.parsers;

import static edu.utils.ParserUtil.isCloseBracket;
import static edu.utils.ParserUtil.isEndOfStatement;
import static edu.utils.ParserUtil.isOpenBracket;

import edu.LexicalRange;
import edu.Parser;
import edu.ast.BlockNode;
import edu.ast.interfaces.StatementNode;
import edu.exceptions.UnexpectedTokenException;
import edu.tokens.Token;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParseBlock {
  /*
   * Expects to be surrounded by '{' and '}'
   */

  public static BlockNode parse(List<Token> tokens, Parser parser) {
    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getLast().getEnd();

    Iterator<Token> tokenIterator = tokens.subList(1, tokens.size() - 1).iterator();
    List<StatementNode> result = new ArrayList<>();

    if (!tokenIterator.hasNext()) {
      return new BlockNode(start, end, result);
    }

    List<Token> statement = new ArrayList<>();
    Token current = null;
    Token next = tokenIterator.next();
    int bracketBalance = 0;

    while (tokenIterator.hasNext()) {
      current = next;
      next = tokenIterator.next();

      statement.add(current);

      if (isOpenBracket(current)) {
        bracketBalance++;
      }
      if (isCloseBracket(current)) {
        bracketBalance--;
      }

      if (bracketBalance == 0 && isEndOfStatement(current) && parser.isFinished(statement, next)) {
        result.add(parser.parseStatement(statement));
        statement.clear();
      }
    }

    statement.add(next);
    if (isOpenBracket(next)) {
      bracketBalance++;
    }
    if (isCloseBracket(next)) {
      bracketBalance--;
    }

    if (bracketBalance == 0 && isEndOfStatement(next)) {
      result.add(parser.parseStatement(statement));
      statement.clear();
    }

    if (!statement.isEmpty()) {
      throw new UnexpectedTokenException(next, ";");
    }

    return new BlockNode(start, end, result);
  }

  public static BlockNode getEmpty(LexicalRange start, LexicalRange end) {
    return new BlockNode(start, end, new ArrayList<>());
  }
}
