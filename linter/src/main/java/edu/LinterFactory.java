package edu;

import com.google.gson.JsonObject;
import edu.functions.NonExpression;
import edu.identifiers.LowerCamelCase;
import edu.identifiers.Or;
import edu.identifiers.SnakeCase;
import edu.identifiers.UpperCamelCase;
import edu.rules.RuleParserLinter;
import java.util.Map;
import java.util.Set;

public class LinterFactory {

  public static Linter createLinterV1(JsonObject rules, Parser parser) {
    RuleParserLinter.addIdentifiers(
        Map.of(
            "snake case",
            new SnakeCase(),
            "camel case",
            new Or(Set.of(new LowerCamelCase(), new UpperCamelCase()))));

    RuleParserLinter.addFunctionRules(
        Map.of("mandatory-variable-or-literal-in-println", new NonExpression("println")));

    return new Linter(RuleParserLinter.parseRules(rules), parser);
  }

  public static Linter createLinterV2(JsonObject rules, Parser parser) {
    RuleParserLinter.addIdentifiers(
        Map.of(
            "snake case",
            new SnakeCase(),
            "camel case",
            new Or(Set.of(new LowerCamelCase(), new UpperCamelCase()))));

    RuleParserLinter.addFunctionRules(
        Map.of(
            "mandatory-variable-or-literal-in-println",
            new NonExpression("println"),
            "mandatory-variable-or-literal-in-readInput",
            new NonExpression("readInput")));

    return new Linter(RuleParserLinter.parseRules(rules), parser);
  }
}
