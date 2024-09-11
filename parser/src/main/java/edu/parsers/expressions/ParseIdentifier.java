package edu.parsers.expressions;

import static edu.utils.ParserUtil.isIdentifier;

import edu.Parser;
import edu.ast.expressions.IdentifierNode;
import edu.ast.interfaces.ExpressionNode;
import edu.exceptions.InvalidExpressionException;
import edu.parsers.ExpressionParser;
import edu.tokens.Token;
import java.util.List;

public class ParseIdentifier implements ExpressionParser {
  @Override
  public ExpressionNode parse(List<Token> tokens, Parser parser) {
    if (!isXexpression(tokens)) {
      throw new InvalidExpressionException(
          tokens.getFirst(), tokens.getLast()); // Should never happen
    }

    Token token = tokens.getFirst();
    return new IdentifierNode(token.getStart(), token.getEnd(), token.getContent());
  }

  public IdentifierNode parse(Token token, Parser parser) {
    return (IdentifierNode) parse(List.of(token), parser);
  }

  @Override
  public boolean isXexpression(List<Token> tokens) {
    return isIdentifier(tokens, 0) && tokens.size() == 1;
  }
}
