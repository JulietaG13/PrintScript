package edu;

import edu.tokens.Token;
import edu.tokens.TokenType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Lexer implements Iterator<Token> {
  private final String code;
  private LexicalRange currentPosition;
  private final PatternManager patterns;
  private final List<Token> tokens = new ArrayList<>();

  public Lexer(String code) {
    this.code = code;
    this.currentPosition = new LexicalRange(0, 0, 0);
    this.patterns = new PatternManager();
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
    while (currentPosition.getOffset() < code.length()) {
      Optional<Character> currentChar = getCharAt(currentPosition);

      if (currentChar.isEmpty()) {
        throw new RuntimeException("Limit exceeded");
      }

      Character c = currentChar.get();

      if (skipWhitespace(c)) {
        continue;
      }

      for (Map.Entry<PatternMatcher, TokenType> entry : patterns.getMatchers().entrySet()) {
        PatternMatcher matcher = entry.getKey();

        String sub = code.substring(currentPosition.getOffset());
        Optional<MatchResult> matchResult = matcher.findMatch(sub);

        if (matchResult.isPresent()) {
          String tokenValue = matchResult.get().getMatchedValue();

          if (matcher.getTokenType() == TokenType.KEYWORD) {
            if (!isWholeWordMatch(tokenValue)) {
              continue;
            }
          }

          Token token = createToken(tokenValue, matcher.getTokenType());
          advancePosition(tokenValue.length());
          return token;
        }
      }

      throw new RuntimeException("Invalid token: " + currentChar.get());
    }
    throw new RuntimeException("No more tokens available");
  }

  public void tokenize() {
    while (hasNext()) {
      tokens.add(next());
    }
  }

  public List<Token> getTokens() {
    return tokens;
  }

  private Token createToken(String tokenValue, TokenType type) {
    LexicalRange startRange =
        new LexicalRange(
            currentPosition.getOffset(), currentPosition.getLine(), currentPosition.getColumn());
    LexicalRange endRange =
        new LexicalRange(
            currentPosition.getOffset() + tokenValue.length() - 1,
            currentPosition.getLine(),
            currentPosition.getColumn() + tokenValue.length() - 1);
    return new Token(type, tokenValue, startRange, endRange);
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

  private boolean isWholeWordMatch(String tokenValue) {
    int endOffset = currentPosition.getOffset() + tokenValue.length();
    if (endOffset < code.length()) {
      char nextChar = code.charAt(endOffset);
      return !Character.isLetterOrDigit(nextChar) && nextChar != '_';
    }
    return true;
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
