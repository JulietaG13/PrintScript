package edu;

import static edu.LexerFactory.createLexerV1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import edu.ast.ProgramNode;
import edu.rules.FormatterRuleParser;
import java.io.BufferedReader;
import java.util.Iterator;
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
    String input = lineSeparator + "let a : number = 1 + 2 + 3 * 5;" + lineSeparator;
    String expected = "let a : number = 1.0 + 2.0 + 3.0 * 5.0;" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void oneParens() {
    String input = lineSeparator + "let a : number = 1 + (2 + 3) * 5;" + lineSeparator;
    String expected = "let a : number = 1.0 + (2.0 + 3.0) * 5.0;" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void removesExtraParens() {
    String input = lineSeparator + "let a : number = (1 + (2 + 3) * 5);" + lineSeparator;
    String expected = "let a : number = 1.0 + (2.0 + 3.0) * 5.0;" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void removesExtraParens2() {
    String input =
        lineSeparator
            + "let a : number = (1 + (10 * (9 + 2) + (8 * 6 + 7) + 3) * 5);"
            + lineSeparator;
    String expected =
        "let a : number = 1.0 + (10.0 * (9.0 + 2.0) + 8.0 * 6.0 + 7.0 + 3.0) * 5.0;"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void manyParens() {
    String input =
        lineSeparator + "let a : number = ((3 + 4) * ((1 + 2) * 5) + 6) * 7;" + lineSeparator;
    String expected =
        "let a : number = ((3.0 + 4.0) * (1.0 + 2.0) * 5.0 + 6.0) * 7.0;" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  private Iterator<String> createIteratorFromString(String code) {
    return new BufferedReader(new java.io.StringReader(code)).lines().iterator();
  }

  public ProgramNode getAst(String input) {
    Lexer lexer = createLexerV1(createIteratorFromString(input));
    lexer.tokenize();
    Parser parser = new Parser();
    return parser.parse(lexer.getTokens());
  }
}
