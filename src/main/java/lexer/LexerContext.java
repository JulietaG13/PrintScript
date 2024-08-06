package lexer;

import lexer.states.StartState;
import lexer.states.State;

import java.util.List;

public class LexerContext {
  private final String code;
  private int position;
  private int line;
  private int column;
  private char currentChar;
  private State state;

  public LexerContext(String code) {
    this.code = code;
    this.position = 0;
    this.line = 0;
    this.column = 0;
    this.currentChar = code.charAt(position);
    this.state = new StartState();
  }

  public char getCurrentChar() {
    return currentChar;
  }

  public int getPosition() {
    return position;
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }

  public void advance() {
    position++;
    column++;
    if (position < code.length()) {
      currentChar = code.charAt(position);
      if (currentChar == '\n') {
        line++;
        column = 0;
      }
    } else {
      currentChar = '\0';
    }
  }

  public void setState(State state) {
    this.state = state;
  }

  public void tokenize(List<Token> tokens) {
    while (currentChar != '\0') {
      state.process(this, tokens);
    }
  }
}

