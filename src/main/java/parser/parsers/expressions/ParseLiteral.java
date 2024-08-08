package parser.parsers.expressions;

import ast.expressions.LiteralNumberNode;
import ast.expressions.LiteralStringNode;
import ast.interfaces.ExpressionNode;
import ast.statements.Type;
import lexer.Token;
import parser.parsers.ExpressionParser;
import parser.utils.TypeProvider;
import utils.LexicalRange;

import java.util.List;

import static parser.utils.ParserUtil.isLiteral;

public class ParseLiteral implements ExpressionParser {

  @Override
  public ExpressionNode parse(List<Token> tokens) {

    if (!isXExpression(tokens)) {
      throw new RuntimeException(); // Should never happen
    }

    Token token = tokens.getFirst();

    LexicalRange start = token.getStart();
    LexicalRange end = token.getEnd();

    String content = token.getContent();
    Type type = TypeProvider.getTypeFromContent(content);

    switch (type) {
      case NUMBER -> {
        return new LiteralNumberNode(
            start,
            end,
            Double.parseDouble(content)
        );
      }
      case STRING -> {
        return new LiteralStringNode(
            start,
            end,
            content
        );
      }
    }

    throw new RuntimeException(); // TODO
  }

  @Override
  public boolean isXExpression(List<Token> tokens) {
    return tokens.size() == 1 && isLiteral(tokens, 0);
  }
}
