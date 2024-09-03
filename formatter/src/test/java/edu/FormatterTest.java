package edu;

import static edu.LexerFactory.createLexerV1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import edu.ast.ProgramNode;
import edu.rules.FormatterRuleParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

public class FormatterTest {
  private static final String lineSeparator = System.lineSeparator();

  private static final JsonObject defaultRules;
  private static final JsonObject noExtraSpaceRules;

  /* Configurable
   * declaration_space_before_colon
   * declaration_space_after_colon
   * assignment_space_before_equals
   * assignment_space_after_equals
   * println_new_lines_before_call
   */

  private InputStream createInputStreamFromString(String code) {
    return new ByteArrayInputStream(code.getBytes());
  }

  static {
    defaultRules = new JsonObject();
    defaultRules.addProperty("declaration_space_before_colon", true);
    defaultRules.addProperty("declaration_space_after_colon", true);
    defaultRules.addProperty("assignment_space_before_equals", true);
    defaultRules.addProperty("assignment_space_after_equals", true);
    defaultRules.addProperty("println_new_lines_before_call", 1);

    noExtraSpaceRules = new JsonObject();
    noExtraSpaceRules.addProperty("declaration_space_before_colon", false);
    noExtraSpaceRules.addProperty("declaration_space_after_colon", false);
    noExtraSpaceRules.addProperty("assignment_space_before_equals", false);
    noExtraSpaceRules.addProperty("assignment_space_after_equals", false);
    noExtraSpaceRules.addProperty("println_new_lines_before_call", 0);
  }

  @Test
  public void defaultVariableDeclaration() {
    String input =
        lineSeparator + "let     my_cool_variable:string    = \"ciclon\";" + lineSeparator;
    String expected = "let my_cool_variable : string = \"ciclon\";" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void defaultPrintln() {
    String input =
        "let             "
            + lineSeparator
            + "my_cool_variable: string=\"ciclon\";"
            + lineSeparator
            + lineSeparator
            + lineSeparator
            + "println  (       my_cool_variable)  ;  ";

    String expected =
        "let my_cool_variable : string = \"ciclon\";"
            + lineSeparator
            + lineSeparator
            + "println(my_cool_variable);"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void defaultAssignments() {
    String input = "let my_cool_variable:string=\"ciclon\";my_cool_variable=\"hurricane\";";
    String expected =
        "let my_cool_variable : string = \"ciclon\";"
            + lineSeparator
            + "my_cool_variable = \"hurricane\";"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void defaultBinaryOperation() {
    String input = "let age: number = 10; println(age+5);";
    String expected =
        "let age : number = 10;"
            + lineSeparator
            + lineSeparator
            + "println(age + 5);"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void noExtraSpacesVariableDeclaration() {
    String input =
        lineSeparator + "let     my_cool_variable:string    = \"ciclon\";" + lineSeparator;
    String expected = "let my_cool_variable:string=\"ciclon\";" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(noExtraSpaceRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void noExtraSpacesPrintln() {
    String input =
        "let             "
            + lineSeparator
            + "my_cool_variable: string=\"ciclon\";"
            + lineSeparator
            + lineSeparator
            + lineSeparator
            + "println  (       my_cool_variable)  ;  ";

    String expected =
        "let my_cool_variable:string=\"ciclon\";"
            + lineSeparator
            + "println(my_cool_variable);"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(noExtraSpaceRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void noExtraSpacesAssignments() {
    String input = "let my_cool_variable:string=\"ciclon\";my_cool_variable=\"hurricane\";";
    String expected =
        "let my_cool_variable:string=\"ciclon\";"
            + lineSeparator
            + "my_cool_variable=\"hurricane\";"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(noExtraSpaceRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void noExtraSpacesBinaryOperation() {
    String input = "let age: number = 10; println(age+5);";
    String expected = "let age:number=10;" + lineSeparator + "println(age + 5);" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(FormatterRuleParser.parseRules(noExtraSpaceRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  public ProgramNode getAst(String input) {
    Lexer lexer = createLexerV1(createInputStreamFromString(input));
    lexer.tokenize();
    Parser parser = new Parser();
    return parser.parse(lexer.getTokens());
  }
}
