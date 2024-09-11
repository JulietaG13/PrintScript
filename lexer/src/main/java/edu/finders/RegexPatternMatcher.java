package edu.finders;

import edu.patterns.TokenPattern;
import java.util.Optional;
import java.util.regex.Matcher;

public class RegexPatternMatcher implements PatternMatcher {
  /* Given a pattern checks if a prefix of the input matches with the given pattern */
  @Override
  public Optional<String> matches(String input, TokenPattern pattern) {
    Matcher matcher = pattern.getPattern().matcher(input);
    if (matcher.lookingAt()) {
      return Optional.of(matcher.group());
    }
    return Optional.empty();
  }
}
