package parser.parsers.expressions;

import ast.interfaces.ExpressionNode;
import lexer.Token;
import parser.parsers.ExpressionParser;
import java.util.List;

public class ParseBinaryExpression implements ExpressionParser {

  @Override
  public ExpressionNode parse(List<Token> tokens) {   // TODO
    return null;
  }

  @Override
  public boolean isXExpression(List<Token> tokens) {  // TODO
    return false;
  }

}
