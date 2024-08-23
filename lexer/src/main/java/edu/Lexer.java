package edu;

import edu.tokens.Token;
import edu.tokens.TokenType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Lexer {
  private final String code;
  private LexicalRange currentPosition;
  private final PatternManager patterns;
  private List<Token> tokens;

  public Lexer(String code) {
    this.code = code;
    this.currentPosition = new LexicalRange(0, 0, 0);
    this.tokens = new ArrayList<>();
    this.patterns = new PatternManager();
  }

  public List<Token> getTokens() {
    return tokens;
  }

  public void tokenize() {
    while (currentPosition.getOffset() < code.length()) {
      Optional<Character> currentChar = getCharAt(currentPosition);

      if (currentChar.isEmpty()) {
        break;
      }
      Character c = currentChar.get();

      if (skipWhitespace(c)) {
        continue;
      }

      boolean matched = false;

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

          createToken(tokenValue, matcher.getTokenType());
          advancePosition(tokenValue.length());
          matched = true;
          break;
        }
      }

      if (!matched) {
        throw new RuntimeException("Invalid token: " + currentChar.get());
      }
    }
  }

  private void createToken(String tokenValue, TokenType type) {
    LexicalRange startRange =
        new LexicalRange(
            currentPosition.getOffset(), currentPosition.getLine(), currentPosition.getColumn());
    LexicalRange endRange =
        new LexicalRange(
            currentPosition.getOffset() + tokenValue.length() - 1,
            currentPosition.getLine(),
            currentPosition.getColumn() + tokenValue.length() - 1);
    tokens.add(new Token(type, tokenValue, startRange, endRange));
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
