package edu;

import edu.patterns.TokenPattern;
import edu.tokens.Token;
import edu.tokens.TokenType;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PatternManager {
  private final List<PatternMatcher> matchers;

  public PatternManager(List<TokenPattern> patterns) {
    this.matchers = new LinkedList<>();
    for (TokenPattern pattern : patterns) {
      RegexPatternMatcher regexPatternMatcher =
          new RegexPatternMatcher(pattern.getPattern(), pattern.getTokenType());
      matchers.add(regexPatternMatcher);
    }
  }

  public Optional<Token> matches(String code, LexicalRange position) {
    for (PatternMatcher matcher : matchers) {

      Optional<MatchResult> matchResult = matcher.findMatch(code);

      if (matchResult.isPresent()) {
        String tokenValue = matchResult.get().getMatchedValue();

        if (matcher.getTokenType() == TokenType.KEYWORD) {
          if (!isWholeWordMatch(tokenValue, code)) {
            continue;
          }
        }

        Token token = createToken(tokenValue, matcher.getTokenType(), position);
        return Optional.of(token);
      }
    }
    return Optional.empty();
  }

  private boolean isWholeWordMatch(String tokenValue, String code) {
    int endOffset = tokenValue.length();
    if (endOffset < code.length()) {
      char nextChar = code.charAt(endOffset);
      return !Character.isLetterOrDigit(nextChar) && nextChar != '_';
    }
    return true;
  }

  private Token createToken(String tokenValue, TokenType type, LexicalRange currentPosition) {
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
}
