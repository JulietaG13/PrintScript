package edu.parsers.statements;

import static edu.utils.ParserUtil.isCloseBracket;
import static edu.utils.ParserUtil.isKeyword;
import static edu.utils.ParserUtil.isOpenBracket;
import static edu.utils.ParserUtil.isOpenParen;

import edu.LexicalRange;
import edu.Parser;
import edu.ast.BlockNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.IfStatementNode;
import edu.exceptions.UnexpectedTokenException;
import edu.parsers.ParseBlock;
import edu.parsers.StatementParser;
import edu.tokens.Token;
import java.util.List;

public class ParseIf implements StatementParser {
  /*
   * Expects a '}' at the end
   */

  @Override
  public StatementNode parse(List<Token> tokens, Parser parser) {
    if (!isXstatement(tokens)) {
      throw new RuntimeException(); // Should never happen
    }

    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getLast().getEnd();

    int openAt = getFirstOpenBracket(tokens);
    int closeAt = getFirstCloseBracket(tokens);

    ExpressionNode condition = parser.parseExpression(tokens.subList(2, openAt - 1));

    BlockNode thenDo = ParseBlock.parse(tokens.subList(openAt, closeAt + 1), parser);

    if (closeAt == tokens.size() - 1) {
      BlockNode elseDo = ParseBlock.getEmpty(end, end);
      return new IfStatementNode(start, end, condition, thenDo, elseDo);
    }

    int elseAt = closeAt + 1;
    if (!isElse(tokens, elseAt)) {
      throw new UnexpectedTokenException(tokens.get(elseAt), "else");
    }

    int secondOpenAt = elseAt + 1;
    BlockNode elseDo = ParseBlock.parse(tokens.subList(secondOpenAt, tokens.size()), parser);

    return new IfStatementNode(start, end, condition, thenDo, elseDo);
  }

  @Override
  public boolean isXstatement(List<Token> tokens) {
    return isIf(tokens, 0) && isOpenParen(tokens, 1) && isCloseBracket(tokens, tokens.size() - 1);
  }

  @Override
  public boolean isFinished(List<Token> tokens, Token next) {
    return isIf(tokens, 0) && isCloseBracket(tokens.getLast()) && !isElse(next);
  }

  private static int getFirstOpenBracket(List<Token> tokens) {
    int bracketBalance = 0;
    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);
      if (isOpenBracket(token)) {
        if (bracketBalance == 0) {
          return i;
        }
        bracketBalance++;
      }
      if (isCloseBracket(token)) {
        bracketBalance--;
      }
    }
    throw new RuntimeException(); // Should never happen
  }

  private static int getFirstCloseBracket(List<Token> tokens) {
    int bracketBalance = 0;
    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);
      if (isOpenBracket(token)) {
        bracketBalance++;
      }
      if (isCloseBracket(token)) {
        bracketBalance--;
        if (bracketBalance == 0) {
          return i;
        }
      }
    }
    throw new RuntimeException(); // Should never happen
  }

  private static boolean isIf(List<Token> tokens, int index) {
    return isKeyword(tokens, index) && tokens.get(index).getContent().equals("if");
  }

  private static boolean isElse(List<Token> tokens, int index) {
    return isKeyword(tokens, index) && tokens.get(index).getContent().equals("else");
  }

  private static boolean isElse(Token token) {
    return isElse(List.of(token), 0);
  }
}
