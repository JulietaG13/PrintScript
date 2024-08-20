package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import edu.ast.ProgramNode;
import edu.rules.RuleParser;
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
        lineSeparator + "let     my_cool_variable:String    = \"ciclon\";" + lineSeparator;
    String expected = "let my_cool_variable : String = \"ciclon\";" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(RuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void defaultPrintln() {
    String input =
        "let             "
            + lineSeparator
            + "my_cool_variable: String=\"ciclon\";"
            + lineSeparator
            + lineSeparator
            + lineSeparator
            + "println  (       my_cool_variable)  ;  ";

    String expected =
        "let my_cool_variable : String = \"ciclon\";"
            + lineSeparator
            + lineSeparator
            + "println(my_cool_variable);"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(RuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void defaultAssignments() {
    String input = "let my_cool_variable:String=\"ciclon\";my_cool_variable=\"hurricane\";";
    String expected =
        "let my_cool_variable : String = \"ciclon\";"
            + lineSeparator
            + "my_cool_variable = \"hurricane\";"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(RuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void defaultBinaryOperation() {
    String input = "let age: Number = 10; println(age+5);";
    String expected =
        "let age : Number = 10.0;"
            + lineSeparator
            + lineSeparator
            + "println(age + 5.0);"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(RuleParser.parseRules(defaultRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void noExtraSpacesVariableDeclaration() {
    String input =
        lineSeparator + "let     my_cool_variable:String    = \"ciclon\";" + lineSeparator;
    String expected = "let my_cool_variable:String=\"ciclon\";" + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(RuleParser.parseRules(noExtraSpaceRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void noExtraSpacesPrintln() {
    String input =
        "let             "
            + lineSeparator
            + "my_cool_variable: String=\"ciclon\";"
            + lineSeparator
            + lineSeparator
            + lineSeparator
            + "println  (       my_cool_variable)  ;  ";

    String expected =
        "let my_cool_variable:String=\"ciclon\";"
            + lineSeparator
            + "println(my_cool_variable);"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(RuleParser.parseRules(noExtraSpaceRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void noExtraSpacesAssignments() {
    String input = "let my_cool_variable:String=\"ciclon\";my_cool_variable=\"hurricane\";";
    String expected =
        "let my_cool_variable:String=\"ciclon\";"
        + lineSeparator
        + "my_cool_variable=\"hurricane\";"
        + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(RuleParser.parseRules(noExtraSpaceRules));
    FormatterResult res = defaultFormatter.format(program);

    assertEquals(expected, res.getResult());
  }

  @Test
  public void noExtraSpacesBinaryOperation() {
    String input = "let age: Number = 10; println(age+5);";
    String expected =
        "let age:Number=10.0;"
            + lineSeparator
            + "println(age + 5.0);"
            + lineSeparator;

    ProgramNode program = getAst(input);
    Formatter defaultFormatter = new Formatter(RuleParser.parseRules(noExtraSpaceRules));
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
