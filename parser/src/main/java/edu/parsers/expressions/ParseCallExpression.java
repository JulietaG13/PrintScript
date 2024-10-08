package edu.parsers.expressions;

import static edu.utils.ParserUtil.isArgSeparator;
import static edu.utils.ParserUtil.isCloseParen;
import static edu.utils.ParserUtil.isIdentifier;
import static edu.utils.ParserUtil.isOpenParen;

import edu.LexicalRange;
import edu.Parser;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.interfaces.ExpressionNode;
import edu.exceptions.UnexpectedTokenException;
import edu.parsers.ExpressionParser;
import edu.tokens.Token;
import java.util.ArrayList;
import java.util.List;

public class ParseCallExpression implements ExpressionParser {
  /*
   * Does not expect a ';' at the end
   */

  @Override
  public ExpressionNode parse(List<Token> tokens, Parser parser) {
    String name = tokens.getFirst().getContent();
    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getLast().getEnd();
    // Identifier + Syntax + ... + Syntax

    IdentifierNode identifier = new IdentifierNode(start, end, name);

    List<List<Token>> expressions = splitArgs(tokens.subList(2, tokens.size() - 1));
    List<ExpressionNode> args = new ArrayList<>();

    for (List<Token> expression : expressions) {
      args.add(parser.parseExpression(expression));
    }

    return new CallExpressionNode(start, end, identifier, args);
  }

  @Override
  public boolean isXexpression(List<Token> tokens) {
    boolean minimum =
        isIdentifier(tokens, 0)
            && tokens.size() >= 3
            && isOpenParen(tokens, 1)
            && isCloseParen(tokens, tokens.size() - 1);

    if (minimum && tokens.size() == 3) {
      return true;
    }

    if (!minimum) {
      return false;
    }

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
        return false; // TODO()
      }
    }

    if (isCloseParen(tokens, tokens.size() - 1)) {
      balance--;
    }

    if (balance > 0) {
      return false;
    }

    return true;
  }

  private static List<List<Token>> splitArgs(List<Token> tokens) {
    List<List<Token>> args = new ArrayList<>();
    List<Token> current = new ArrayList<>();

    int parenBalance = 0;
    for (Token token : tokens) {

      if (isOpenParen(token)) {
        parenBalance++;
      }
      if (isCloseParen(token)) {
        parenBalance--;
      }

      if (parenBalance == 0 && isArgSeparator(token)) {
        if (current.isEmpty()) {
          throw new UnexpectedTokenException(token);
        }
        args.add(new ArrayList<>(current));
        current.clear();

      } else {
        current.add(token);
      }
    }

    if (!current.isEmpty()) {
      args.add(current);
    }
    return args;
  }
}
