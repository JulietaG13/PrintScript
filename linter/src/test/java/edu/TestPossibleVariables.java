package edu;

import edu.ast.ProgramNode;
import edu.identifiers.LowerCamelCase;
import edu.identifiers.SnakeCase;
import edu.identifiers.UpperCamelCase;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestPossibleVariables {
  private Report processCode(String code, Linter linter) {
    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();
    Parser parser = new Parser();
    ProgramNode programNode = parser.parse(tokens);
    return linter.analyze(programNode);
  }

  @Test
  public void testSnakeCaseVariableDeclaration() {
    String code = "let snake_case_variable : String;";
    Linter linter = new Linter();
    linter = linter.addIdentifierType(new SnakeCase());
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testSnakeCaseVariableDeclarationError() {
    String code = "let snake_case_variable : String;";
    Linter linter = new Linter();
    Report report = processCode(code, linter);
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals("Invalid identifier name: snake_case_variable at position LexicalRange(offset=4, line=0, column=4)", messages.get(0));
  }

  @Test
  public void testUpperCamelCaseVariableDeclaration() {
    String code = "let UpperCamelCase : String;";
    Linter linter = new Linter();
    linter = linter.addIdentifierType(new UpperCamelCase());
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testUpperCamelCaseVariableDeclarationError() {
    String code = "let UpperCamelCase : String;";
    Linter linter = new Linter();
    Report report = processCode(code, linter);
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals("Invalid identifier name: UpperCamelCase at position LexicalRange(offset=4, line=0, column=4)", messages.get(0));
  }

  @Test
  public void testLowerCaseVariableDeclaration() {
    String code = "let lowerCamelCase : String;";
    Linter linter = new Linter();
    linter = linter.addIdentifierType(new LowerCamelCase());
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testLowerCaseVariableDeclarationError() {
    String code = "let lowerCamelCase : String;";
    Linter linter = new Linter();
    Report report = processCode(code, linter);
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals("Invalid identifier name: lowerCamelCase at position LexicalRange(offset=4, line=0, column=4)", messages.get(0));
  }

  @Test
  public void testOrVariableDeclarationError(){
    String code = "let Camel_SnakeCaseMIX : String;";
    Linter linter = new Linter();
    linter = linter.addIdentifierType(new LowerCamelCase());
    linter = linter.addIdentifierType(new UpperCamelCase());
    linter = linter.addIdentifierType(new SnakeCase());
    Report report = processCode(code, linter);
    assertFalse(report.getReport().isEmpty());
    List<String> messages = report.getMessages();
    assertEquals(1, messages.size());
    assertEquals("Invalid identifier name: Camel_SnakeCaseMIX at position LexicalRange(offset=4, line=0, column=4)", messages.get(0));
  }

  @Test
  public void testOrVariableDeclaration(){
    String code = "let snake_case : String;";
    Linter linter = new Linter();
    linter = linter.addIdentifierType(new LowerCamelCase());
    linter = linter.addIdentifierType(new UpperCamelCase());
    linter = linter.addIdentifierType(new SnakeCase());
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testIdentifierAsFunctionCalls() {
    String code = "sayHallo();";
    Linter linter = new Linter();
    linter = linter.addIdentifierType(new LowerCamelCase());
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testIdentifierInBinart() {
    String code = "let age: Number = oldAge + 10;";
    Linter linter = new Linter();
    linter = linter.addIdentifierType(new LowerCamelCase());
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }
}
