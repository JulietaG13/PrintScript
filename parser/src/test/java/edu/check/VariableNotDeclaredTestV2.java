package edu.check;

import static edu.ParserTestUtil.createInputStreamFromString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.Lexer;
import edu.LexerFactory;
import edu.Parser;
import edu.ParserFactory;
import org.junit.jupiter.api.Test;

class VariableNotDeclaredTestV2 {
  private static final String lineSeparator = System.lineSeparator();

  @Test
  public void simpleNotDeclared() { // var = 123.45
    String input = "var = 123.45;";

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertThrows(
        VariableNotDeclaredException.class,
        () -> {
          while (parser.hasNext()) {
            parser.next();
          }
        });
  }

  @Test
  public void similarNames() { // let Var : Number; var = 123.45;
    String input = "let Var : number;" + lineSeparator + "var = 123.45;";

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertThrows(
        VariableNotDeclaredException.class,
        () -> {
          while (parser.hasNext()) {
            parser.next();
          }
        });
  }

  @Test
  public void notDeclaredInExpression() { // let var : Number; var = 123.45 + 2 * other;
    String input = "let var : number;" + lineSeparator + "var = 123.45 + 2 * other;";

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertThrows(
        VariableNotDeclaredException.class,
        () -> {
          while (parser.hasNext()) {
            parser.next();
          }
        });
  }

  @Test
  public void notDeclaredInFunction() { // f(var);
    String input = "println(var);";

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertThrows(
        VariableNotDeclaredException.class,
        () -> {
          while (parser.hasNext()) {
            parser.next();
          }
        });
  }

  @Test
  public void noExceptionInFunction() { // f("var");
    String input = "println(\"var\");";

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertDoesNotThrow(
        () -> {
          while (parser.hasNext()) {
            parser.next();
          }
        });
  }
}
