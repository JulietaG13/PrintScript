package edu;

import static edu.LexerFactory.createLexerV1;
import static edu.LinterFactory.createLinterV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import edu.ast.ProgramNode;
import edu.tokens.Token;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestPrintRules {

  private static final JsonObject noRules;
  private static final JsonObject nonExpression;

  static {
    noRules = new JsonObject();
    noRules.addProperty("lower_camel_case", true);

    nonExpression = new JsonObject();
    nonExpression.addProperty("lower_camel_case", true);
    nonExpression.addProperty("println_non_expressions", true);
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
  public void testNoRules() {
    String code = "println(10);";
    Linter linter = createLinterV1(noRules);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testLiteralNumberPrint() {
    String code = "println(10);";
    Linter linter = createLinterV1(nonExpression);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testIdentifierPrint() {
    String code = "println(hello);";
    Linter linter = createLinterV1(nonExpression);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testLiteralStringPrint() {
    String code = "println(\"Hello\");";
    Linter linter = createLinterV1(nonExpression);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testExpressionError() {
    String code = "println(\"Hello\" + \"World\");";
    Linter linter = createLinterV1(nonExpression);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isPresent());
    List<String> messages = report.getReport().get();
    String expectedOutput =
        "Error in println function: The println function "
            + "only accepts identifiers or literals as arguments.\n"
            + "Argument 1 is invalid:\n"
            + " - Type: BinaryExpressionNode\n"
            + " - Position: LexicalRange(offset=8, line=0, column=8)\n"
            + " - Content: \"Hello\" + \"World\"";
    assertEquals(messages.size(), 1);
    assertEquals(messages.get(0), expectedOutput);
  }

  @Test
  public void testExpressionCallError() {
    String code = "println(hello());";
    Linter linter = createLinterV1(nonExpression);
    Report report = processCode(code, linter);
    assertTrue(report.getReport().isPresent());
    String expectedOutput =
        "Error in println function: The println function only "
            + "accepts identifiers or literals as arguments.\n"
            + "Argument 1 is invalid:\n"
            + " - Type: CallExpressionNode\n"
            + " - Position: LexicalRange(offset=8, line=0, column=8)\n"
            + " - Content: hello()";
    List<String> messages = report.getReport().get();
    assertEquals(messages.size(), 1);
    assertEquals(messages.get(0), expectedOutput);
  }
}
