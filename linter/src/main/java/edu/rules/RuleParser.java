package edu.rules;

import com.google.gson.JsonObject;
import edu.functions.FunctionRule;
import edu.identifiers.IdentifierType;
import edu.functions.NonExpressionPrintln;
import edu.identifiers.LowerCamelCase;
import edu.identifiers.SnakeCase;
import edu.identifiers.UpperCamelCase;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RuleParser {

  private static final Map<String, IdentifierType> rulesPossibleIdentifiers =
    Map.of(
      "snake_case", new SnakeCase(),
      "lower_camel_case", new LowerCamelCase(),
      "upper_camel_case", new UpperCamelCase());

  private static final Map<String, FunctionRule> rulesFunctionCalls =
    Map.of(
      "println_non_expressions", new NonExpressionPrintln());

  public static RuleProvider parseRules(JsonObject rules) {
    Set<IdentifierType> possibleIdentifiers = new HashSet<>();
    Set<FunctionRule> functionRules = new HashSet<>();

    for (String key : rules.keySet()) {
      if (rules.has(key) && rules.get(key).getAsBoolean()) {
        if (rulesPossibleIdentifiers.containsKey(key)) {
          possibleIdentifiers.add(rulesPossibleIdentifiers.get(key));
        } else if (rulesFunctionCalls.containsKey(key)) {
          functionRules.add(rulesFunctionCalls.get(key));
        }
      }
    }

    return new RuleProvider(possibleIdentifiers, functionRules);
  }
}
