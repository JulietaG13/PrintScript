package edu.rules;

import java.util.Map;
import java.util.Set;

public class FormatterRuleProvider {
  private final Set<String> spaceBefore;
  private final Set<String> spaceAfter;
  private final Map<String, Integer> newLineBefore;
  private final int indent;

  public FormatterRuleProvider(
      Set<String> spaceBefore, Set<String> spaceAfter, Map<String, Integer> newLineBefore) {
    this.spaceBefore = spaceBefore;
    this.spaceAfter = spaceAfter;
    this.newLineBefore = newLineBefore;
    this.indent = 4; // default
  }

  public FormatterRuleProvider(
      Set<String> spaceBefore,
      Set<String> spaceAfter,
      Map<String, Integer> newLineBefore,
      int indent) {
    this.spaceBefore = spaceBefore;
    this.spaceAfter = spaceAfter;
    this.newLineBefore = newLineBefore;
    this.indent = indent;
  }

  public boolean hasSpaceBefore(String string) {
    return spaceBefore.contains(string);
  }

  public boolean hasSpaceAfter(String string) {
    return spaceAfter.contains(string);
  }

  public int newLineBefore(String string) {
    return newLineBefore.getOrDefault(string, 0);
  }

  public int getIndentLevel() {
    return indent;
  }
}

/* Configurable
v1.0
* declaration_space_before_colon
* declaration_space_after_colon
* assignment_space_before_equals
* assignment_space_after_equals
* println_new_lines_before_call
v1.1
* indent
*/
