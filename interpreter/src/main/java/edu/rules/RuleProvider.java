package edu.rules;

import java.util.List;

public interface RuleProvider {
  List<Rule> getDeclarationRules();

  List<Rule> getAssignmentRules();
}
