package edu.parsers.expressions;

import edu.Token;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.statements.Type;
import edu.utils.LexicalRange;
import edu.parsers.ExpressionParser;
import edu.utils.TypeProvider;

import java.util.List;

import static edu.utils.ParserUtil.isLiteral;

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
