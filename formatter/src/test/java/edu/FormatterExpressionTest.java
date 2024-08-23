package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import edu.ast.ProgramNode;
import edu.rules.FormatterRuleParser;
import org.junit.jupiter.api.Test;

public class FormatterExpressionTest {
  private static final String lineSeparator = System.lineSeparator();

  private static final JsonObject defaultRules;

  static {
    defaultRules = new JsonObject();
    defaultRules.addProperty("declaration_space_before_colon", true);
    defaultRules.addProperty("declaration_space_after_colon", true);
    defaultRules.addProperty("assignment_space_before_equals", true);
    defaultRules.addProperty("assignment_space_after_equals", true);
    defaultRules.addProperty("println_new_lines_before_call", 1);
  }

  @Test
  public void noParens() {
    String input = lineSeparator + "let a : Number = 1 + 2 + 3 * 5;" + lineSeparator;
    String expected = "let a : Number = 1.0 + 2.0 + 3.0 * 5.0;" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void oneParens() {
    String input = lineSeparator + "let a : Number = 1 + (2 + 3) * 5;" + lineSeparator;
    String expected = "let a : Number = 1.0 + (2.0 + 3.0) * 5.0;" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void removesExtraParens() {
    String input = lineSeparator + "let a : Number = (1 + (2 + 3) * 5);" + lineSeparator;
    String expected = "let a : Number = 1.0 + (2.0 + 3.0) * 5.0;" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void removesExtraParens2() {
    String input =
        lineSeparator
            + "let a : Number = (1 + (10 * (9 + 2) + (8 * 6 + 7) + 3) * 5);"
            + lineSeparator;
    String expected =
        "let a : Number = 1.0 + (10.0 * (9.0 + 2.0) + 8.0 * 6.0 + 7.0 + 3.0) * 5.0;"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void manyParens() {
    String input =
        lineSeparator + "let a : Number = ((3 + 4) * ((1 + 2) * 5) + 6) * 7;" + lineSeparator;
    String expected =
        "let a : Number = ((3.0 + 4.0) * (1.0 + 2.0) * 5.0 + 6.0) * 7.0;" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  public ProgramNode getAst(String input) {
    Lexer lexer = new Lexer(input);
    lexer.tokenize();
    Parser parser = new Parser();
    return parser.parse(lexer.getTokens());
  }
}
