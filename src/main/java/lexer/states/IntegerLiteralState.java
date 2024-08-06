package lexer.states;

import lexer.*;
import utils.LexicalRange;

import java.util.List;

public class IntegerLiteralState implements State {

  @Override
  public void process(LexerContext context, List<Token> tokens) {
    StringBuilder result = new StringBuilder();
    LexicalRange start = new LexicalRange(context.getPosition(), context.getLine(), context.getColumn());
    while (Character.isDigit(context.getCurrentChar())) {
      result.append(context.getCurrentChar());
      context.advance();
    }
    LexicalRange end = new LexicalRange(context.getPosition(), context.getLine(), context.getColumn());
    tokens.add(new Token(TokenType.INTEGER_LITERAL, result.toString(), start, end));
    while (Character.isWhitespace(context.getCurrentChar())) {
      context.advance();
    }
    if (context.getCurrentChar() == ';') {
      // Moverse al syntax
      context.setState(new StartState());
    }
    else if (context.getCurrentChar() == '+' || context.getCurrentChar() == '-' || context.getCurrentChar() == '*' || context.getCurrentChar() == '/') {
      // Moverse al operaciones
    }
    else {
      throw new RuntimeException("Invalid character as integer literal state. ");
    }
  }
}
