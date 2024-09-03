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
    if (rules.keySet().contains("identifier_format")) {
      String value = rules.get("identifier_format").getAsString();
      if (rulesPossibleIdentifiers.containsKey(value)) {
        possibleIdentifiers.add(rulesPossibleIdentifiers.get(value));
      } else {
        throw new RuntimeException("Invalid identifier format: " + value);
      }
    } else {
      possibleIdentifiers.addAll(rulesPossibleIdentifiers.values());
    }

    for (String key : rules.keySet()) {
      if (!key.equals("identifier_format")) {
        if (rulesFunctionCalls.containsKey(key) && rules.get(key).getAsBoolean()) {
          functionRules.add(rulesFunctionCalls.get(key));
        } else {
          throw new RuntimeException("Invalid function rule: " + key);
        }
      }
    }
    return new RuleProviderLinter(possibleIdentifiers, functionRules);
  }
}
