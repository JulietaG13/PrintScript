package edu.rules;

import java.util.List;

public class RuleProviderV1 implements RuleProvider {
  @Override
  public List<Rule> getAssignmentRules() {
    return RuleFactory.getAssignmentRulesForVersion1();
  }

  @Override
  public List<Rule> getDeclarationRules() {
    return RuleFactory.getDeclarationRulesForVersion1();
  }
}
