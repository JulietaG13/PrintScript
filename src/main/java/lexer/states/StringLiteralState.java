package lexer.states;

import lexer.LexerContext;
import lexer.Token;
import lexer.TokenType;
import utils.LexicalRange;

import java.util.List;

public class StringLiteralState implements State {

  @Override
  public void process(LexerContext context, List<Token> tokens) {
    LexicalRange start = new LexicalRange(context.getPosition(), context.getLine(), context.getColumn());
    StringBuilder result = new StringBuilder();
    while (context.getCurrentChar() != '\0' && context.getCurrentChar() != '"'){
      result.append(context.getCurrentChar());
      context.advance();
    }
    if (context.getCurrentChar() == '"') {
      context.advance();
      LexicalRange end = new LexicalRange(context.getPosition(), context.getLine(), context.getColumn());
      tokens.add(new Token(TokenType.STRING_LITERAL, result.toString(), start, end));
      while (Character.isWhitespace(context.getCurrentChar())) {
        context.advance();
      }
      if (context.getCurrentChar() == ';') {
        // Moverse al syntax
        context.setState(new StartState());
      } else if (context.getCurrentChar() == '+') {
        // Moverse a concatenación de strings
      } else {
        throw new RuntimeException("Invalid character as string literal state. ");
      }
    } else {
      throw new RuntimeException("Invalid character as string literal state. ");
    }
  }
}
