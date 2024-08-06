package lexer.states;

import lexer.LexerContext;
import lexer.Token;
import lexer.TokenType;
import utils.LexicalRange;

import java.util.List;

public class KeywordOrIdentifierState implements State {
  @Override
  public void process(LexerContext context, List<Token> tokens) {
    StringBuilder result = new StringBuilder();
    LexicalRange start = new LexicalRange(context.getPosition(), context.getLine(), context.getColumn());
    while (context.getCurrentChar() != '\0' && Character.isLetterOrDigit(context.getCurrentChar())) {
      result.append(context.getCurrentChar());
      context.advance();
    }
    LexicalRange end = new LexicalRange(context.getPosition(), context.getLine(), context.getColumn());
    while (Character.isWhitespace(context.getCurrentChar())) {
      context.advance();
    }
    if (context.getCurrentChar() == '=') {
      context.advance();
      tokens.add(new Token(TokenType.IDENTIFIER, result.toString(), start, end));
      context.setState(new LiteralState());
    } else if (Character.isLetter(context.getCurrentChar())){
      tokens.add(new Token(TokenType.KEYWORD, result.toString(), start, end));
    }
  }
}
