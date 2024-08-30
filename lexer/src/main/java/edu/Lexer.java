package edu;

import edu.patterns.TokenPattern;
import edu.tokens.Token;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Lexer implements Iterator<Token> {
  private final String code;
  private LexicalRange currentPosition;
  private final PatternManager patterns;
  private final List<Token> tokens = new ArrayList<>();

  public Lexer(String code, List<TokenPattern> patterns) {
    this.code = code;
    this.currentPosition = new LexicalRange(0, 0, 0);
    this.patterns = new PatternManager(patterns);
  }

  @Override
  public boolean hasNext() {
    Optional<Character> currentChar = getCharAt(currentPosition);
    if (currentChar.isEmpty()) {
      return false;
    }
    Character c = currentChar.get();
    while (skipWhitespace(c)) {
      currentChar = getCharAt(currentPosition);
      if (currentChar.isEmpty()) {
        return false;
      }
      c = currentChar.get();
    }
    return currentPosition.getOffset() < code.length();
  }

  @Override
  public Token next() {
    String sub = code.substring(currentPosition.getOffset());

    Optional<Token> token = patterns.matches(sub, currentPosition);

    if (token.isEmpty()) {
      throw new RuntimeException("Invalid token: " + sub);
    } else {
      Token t = token.get();
      advancePosition(t.getEnd().getOffset() - t.getStart().getOffset() + 1);
      return t;
    }
  }

  public void tokenize() {
    while (hasNext()) {
      tokens.add(next());
    }
  }

  public List<Token> getTokens() {
    return tokens;
  }

  public void getTokens(Stream.Builder<Token> tokensStream) {
    while (hasNext()) {
      tokensStream.accept(next());
    }
  }

  private boolean skipWhitespace(Character currentChar) {
    if (Character.isWhitespace(currentChar)) {
      advancePosition(1);
      return true;
    }
    return false;
  }

  private void advancePosition(int length) {
    for (int i = 0; i < length; i++) {
      char c = code.charAt(currentPosition.getOffset());
      currentPosition =
          new LexicalRange(
              currentPosition.getOffset() + 1,
              c == '\n' ? currentPosition.getLine() + 1 : currentPosition.getLine(),
              c == '\n' ? 0 : currentPosition.getColumn() + 1);
    }
  }

  private Optional<Character> getCharAt(LexicalRange position) {
    if (position.getOffset() >= code.length()) {
      return Optional.empty();
    }
    return Optional.of(code.charAt(position.getOffset()));
  }
}
