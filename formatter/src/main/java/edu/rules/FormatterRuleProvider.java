package edu.rules;

import java.util.Map;
import java.util.Set;

public class FormatterRuleProvider {
  private final Set<String> spaceBefore;
  private final Set<String> spaceAfter;
  private final Map<String, Integer> newLineBefore;

  public FormatterRuleProvider(
      Set<String> spaceBefore, Set<String> spaceAfter, Map<String, Integer> newLineBefore) {
    this.spaceBefore = spaceBefore;
    this.spaceAfter = spaceAfter;
    this.newLineBefore = newLineBefore;
  }

  public boolean hasSpaceBefore(String string) {
    return spaceBefore.contains(string);
  }

  public boolean hasSpaceAfter(String string) {
    return spaceAfter.contains(string);
  }

  public int newLineBefore(String string) {
    return newLineBefore.get(string);
  }
}

/* Configurable
 * declaration_space_before_colon
 * declaration_space_after_colon
 * assignment_space_before_equals
 * assignment_space_after_equals
 * println_new_lines_before_call
 */
