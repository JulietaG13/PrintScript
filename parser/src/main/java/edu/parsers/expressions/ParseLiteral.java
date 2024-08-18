package edu.parsers.expressions;

import static edu.utils.ParserUtil.isLiteral;

import edu.Token;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.statements.Type;
import edu.parsers.ExpressionParser;
import edu.utils.LexicalRange;
import edu.utils.TypeProvider;
import java.util.List;

public class ParseLiteral implements ExpressionParser {

  @Override
  public ExpressionNode parse(List<Token> tokens) {

    if (!isXexpression(tokens)) {
      throw new RuntimeException(); // Should never happen
    }

    Token token = tokens.getFirst();

    LexicalRange start = token.getStart();
    LexicalRange end = token.getEnd();

    String content = token.getContent();
    Type type = TypeProvider.getTypeFromContent(content);

    switch (type) {
      case NUMBER -> {
        return new LiteralNumberNode(start, end, Double.parseDouble(content));
      }
      case STRING -> {
        return new LiteralStringNode(start, end, stripQuotes(content));
      }
    }

    throw new RuntimeException(); // TODO
  }

  @Override
  public boolean isXexpression(List<Token> tokens) {
    return tokens.size() == 1 && isLiteral(tokens, 0);
  }

  private static String stripQuotes(String s) {
    return s.substring(1, s.length() - 1);
  }
}
