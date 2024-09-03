package edu;

import static edu.LexerFactory.createLexerV1;
import static edu.LinterFactory.createLinterV1;
import static edu.ParserFactory.createParserV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestPossibleVariables {

  private static final JsonObject snakeCase;
  private static final JsonObject upperCamelCase;
  private static final JsonObject lowerCamelCase;
  private static final JsonObject allCases;

  static {
    snakeCase = new JsonObject();
    snakeCase.addProperty("identifier_format", "snake case");

    upperCamelCase = new JsonObject();
    upperCamelCase.addProperty("identifier_format", "camel case");

    lowerCamelCase = new JsonObject();
    lowerCamelCase.addProperty("identifier_format", "camel case");

    allCases = new JsonObject();
  }

  private InputStream createInputStreamFromString(String code) {
    return new ByteArrayInputStream(code.getBytes());
  }

  private Linter createLinter(String code, JsonObject rules) {
    InputStream codeIterator = createInputStreamFromString(code);
    Lexer lexer = createLexerV1(codeIterator);
    Parser parser = createParserV1(lexer);
    return createLinterV1(rules, parser);
  }

  @Test
  public void testSnakeCaseVariableDeclaration() {
    String code = "let snake_case_variable : string;";
    Linter linter = createLinter(code, snakeCase);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testUpperCamelCaseVariableDeclaration() {
    String code = "let UpperCamelCase : string;";
    Linter linter = createLinter(code, upperCamelCase);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testLowerCaseVariableDeclaration() {
    String code = "let lowerCamelCase : string;";
    Linter linter = createLinter(code, lowerCamelCase);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testOrVariableDeclarationError() {
    String code = "let Camel_SnakeCaseMIX : string;";
    Linter linter = createLinter(code, allCases);
    Report report = linter.analyze();
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
    String code = "let snake_case : string;";
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
    String code = "let oldAge : number = 5; let age: number = oldAge + 10;";
    Linter linter = createLinter(code, lowerCamelCase);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }
}
