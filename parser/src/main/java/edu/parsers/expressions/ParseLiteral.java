package edu.parsers.expressions;

import static edu.utils.ParserUtil.isLiteral;

import edu.LexicalRange;
import edu.Parser;
import edu.ast.expressions.LiteralBooleanNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.statements.Type;
import edu.exceptions.InvalidExpressionException;
import edu.parsers.ExpressionParser;
import edu.tokens.Token;
import edu.utils.TypeProvider;
import java.math.BigDecimal;
import java.util.List;

public class ParseLiteral implements ExpressionParser {

  @Override
  public ExpressionNode parse(List<Token> tokens, Parser parser) {

    if (!isXexpression(tokens)) {
      throw new InvalidExpressionException(
          tokens.getFirst(), tokens.getLast()); // Should never happen
    }

    Token token = tokens.getFirst();

    LexicalRange start = token.getStart();
    LexicalRange end = token.getEnd();

    String content = token.getContent();
    Type type = TypeProvider.getTypeFromContent(content, start);

    return switch (type) {
      case NUMBER -> new LiteralNumberNode(start, end, new BigDecimal(content));
      case STRING -> new LiteralStringNode(start, end, stripQuotes(content));
      case BOOLEAN -> new LiteralBooleanNode(start, end, Boolean.parseBoolean(content));
      default -> throw new RuntimeException();
    };
  }

  @Override
  public boolean isXexpression(List<Token> tokens) {
    return tokens.size() == 1 && isLiteral(tokens, 0);
  }

  private static String stripQuotes(String s) {
    return s.substring(1, s.length() - 1);
  }
}
