package edu;

import static edu.LexerFactory.createLexerV1;
import static edu.ParserFactory.createParserV1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import edu.rules.FormatterRuleParser;
import edu.rules.FormatterRuleProvider;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

public class FormatterExpressionTest {
  private static final String lineSeparator = System.lineSeparator();

  private static final JsonObject jsonDefaultRules;
  private static final FormatterRuleProvider defaultRules;

  static {
    jsonDefaultRules = new JsonObject();
    jsonDefaultRules.addProperty("declaration_space_before_colon", true);
    jsonDefaultRules.addProperty("declaration_space_after_colon", true);
    jsonDefaultRules.addProperty("assignment_space_before_equals", true);
    jsonDefaultRules.addProperty("assignment_space_after_equals", true);
    jsonDefaultRules.addProperty("println_new_lines_before_call", 1);
    defaultRules = FormatterRuleParser.parseRules(jsonDefaultRules);
  }

  @Test
  public void noParens() {
    String input = lineSeparator + "let a : number = 1 + 2 + 3 * 5;" + lineSeparator;
    String expected = "let a : number = 1 + 2 + 3 * 5;";

    Formatter defaultFormatter = new Formatter(defaultRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  @Test
  public void oneParens() {
    String input = lineSeparator + "let a : number = 1 + (2 + 3) * 5;" + lineSeparator;
    String expected = "let a : number = 1 + (2 + 3) * 5;";

    Formatter defaultFormatter = new Formatter(defaultRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  @Test
  public void removesExtraParens() {
    String input = lineSeparator + "let a : number = (1 + (2 + 3) * 5);" + lineSeparator;
    String expected = "let a : number = 1 + (2 + 3) * 5;";

    Formatter defaultFormatter = new Formatter(defaultRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  @Test
  public void removesExtraParens2() {
    String input =
        lineSeparator
            + "let a : number = (1 + (10 * (9 + 2) + (8 * 6 + 7) + 3) * 5);"
            + lineSeparator;
    String expected = "let a : number = 1 + (10 * (9 + 2) + 8 * 6 + 7 + 3) * 5;";

    Formatter defaultFormatter = new Formatter(defaultRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  @Test
  public void manyParens() {
    String input =
        lineSeparator + "let a : number = ((3 + 4) * ((1 + 2) * 5) + 6) * 7;" + lineSeparator;
    String expected = "let a : number = ((3 + 4) * (1 + 2) * 5 + 6) * 7;";

    Formatter defaultFormatter = new Formatter(defaultRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  private InputStream createInputStreamFromString(String code) {
    return new ByteArrayInputStream(code.getBytes());
  }

  public Parser getParser(String input) {
    Lexer lexer = createLexerV1(createInputStreamFromString(input));
    return createParserV1(lexer);
  }
}
