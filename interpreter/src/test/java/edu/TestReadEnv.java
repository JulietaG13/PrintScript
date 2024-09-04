package edu;

import static edu.InterpreterFactory.createInterpreterV2;
import static edu.LexerFactory.createLexerV2;
import static edu.ParserFactory.createParserV2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.handlers.expressions.ConsoleInputProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

public class TestReadEnv {
  private InputStream createInputStreamFromString(String code) {
    return new ByteArrayInputStream(code.getBytes());
  }

  private Interpreter createInterpreter(String code) {
    InputStream codeIterator = createInputStreamFromString(code);
    Lexer lexer = createLexerV2(codeIterator);
    Parser parser = createParserV2(lexer);
    return createInterpreterV2(parser, new ConsoleInputProvider());
  }

  @Test
  public void testReadEnv() {
    String code =
        "const name: string = readEnv(\"BEST_FOOTBALL_CLUB\");\n"
            + "    println(\"What is the best football club?\");\n"
            + "    println(name);";
    Interpreter interpreter = createInterpreter(code);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    // Run the interpreter
    interpreter.interpret();

    // Restore original System.out
    System.setOut(originalOut);

    // Get the captured output
    String output = outputStream.toString().trim();

    assertEquals("What is the best football club?\n" + "San Lorenzo", output);
  }
}
