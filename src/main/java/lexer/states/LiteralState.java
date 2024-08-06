package lexer.states;

import lexer.LexerContext;
import lexer.Token;

import java.util.List;

public class LiteralState implements State {
  @Override
  public void process(LexerContext context, List<Token> tokens) {
    if (Character.isDigit(context.getCurrentChar())) {
      context.setState(new IntegerLiteralState());
    }
    if (context.getCurrentChar() == '"') {
      context.advance();
      context.setState(new StringLiteralState());
    }
    else {
      throw new RuntimeException("Invalid character as literal state. ");
    }
  }
}
