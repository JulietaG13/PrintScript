package edu.rules;

import java.util.List;

public class RuleProviderV2 implements RuleProvider {
  @Override
  public List<Rule> getAssignmentRules() {
    return RuleFactory.getAssignmentRulesForVersion11();
  }

  @Override
  public List<Rule> getDeclarationRules() {
    return RuleFactory.getDeclarationRulesForVersion11();
  }
}
