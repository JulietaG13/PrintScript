package edu;

import edu.tokens.TokenType;
import java.util.Optional;

public interface PatternMatcher {
  Optional<MatchResult> findMatch(String input);

  TokenType getTokenType();
}
