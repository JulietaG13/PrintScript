package edu.rules;

import edu.functions.And;
import edu.functions.FunctionRule;
import edu.identifiers.IdentifierType;
import edu.identifiers.Or;

import java.util.Map;
import java.util.Set;

public class RuleProvider {
  private final Set<IdentifierType> possibleIdentifiers;
  private final Set<FunctionRule> functionRules;

  public RuleProvider(
    Set<IdentifierType> possibleIdentifiers, Set<FunctionRule> functionRules) {
    this.possibleIdentifiers = possibleIdentifiers;
    this.functionRules = functionRules;
  }

  public IdentifierType getPossibleIdentifiers() {
    if (possibleIdentifiers.size() == 1) {
      return possibleIdentifiers.iterator().next();
    } else {
      return new Or(possibleIdentifiers);
    }
  }

  public FunctionRule getFunctionRules() {
    if (functionRules.size() == 1) {
      return functionRules.iterator().next();
    } else {
      return new And(functionRules);
    }
  }
}
