package edu;

import com.google.gson.JsonObject;
import edu.rules.FormatterRuleParser;
import edu.rules.FormatterRuleProvider;

public class FormatterTestV1 {
  private static final String lineSeparator = System.lineSeparator();

  private static final JsonObject jsonDefaultRules;
  private static final JsonObject jsonNoExtraSpaceRules;

  private static final FormatterRuleProvider defaultRules;
  private static final FormatterRuleProvider noExtraSpaceRules;

  /* Configurable
   * declaration_space_before_colon
   * declaration_space_after_colon
   * assignment_space_before_equals
   * assignment_space_after_equals
   * println_new_lines_before_call
   */

  static {
    jsonDefaultRules = new JsonObject();
    jsonDefaultRules.addProperty("declaration_space_before_colon", true);
    jsonDefaultRules.addProperty("declaration_space_after_colon", true);
    jsonDefaultRules.addProperty("assignment_space_before_equals", true);
    jsonDefaultRules.addProperty("assignment_space_after_equals", true);
    jsonDefaultRules.addProperty("println_new_lines_before_call", 1);
    defaultRules = FormatterRuleParser.parseRules(jsonDefaultRules);

    jsonNoExtraSpaceRules = new JsonObject();
    jsonNoExtraSpaceRules.addProperty("declaration_space_before_colon", false);
    jsonNoExtraSpaceRules.addProperty("declaration_space_after_colon", false);
    jsonNoExtraSpaceRules.addProperty("assignment_space_before_equals", false);
    jsonNoExtraSpaceRules.addProperty("assignment_space_after_equals", false);
    jsonNoExtraSpaceRules.addProperty("println_new_lines_before_call", 0);
    noExtraSpaceRules = FormatterRuleParser.parseRules(jsonNoExtraSpaceRules);
  }

  /*
  @Test
  public void defaultVariableDeclaration() {
    String input =
        lineSeparator + "let     my_cool_variable:string    = \"ciclon\";" + lineSeparator;
    String expected = "let my_cool_variable : string = \"ciclon\";";

    Formatter defaultFormatter = new Formatter(defaultRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

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
            + "println(my_cool_variable);";

    Formatter defaultFormatter = new Formatter(defaultRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  @Test
  public void defaultAssignments() {
    String input = "let my_cool_variable:string=\"ciclon\";my_cool_variable=\"hurricane\";";
    String expected =
        "let my_cool_variable : string = \"ciclon\";"
            + lineSeparator
            + "my_cool_variable = \"hurricane\";";

    Formatter defaultFormatter = new Formatter(defaultRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  @Test
  public void defaultBinaryOperation() {
    String input = "let age: number = 10; println(age+5);";
    String expected =
        "let age : number = 10;" + lineSeparator + lineSeparator + "println(age + 5);";

    Formatter defaultFormatter = new Formatter(defaultRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  @Test
  public void noExtraSpacesVariableDeclaration() {
    String input =
        lineSeparator + "let     my_cool_variable:string    = \"ciclon\";" + lineSeparator;
    String expected = "let my_cool_variable:string=\"ciclon\";";

    Formatter defaultFormatter = new Formatter(noExtraSpaceRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

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
        "let my_cool_variable:string=\"ciclon\";" + lineSeparator + "println(my_cool_variable);";

    Formatter defaultFormatter = new Formatter(noExtraSpaceRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  @Test
  public void noExtraSpacesAssignments() {
    String input = "let my_cool_variable:string=\"ciclon\";my_cool_variable=\"hurricane\";";
    String expected =
        "let my_cool_variable:string=\"ciclon\";"
            + lineSeparator
            + "my_cool_variable=\"hurricane\";";

    Formatter defaultFormatter = new Formatter(noExtraSpaceRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  @Test
  public void noExtraSpacesBinaryOperation() {
    String input = "let age: number = 10; println(age+5);";
    String expected = "let age:number=10;" + lineSeparator + "println(age + 5);";

    Formatter defaultFormatter = new Formatter(noExtraSpaceRules, getParser(input));
    FormatterResult res = defaultFormatter.format();

    assertEquals(expected, res.getResult());
  }

  public Parser getParser(String input) {
    Lexer lexer = createLexerV1(createInputStreamFromString(input));
    return createParserV1(lexer);
  }

  private InputStream createInputStreamFromString(String code) {
    return new ByteArrayInputStream(code.getBytes());
  }
  */
}
