package edu.rules;

import edu.rules.assignments.VerifyConstantAssignmentRule;
import edu.rules.assignments.VerifyTypeCompatibilityRule;
import edu.rules.declarations.VerifyConstantNotRedefinedRule;
import edu.rules.declarations.VerifyCorrectTypeAssignmentRule;
import edu.rules.declarations.VerifyVariableNotRedefinedRule;
import java.util.ArrayList;
import java.util.List;

public class RuleFactory {
  public static List<Rule> getAssignmentRulesForVersion1() {
    List<Rule> rules = new ArrayList<>();

    rules.add(new VerifyTypeCompatibilityRule());
    return rules;
  }

  public static List<Rule> getAssignmentRulesForVersion11() {
    List<Rule> rules = new ArrayList<>();
    rules.add(new VerifyTypeCompatibilityRule());
    rules.add(new VerifyConstantAssignmentRule());
    return rules;
  }

  public static List<Rule> getDeclarationRulesForVersion1() {
    List<Rule> rules = new ArrayList<>();
    rules.add(new VerifyCorrectTypeAssignmentRule());
    rules.add(new VerifyVariableNotRedefinedRule());
    return rules;
  }

  public static List<Rule> getDeclarationRulesForVersion11() {
    List<Rule> rules = new ArrayList<>();
    rules.add(new VerifyCorrectTypeAssignmentRule());
    rules.add(new VerifyConstantNotRedefinedRule());
    rules.add(new VerifyVariableNotRedefinedRule());
    return rules;
  }
}
