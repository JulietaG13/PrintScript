package edu;

import static edu.InterpreterFactory.createInterpreterV1;
import static edu.LexerFactory.createLexerV1;
import static edu.ParserFactory.createParserV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.context.VariableContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class TestVariableDeclaration {
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
  public void testValidVariableDeclaration() {
    String code = "let result: number; result = 5; println(result);";
    Interpreter interpreter = createInterpreter(code);
    interpreter.interpret();
    VariableContext variableContext = interpreter.getVisitor().getInventory().getVariableContext();
    assertTrue(variableContext.hasNumberVariable("result"));
    assertEquals(new BigDecimal(5), variableContext.getNumberVariable("result"));
  }

  @Test
  public void testBinaryStringIdentifierPrint() {
    String code = "let age: number = 20; println(\"I am \" + age + \" years old\");";

    Interpreter interpreter = createInterpreter(code);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    interpreter.interpret();

    System.setOut(originalOut);

    String output = outputStream.toString().trim();

    String expectedOutput = "I am 20 years old";

    assertEquals(expectedOutput, output);

    VariableContext variableContext = interpreter.getVisitor().getInventory().getVariableContext();
    assertTrue(variableContext.hasNumberVariable("age"));
    assertEquals(new BigDecimal(20), variableContext.getNumberVariable("age"));
  }
}
