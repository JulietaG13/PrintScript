package edu.tokens;

import edu.LexicalRange;

public class Token {
  private final TokenType type;
  private final String content;
  private final LexicalRange start;
  private final LexicalRange end;

  public Token(TokenType type, String content, LexicalRange start, LexicalRange end) {
    this.type = type;
    this.content = content;
    this.start = start;
    this.end = end;
  }

  public TokenType getType() {
    return type;
  }

  public String getContent() {
    return content;
  }

  public LexicalRange getStart() {
    return start;
  }

  public LexicalRange getEnd() {
    return end;
  }

  @Override
  public String toString() {
    return content;
  }

  public String tokenString() {
    return type + " " + content + " " + start.toString() + " " + end.toString();
  }
}
