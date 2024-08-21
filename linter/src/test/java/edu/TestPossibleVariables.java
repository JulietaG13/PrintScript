package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import edu.ast.ProgramNode;
import java.util.List;

import edu.rules.RuleParserLinter;
import org.junit.jupiter.api.Test;

public class TestPossibleVariables {

  private static final JsonObject snakeCase;
  private static final JsonObject upperCamelCase;
  private static final JsonObject lowerCamelCase;
  private static final JsonObject allCases;
  private static final JsonObject empty;

  static {
    empty = new JsonObject();

    snakeCase = new JsonObject();
    snakeCase.addProperty("snake_case", true);

    upperCamelCase = new JsonObject();
    upperCamelCase.addProperty("upper_camel_case", true);

    lowerCamelCase = new JsonObject();
    lowerCamelCase.addProperty("lower_camel_case", true);

    allCases = new JsonObject();
    allCases.addProperty("upper_camel_case", true);
    allCases.addProperty("lower_camel_case", true);
    allCases.addProperty("snake_case", true);

  }

  private Report processCode(String code, Linter linter) {
    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();
    Parser parser = new Parser();
    ProgramNode programNode = parser.parse(tokens, true);
    return linter.analyze(programNode);
  }

  @Test
  public void testSnakeCaseVariableDeclaration() {
    String code = "let snake_case_variable : String;";
    Linter linter = new Linter(RuleParserLinter.parseRules(snakeCase));
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testSnakeCaseVariableDeclarationError() {
    String code = "let snake_case_variable : String;";
    Linter linter = new Linter(RuleParserLinter.parseRules(empty));
    Report report = processCode(code, linter);
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Invalid identifier name: snake_case_variable at"
            + " position LexicalRange(offset=4, line=0, column=4)",
        messages.get(0));
  }

  @Test
  public void testUpperCamelCaseVariableDeclaration() {
    String code = "let UpperCamelCase : String;";
    Linter linter = new Linter(RuleParserLinter.parseRules(upperCamelCase));
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testUpperCamelCaseVariableDeclarationError() {
    String code = "let UpperCamelCase : String;";
    Linter linter = new Linter(RuleParserLinter.parseRules(empty));
    Report report = processCode(code, linter);
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Invalid identifier name: UpperCamelCase at"
            + " position LexicalRange(offset=4, line=0, column=4)",
        messages.get(0));
  }

  @Test
  public void testLowerCaseVariableDeclaration() {
    String code = "let lowerCamelCase : String;";
    Linter linter = new Linter(RuleParserLinter.parseRules(lowerCamelCase));
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testLowerCaseVariableDeclarationError() {
    String code = "let lowerCamelCase : String;";
    Linter linter = new Linter(RuleParserLinter.parseRules(empty));
    Report report = processCode(code, linter);
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Invalid identifier name: lowerCamelCase "
            + "at position LexicalRange(offset=4, line=0, column=4)",
        messages.get(0));
  }

  @Test
  public void testOrVariableDeclarationError() {
    String code = "let Camel_SnakeCaseMIX : String;";
    Linter linter = new Linter(RuleParserLinter.parseRules(allCases));
    Report report = processCode(code, linter);
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Invalid identifier name: Camel_SnakeCaseMIX "
            + "at position LexicalRange(offset=4, line=0, column=4)",
        messages.get(0));
  }

  @Test
  public void testOrVariableDeclaration() {
    String code = "let snake_case : String;";
    Linter linter = new Linter(RuleParserLinter.parseRules(allCases));
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testIdentifierAsFunctionCalls() {
    String code = "sayHallo();";
    Linter linter = new Linter(RuleParserLinter.parseRules(lowerCamelCase));
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testIdentifierInBinary() {
    String code = "let age: Number = oldAge + 10;";
    Linter linter = new Linter(RuleParserLinter.parseRules(lowerCamelCase));
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }
}
