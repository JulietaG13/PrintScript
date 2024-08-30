package edu.rules;

import com.google.gson.JsonObject;
import edu.functions.FunctionRule;
import edu.identifiers.IdentifierType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RuleParserLinter {
  private static final Map<String, IdentifierType> rulesPossibleIdentifiers = new HashMap<>();
  private static final Map<String, FunctionRule> rulesFunctionCalls = new HashMap<>();

  public static void addIdentifiers(Map<String, IdentifierType> identifiers) {
    rulesPossibleIdentifiers.putAll(identifiers);
  }

  public static void addFunctionRules(Map<String, FunctionRule> functionCalls) {
    rulesFunctionCalls.putAll(functionCalls);
  }

  public static RuleProviderLinter parseRules(JsonObject rules) {
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
    return new RuleProviderLinter(possibleIdentifiers, functionRules);
  }
}
