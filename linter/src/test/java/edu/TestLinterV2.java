package edu;

import static edu.LexerFactory.createLexerV1;
import static edu.LinterFactory.createLinterV2;
import static edu.ParserFactory.createParserV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

public class TestLinterV2 {

  private static final JsonObject nonExpression;

  static {
    nonExpression = new JsonObject();
    nonExpression.addProperty("lower_camel_case", true);
    nonExpression.addProperty("read_input_non_expressions", true);
  }

  private Iterator<String> createIteratorFromString(String code) {
    return new BufferedReader(new java.io.StringReader(code)).lines().iterator();
  }

  private Linter createLinter(String code, JsonObject rules) {
    Iterator<String> codeIterator = createIteratorFromString(code);
    Lexer lexer = createLexerV1(codeIterator);
    Parser parser = createParserV1(lexer);
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
}
