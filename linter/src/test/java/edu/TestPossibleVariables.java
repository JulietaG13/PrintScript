package edu;

import static edu.LexerFactory.createLexerV1;
import static edu.LinterFactory.createLinterV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import edu.ast.ProgramNode;
import edu.tokens.Token;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.List;
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

  private Iterator<String> createIteratorFromString(String code) {
    return new BufferedReader(new java.io.StringReader(code)).lines().iterator();
  }

  private Report processCode(String code, Linter linter) {
    Lexer lexer = createLexerV1(createIteratorFromString(code));
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();
    Parser parser = new Parser();
    ProgramNode programNode = parser.parse(tokens, true);
    return linter.analyze(programNode);
  }

  @Test
  public void testSnakeCaseVariableDeclaration() {
    String code = "let snake_case_variable : String;";
    Linter linter = createLinterV1(snakeCase);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testSnakeCaseVariableDeclarationError() {
    String code = "let snake_case_variable : String;";
    Linter linter = createLinterV1(empty);
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
    Linter linter = createLinterV1(upperCamelCase);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testUpperCamelCaseVariableDeclarationError() {
    String code = "let UpperCamelCase : String;";
    Linter linter = createLinterV1(empty);
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
    Linter linter = createLinterV1(lowerCamelCase);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testLowerCaseVariableDeclarationError() {
    String code = "let lowerCamelCase : String;";
    Linter linter = createLinterV1(empty);
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
    Linter linter = createLinterV1(allCases);
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
    Linter linter = createLinterV1(allCases);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testIdentifierAsFunctionCalls() {
    String code = "sayHallo();";
    Linter linter = createLinterV1(lowerCamelCase);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testIdentifierInBinary() {
    String code = "let age: Number = oldAge + 10;";
    Linter linter = createLinterV1(lowerCamelCase);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }
}
