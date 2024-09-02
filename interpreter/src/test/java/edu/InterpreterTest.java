package edu;

import static edu.InterpreterFactory.createInterpreterV1;
import static edu.LexerFactory.createLexerV1;
import static edu.ParserFactory.createParserV1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

public class InterpreterTest {
  private Iterator<String> createIteratorFromString(String code) {
    return new BufferedReader(new java.io.StringReader(code)).lines().iterator();
  }

  private Interpreter createInterpreter(String code) {
    Iterator<String> codeIterator = createIteratorFromString(code);
    Lexer lexer = createLexerV1(codeIterator);
    Parser parser = createParserV1(lexer);
    return createInterpreterV1(parser);
  }

  @Test
  public void testInterpretWithPrintln() {
    String code = "println(\"Hello, world!\");";

    // Create interpreter for version 1
    Interpreter interpreter = createInterpreter(code);

    // Capture output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    // Run the interpreter
    interpreter.interpret();

    // Restore original System.out
    System.setOut(originalOut);

    // Get the captured output
    String output = outputStream.toString().trim();

    // Expected output
    String expectedOutput = "Hello, world!";

    // Assertions
    assertEquals(expectedOutput, output);
  }
}
