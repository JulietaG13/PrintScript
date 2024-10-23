package edu.utils;

import com.google.gson.JsonObject;
import edu.rules.FormatterRuleParser;
import edu.rules.FormatterRuleProvider;

public class DefaultRulesFactory {
  String version;

  public DefaultRulesFactory(String version) {
    this.version = version;
  }

  public FormatterRuleProvider getDefaultFormattingRules() {
    switch (version) {
      case "1.0":
        return getDefaultFormattingRulesV1();
      case "1.1":
        return getDefaultFormattingRulesV2();
      default:
        throw new IllegalArgumentException("Invalid version: " + version);
    }
  }

  public JsonObject getDefaultLintingRules() {
    switch (version) {
      case "1.0":
        return getDefaultLintingRulesV1();
      case "1.1":
        return getDefaultLintingRulesV2();
      default:
        throw new IllegalArgumentException("Invalid version: " + version);
    }
  }

  private FormatterRuleProvider getDefaultFormattingRulesV1() {
    JsonObject jsonDefaultRules = new JsonObject();
    jsonDefaultRules.addProperty("declaration_space_before_colon", true);
    jsonDefaultRules.addProperty("declaration_space_after_colon", true);
    jsonDefaultRules.addProperty("assignment_space_before_equals", true);
    jsonDefaultRules.addProperty("assignment_space_after_equals", true);
    jsonDefaultRules.addProperty("println_new_lines_before_call", 1);
    return FormatterRuleParser.parseRules(jsonDefaultRules);
  }

  private FormatterRuleProvider getDefaultFormattingRulesV2() {
    JsonObject jsonDefaultRules = new JsonObject();
    jsonDefaultRules.addProperty("declaration_space_before_colon", true);
    jsonDefaultRules.addProperty("declaration_space_after_colon", true);
    jsonDefaultRules.addProperty("assignment_space_before_equals", true);
    jsonDefaultRules.addProperty("assignment_space_after_equals", true);
    jsonDefaultRules.addProperty("println_new_lines_before_call", 1);
    jsonDefaultRules.addProperty("indent", 4);
    return FormatterRuleParser.parseRules(jsonDefaultRules);
  }

  private JsonObject getDefaultLintingRulesV1() {
    JsonObject lintingRules = new JsonObject();
    lintingRules.addProperty("identifier_format", "camel case");
    lintingRules.addProperty("mandatory-variable-or-literal-in-readInput", true);
    return lintingRules;
  }

  private JsonObject getDefaultLintingRulesV2() {
    JsonObject lintingRules = new JsonObject();
    lintingRules.addProperty("identifier_format", "snake case");
    lintingRules.addProperty("mandatory-variable-or-literal-in-println", true);
    return lintingRules;
  }
}
