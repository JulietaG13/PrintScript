package lexer.states;

import lexer.LexerContext;
import lexer.Token;

import java.util.List;

public class StartState implements State {
  @Override
  public void process(LexerContext context, List<Token> tokens) {
    char currentChar = context.getCurrentChar();
    if (Character.isWhitespace(currentChar)) {
      context.advance();
    }
    if (Character.isLetter(currentChar)) {
      context.setState(new KeywordOrIdentifierState());
    }
    if (Character.isDigit(currentChar)) {
      context.setState(new IntegerLiteralState());
    }
    if (context.getCurrentChar() == '"') {
      context.advance();
      context.setState(new StringLiteralState());
    }
  }
}
