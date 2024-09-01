package edu;

import static edu.LexerFactory.createLexerV2;
import static edu.LinterFactory.createLinterV2;
import static edu.ParserFactory.createParserV2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestLinterV2 {

  private static final JsonObject nonExpression;
  private static final JsonObject snakeCase;
  private static final JsonObject upperCamelCase;
  private static final JsonObject lowerCamelCase;
  private static final JsonObject allCases;
  private static final JsonObject empty;

  static {
    nonExpression = new JsonObject();
    nonExpression.addProperty("lower_camel_case", true);
    nonExpression.addProperty("read_input_non_expressions", true);

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

  private Iterator<String> createIteratorFromString(String code) {
    return new BufferedReader(new java.io.StringReader(code)).lines().iterator();
  }

  private Linter createLinter(String code, JsonObject rules) {
    Iterator<String> codeIterator = createIteratorFromString(code);
    Lexer lexer = createLexerV2(codeIterator);
    Parser parser = createParserV2(lexer);
    return createLinterV2(rules, parser);
  }

  @Test
  public void testReadInputNonExpression() {
    String input = "readInput(\"Enter a \" + \"number: \");";
    Linter linter = createLinter(input, nonExpression);
    Report report = linter.analyze();
    assertTrue(report.getReport().isPresent());
    assertEquals(1, report.getReport().get().size());
    assertEquals(
        "Error in println function: The readInput function only "
            + "accepts identifiers or literals as arguments.\n"
            + "Argument 1 is invalid:\n"
            + " - Type: BinaryExpressionNode\n"
            + " - Position: LexicalRange(offset=10, line=0, column=10)\n"
            + " - Content: \"Enter a \" + \"number: \"",
        report.getReport().get().get(0));
  }

  @Test
  public void testSnakeCaseVariableDeclaration() {
    String code = "const snake_case_variable : string;";
    Linter linter = createLinter(code, snakeCase);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testSnakeCaseVariableDeclarationError() {
    String code = "const snake_case_variable : string;";
    Linter linter = createLinter(code, empty);
    Report report = linter.analyze();
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Invalid identifier name: snake_case_variable at"
            + " position LexicalRange(offset=6, line=0, column=6)",
        messages.get(0));
  }

  @Test
  public void testUpperCamelCaseVariableDeclaration() {
    String code = "const UpperCamelCase : string;";
    Linter linter = createLinter(code, upperCamelCase);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testUpperCamelCaseVariableDeclarationError() {
    String code = "const UpperCamelCase : string;";
    Linter linter = createLinter(code, empty);
    Report report = linter.analyze();
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Invalid identifier name: UpperCamelCase "
            + "at position LexicalRange(offset=6, line=0, column=6)",
        messages.get(0));
  }

  @Test
  public void testLowerCaseVariableDeclaration() {
    String code = "const lowerCamelCase : string;";
    Linter linter = createLinter(code, lowerCamelCase);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testLowerCaseVariableDeclarationError() {
    String code = "const lowerCamelCase : string;";
    Linter linter = createLinter(code, empty);
    Report report = linter.analyze();
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Invalid identifier name: lowerCamelCase "
            + "at position LexicalRange(offset=6, line=0, column=6)",
        messages.get(0));
  }

  @Test
  public void testOrVariableDeclarationError() {
    String code = "const Camel_SnakeCaseMIX : string;";
    Linter linter = createLinter(code, allCases);
    Report report = linter.analyze();
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals(
        "Invalid identifier name: Camel_SnakeCaseMIX "
            + "at position LexicalRange(offset=6, line=0, column=6)",
        messages.get(0));
  }

  @Test
  public void testOrVariableDeclaration() {
    String code = "const snake_case : string;";
    Linter linter = createLinter(code, allCases);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testIdentifierAsFunctionCalls() {
    String code = "sayHallo();";
    Linter linter = createLinter(code, lowerCamelCase);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testIdentifierInBinary() {
    String code = "const oldAge : number = 5; let age: number = oldAge + 10;";
    Linter linter = createLinter(code, lowerCamelCase);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }
}
