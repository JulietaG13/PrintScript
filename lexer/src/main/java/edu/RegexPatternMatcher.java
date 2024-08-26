package edu;

import edu.tokens.TokenType;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternMatcher implements PatternMatcher {
  private final Pattern pattern;
  private final TokenType tokenType;

  public RegexPatternMatcher(Pattern pattern, TokenType tokenType) {
    this.pattern = pattern;
    this.tokenType = tokenType;
  }

  @Override
  public Optional<MatchResult> findMatch(String input) {
    Matcher matcher = pattern.matcher(input);
    if (matcher.lookingAt()) {
      return Optional.of(new MatchResult(matcher.group()));
    }
    return Optional.empty();
  }

  @Override
  public TokenType getTokenType() {
    return tokenType;
  }
}
