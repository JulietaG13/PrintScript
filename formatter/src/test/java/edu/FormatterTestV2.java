package edu;

import static edu.LexerFactory.createLexerV2;
import static edu.ParserFactory.createParserV2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import edu.rules.FormatterRuleParser;
import edu.rules.FormatterRuleProvider;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

public class FormatterTestV2 {
  private static final String lineSeparator = System.lineSeparator();

  private static final JsonObject jsonDefaultRules;
  private static final JsonObject jsonDoubleIndent;
  private static final JsonObject jsonNoIndent;

  private static final FormatterRuleProvider defaultRules;
  private static final FormatterRuleProvider doubleIndent;
  private static final FormatterRuleProvider noIndent;

  /* Configurable
   * declaration_space_before_colon
   * declaration_space_after_colon
   * assignment_space_before_equals
   * assignment_space_after_equals
   * println_new_lines_before_call
   * indent
   */

  static {
    JsonObject basic = new JsonObject();
    basic.addProperty("declaration_space_before_colon", true);
    basic.addProperty("declaration_space_after_colon", true);
    basic.addProperty("assignment_space_before_equals", true);
    basic.addProperty("assignment_space_after_equals", true);
    basic.addProperty("println_new_lines_before_call", 1);

    jsonDefaultRules = basic.deepCopy();
    jsonDefaultRules.addProperty("indent", 4);
    defaultRules = FormatterRuleParser.parseRules(jsonDefaultRules);

    jsonDoubleIndent = basic.deepCopy();
    jsonDoubleIndent.addProperty("indent", 8);
    doubleIndent = FormatterRuleParser.parseRules(jsonDoubleIndent);

    jsonNoIndent = basic.deepCopy();
    jsonNoIndent.addProperty("indent", 0);
    noIndent = FormatterRuleParser.parseRules(jsonNoIndent);
  }

  @Test
  public void simpleIfDefault() {
    String input = "if(true){println(2+2);}";
    String expected =
        "if (true) {"
            + lineSeparator
            + lineSeparator
            + " ".repeat(4)
            + "println(2 + 2);"
            + lineSeparator
            + "}"
            + lineSeparator;

    Formatter formatDefault = new Formatter(defaultRules, getParser(input));
    FormatterResult result = formatDefault.format();

    assertEquals(expected, result.getResult());
  }

  @Test
  public void simpleIfDouble() {
    String input = "if(true){println(2+2);}";
    String expected =
        "if (true) {"
            + lineSeparator
            + lineSeparator
            + " ".repeat(8)
            + "println(2 + 2);"
            + lineSeparator
            + "}"
            + lineSeparator;

    Formatter formatDefault = new Formatter(doubleIndent, getParser(input));
    FormatterResult result = formatDefault.format();

    assertEquals(expected, result.getResult());
  }

  @Test
  public void simpleIfNoIndent() {
    String input = "if(true){println(2+2);}";
    String expected =
        "if (true) {"
            + lineSeparator
            + lineSeparator
            + " ".repeat(0)
            + "println(2 + 2);"
            + lineSeparator
            + "}"
            + lineSeparator;

    Formatter formatDefault = new Formatter(noIndent, getParser(input));
    FormatterResult result = formatDefault.format();

    assertEquals(expected, result.getResult());
  }

  @Test
  public void nestedIfs() {
    String input = "if(true){   if(false){   if(true){}else{println(2);}   }   }";
    String expected =
        "if (true) {"
            + lineSeparator
            + " ".repeat(4 * 1)
            + "if (false) {"
            + lineSeparator
            + " ".repeat(4 * 2)
            + "if (true) {"
            + lineSeparator
            + " ".repeat(4 * 2)
            + "} else {"
            + lineSeparator
            + lineSeparator
            + " ".repeat(4 * 3)
            + "println(2);"
            + lineSeparator
            + " ".repeat(4 * 2)
            + "}"
            + lineSeparator
            + " ".repeat(4 * 1)
            + "}"
            + lineSeparator
            + "}"
            + lineSeparator;

    Formatter formatDefault = new Formatter(defaultRules, getParser(input));
    FormatterResult result = formatDefault.format();

    assertEquals(expected, result.getResult());
  }

  @Test
  public void defaultConstDeclaration() {
    String input =
        lineSeparator + "const     my_cool_variable:string    = \"ciclon\";" + lineSeparator;
    String expected = "const my_cool_variable : string = \"ciclon\";" + lineSeparator;

    Formatter defaultFormatter = new Formatter(defaultRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  public Parser getParser(String input) {
    Lexer lexer = createLexerV2(createInputStreamFromString(input));
    return createParserV2(lexer);
  }

  private InputStream createInputStreamFromString(String code) {
    return new ByteArrayInputStream(code.getBytes());
  }
}
