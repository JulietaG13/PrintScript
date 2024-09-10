package edu.check;

import static edu.ParserTestUtil.createInputStreamFromString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.Lexer;
import edu.LexerFactory;
import edu.Parser;
import edu.ParserFactory;
import org.junit.jupiter.api.Test;

class VariableMismatchTestV2 {

  @Test
  public void mismatchSimpleExpectedString() { // let a : String = 123.45
    String input = "let var : string = 123;";

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertThrows(
        VariableTypeMismatchException.class,
        () -> {
          while (parser.hasNext()) {
            parser.next();
          }
        });
  }

  @Test
  public void mismatchSimpleExpectedNumber() { // let a : Number = "123.45"
    String input = "let var : number = \"123\";";

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertThrows(
        VariableTypeMismatchException.class,
        () -> {
          while (parser.hasNext()) {
            parser.next();
          }
        });
  }

  @Test
  public void mismatchExpression() { // let a : String = 123.45 + 1 + 45 * 2
    String input = "let var : string = 123.45 + 1 + 45 * 2;";

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertThrows(
        VariableTypeMismatchException.class,
        () -> {
          while (parser.hasNext()) {
            parser.next();
          }
        });
  }

  @Test
  public void mismatchMixedExpression() { // let a : Number = 123.45 * 1 + "hola" + 2
    String input = "let var : number = 123.45 * 1 + \"hola\" + 2;";

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertThrows(
        VariableTypeMismatchException.class,
        () -> {
          while (parser.hasNext()) {
            parser.next();
          }
        });
  }

  @Test
  public void notMismatchMixedExpression() { // let a : String = 123.45 * 1 + "hola" + 2
    String input = "let var : string = 123.45 * 1 + \"hola\" + 2;";

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
