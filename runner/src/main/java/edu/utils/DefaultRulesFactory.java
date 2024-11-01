package edu.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DefaultRulesFactory {
  String version;

  public DefaultRulesFactory(String version) {
    this.version = version;
  }

  public JsonObject getDefaultFormattingRules() {
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

  private JsonObject getDefaultFormattingRulesV1() {
    JsonObject jsonDefaultRules = new JsonObject();
    jsonDefaultRules.addProperty("declaration_space_before_colon", false);
    jsonDefaultRules.addProperty("declaration_space_after_colon", false);
    jsonDefaultRules.addProperty("assignment_space_before_equals", false);
    jsonDefaultRules.addProperty("assignment_space_after_equals", false);
    jsonDefaultRules.addProperty("println_new_lines_before_call", 1);
    return jsonDefaultRules;
  }

  private JsonObject getDefaultFormattingRulesV2() {
    JsonObject jsonDefaultRules = new JsonObject();
    jsonDefaultRules.addProperty("declaration_space_before_colon", false);
    jsonDefaultRules.addProperty("declaration_space_after_colon", false);
    jsonDefaultRules.addProperty("assignment_space_before_equals", false);
    jsonDefaultRules.addProperty("assignment_space_after_equals", false);
    jsonDefaultRules.addProperty("println_new_lines_before_call", 1);
    jsonDefaultRules.addProperty("indent", 4);
    return jsonDefaultRules;
  }

  private JsonObject getDefaultLintingRulesV1() {
    JsonObject lintingRules = new JsonObject();
    JsonArray identifierFormats = new JsonArray();
    identifierFormats.add("camel case");
    lintingRules.add("identifier_format", identifierFormats);
    lintingRules.addProperty("mandatory-variable-or-literal-in-println", false);
    lintingRules.addProperty("mandatory-variable-or-literal-in-readInput", false);
    return lintingRules;
  }

  private JsonObject getDefaultLintingRulesV2() {
    JsonObject lintingRules = new JsonObject();
    JsonArray identifierFormats = new JsonArray();
    identifierFormats.add("camel case");
    lintingRules.add("identifier_format", identifierFormats);
    lintingRules.addProperty("mandatory-variable-or-literal-in-println", false);
    return lintingRules;
  }
}
