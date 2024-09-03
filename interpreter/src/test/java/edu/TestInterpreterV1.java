package edu;

import static edu.InterpreterFactory.createInterpreterV1;
import static edu.LexerFactory.createLexerV1;
import static edu.ParserFactory.createParserV1;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.visitor.ExecutionVisitor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

public class TestInterpreterV1 {

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
  public void testSnakeCaseVariableDeclaration() {
    String code = "let snake_case_variable : string;";
    Interpreter interpreter = createInterpreter(code);
    interpreter.interpret();
    ExecutionVisitor visitor = interpreter.getVisitor();
    assertTrue(
        visitor.getInventory().getVariableContext().hasStringVariable("snake_case_variable"));
  }
}
