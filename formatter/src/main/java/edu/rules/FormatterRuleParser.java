package edu.rules;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FormatterRuleParser {

  private static Map<String, String> rulesSpaceBefore =
      Map.of(
          "declaration_space_before_colon", ":",
          "assignment_space_before_equals", "=");

  private static Map<String, String> rulesSpaceAfter =
      Map.of(
          "declaration_space_after_colon", ":",
          "assignment_space_after_equals", "=");

  private static Map<String, String> rulesNewLinesBefore =
      Map.of("println_new_lines_before_call", "println");

  public static FormatterRuleProvider parseRules(JsonObject rules) {
    Set<String> spaceBefore = new HashSet<>();
    Set<String> spaceAfter = new HashSet<>();
    Map<String, Integer> newLinesBefore = new HashMap<>();

    for (String key : rules.keySet()) {
      if (rulesSpaceBefore.containsKey(key)) {
        if (rules.get(key).getAsBoolean()) {
          spaceBefore.add(rulesSpaceBefore.get(key));
        }

      } else if (rulesSpaceAfter.containsKey(key)) {
        if (rules.get(key).getAsBoolean()) {
          spaceAfter.add(rulesSpaceAfter.get(key));
        }

      } else if (rulesNewLinesBefore.containsKey(key)) {
        newLinesBefore.put(rulesNewLinesBefore.get(key), rules.get(key).getAsInt());
      }
    }

    return new FormatterRuleProvider(spaceBefore, spaceAfter, newLinesBefore);
  }

  /* Configurable
   * declaration_space_before_colon
   * declaration_space_after_colon
   * assignment_space_before_equals
   * assignment_space_after_equals
   * println_new_lines_before_call
   */

  /* Not configurable
   * New line after ;
   * One space max between tokens
   * Space before and after an operator
   */
}
