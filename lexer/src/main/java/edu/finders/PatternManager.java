package edu.finders;

import edu.LexicalRange;
import edu.patterns.TokenPattern;
import edu.tokens.Token;
import edu.tokens.TokenType;
import java.util.List;
import java.util.Optional;

public class PatternManager {
  private final List<TokenPattern> patterns;
  private final PatternMatcher patternMatcher;

  public PatternManager(List<TokenPattern> patterns, PatternMatcher patternMatcher) {
    this.patterns = patterns;
    this.patternMatcher = patternMatcher;
  }

  /* Iterates over all patterns and looks for a match */
  public Optional<Token> findMatch(String code, LexicalRange position) {
    for (TokenPattern pattern : patterns) {
      Optional<String> matchResult = patternMatcher.matches(code, pattern);
      if (matchResult.isPresent()) {
        String tokenValue = matchResult.get();
        Token token = createToken(tokenValue, pattern.getTokenType(), position);
        return Optional.of(token);
      }
    }
    return Optional.empty();
  }

  /* Creates a token with the given value, type and position */
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
