package parser.parsers.expressions;

import ast.expressions.IdentifierNode;
import ast.interfaces.ExpressionNode;
import lexer.Token;
import parser.parsers.ExpressionParser;
import java.util.List;

import static parser.utils.ParserUtil.isIdentifier;

public class ParseIdentifier implements ExpressionParser {
  @Override
  public ExpressionNode parse(List<Token> tokens) {
    if (!isXExpression(tokens)) {
      throw new RuntimeException(); // Should never happen
    }

    Token token = tokens.getFirst();
    return new IdentifierNode(
        token.getStart(),
        token.getEnd(),
        token.getContent()
    );
  }

  public IdentifierNode parse(Token token) {
    return (IdentifierNode) parse(List.of(token));
  }

  @Override
  public boolean isXExpression(List<Token> tokens) {
    return isIdentifier(tokens, 0) && tokens.size() == 1;
  }
}
