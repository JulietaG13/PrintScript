package edu;

import static edu.InterpreterFactory.createInterpreterV1;
import static edu.LexerFactory.createLexerV1;
import static edu.ParserFactory.createParserV1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

public class InterpreterTest {
  private InputStream createInputStreamFromString(String code) {
    return new ByteArrayInputStream(code.getBytes());
  }

  private Interpreter createInterpreter(String code) {
    InputStream codeIterator = createInputStreamFromString(code);
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
