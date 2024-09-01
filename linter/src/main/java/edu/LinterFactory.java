package edu;

import com.google.gson.JsonObject;
import edu.functions.NonExpression;
import edu.identifiers.LowerCamelCase;
import edu.identifiers.SnakeCase;
import edu.identifiers.UpperCamelCase;
import edu.rules.RuleParserLinter;
import java.util.Map;

public class LinterFactory {

  public static Linter createLinterV1(JsonObject rules, Parser parser) {
    RuleParserLinter.addIdentifiers(
        Map.of(
            "snake_case",
            new SnakeCase(),
            "lower_camel_case",
            new LowerCamelCase(),
            "upper_camel_case",
            new UpperCamelCase()));
    RuleParserLinter.addFunctionRules(
        Map.of("println_non_expressions", new NonExpression("println")));

    return new Linter(RuleParserLinter.parseRules(rules), parser);
  }

  public static Linter createLinterV2(JsonObject rules, Parser parser) {
    RuleParserLinter.addIdentifiers(
        Map.of(
            "snake_case", new SnakeCase(),
            "lower_camel_case", new LowerCamelCase(),
            "upper_camel_case", new UpperCamelCase()));

    RuleParserLinter.addFunctionRules(
        Map.of(
            "println_non_expressions", new NonExpression("println"),
            "read_input_non_expressions", new NonExpression("readInput")));

    return new Linter(RuleParserLinter.parseRules(rules), parser);
  }
}
