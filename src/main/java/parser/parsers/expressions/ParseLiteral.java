package parser.parsers.expressions;

import ast.expressions.LiteralNumberNode;
import ast.expressions.LiteralStringNode;
import ast.interfaces.ExpressionNode;
import ast.statements.Type;
import lexer.Token;
import parser.utils.TypeProvider;
import utils.LexicalRange;

import static parser.utils.ParserUtil.isLiteral;

public class ParseLiteral {

  public static ExpressionNode parse(Token token) {
    if (!isLiteral(token)) {
      throw new RuntimeException(); // TODO
    }

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

}
