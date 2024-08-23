package edu.parsers.expressions;

import static edu.utils.ParserUtil.isIdentifier;

import edu.ast.expressions.IdentifierNode;
import edu.ast.interfaces.ExpressionNode;
import edu.parsers.ExpressionParser;
import edu.tokens.Token;
import java.util.List;

public class ParseIdentifier implements ExpressionParser {
  @Override
  public ExpressionNode parse(List<Token> tokens) {
    if (!isXexpression(tokens)) {
      throw new RuntimeException(); // Should never happen
    }

    Token token = tokens.getFirst();
    return new IdentifierNode(token.getStart(), token.getEnd(), token.getContent());
  }

  public IdentifierNode parse(Token token) {
    return (IdentifierNode) parse(List.of(token));
  }

  @Override
  public boolean isXexpression(List<Token> tokens) {
    return isIdentifier(tokens, 0) && tokens.size() == 1;
  }
}
