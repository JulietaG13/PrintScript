package lexer.states;

import lexer.LexerContext;
import lexer.Token;

import java.util.List;

public interface State {
  void process(LexerContext context, List<Token> tokens);
}
