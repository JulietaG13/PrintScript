package edu;

import static edu.LexerFactory.createLexerV1;
import static edu.LinterFactory.createLinterV1;
import static edu.ParserFactory.createParserV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestPrintRules {

  private static final JsonObject noRules;
  private static final JsonObject nonExpression;

  static {
    noRules = new JsonObject();

    nonExpression = new JsonObject();
    nonExpression.addProperty("identifier_format", "camel case");
    nonExpression.addProperty("mandatory-variable-or-literal-in-println", true);
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
  public void testNoRules() {
    String code = "println(10);";
    Linter linter = createLinter(code, noRules);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testLiteralNumberPrint() {
    String code = "println(10);";
    Linter linter = createLinter(code, nonExpression);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testIdentifierPrint() {
    String code = "println(hello);";
    Linter linter = createLinter(code, nonExpression);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testLiteralStringPrint() {
    String code = "println(\"Hello\");";
    Linter linter = createLinter(code, nonExpression);
    Report report = linter.analyze();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testExpressionError() {
    String code = "println(\"Hello\" + \"World\");";
    Linter linter = createLinter(code, nonExpression);
    Report report = linter.analyze();
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
    Linter linter = createLinter(code, nonExpression);
    Report report = linter.analyze();
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
