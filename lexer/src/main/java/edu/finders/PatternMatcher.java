package edu.finders;

import edu.patterns.TokenPattern;
import java.util.Optional;

public interface PatternMatcher {
  Optional<String> matches(String input, TokenPattern patter);
}
