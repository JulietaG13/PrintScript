package edu;

import static edu.InterpreterFactory.createInterpreterV1;
import static edu.LexerFactory.createLexerV1;
import static edu.ParserFactory.createParserV1;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.visitor.ExecutionVisitor;
import java.io.BufferedReader;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

public class TestInterpreterV1 {

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
  public void testSnakeCaseVariableDeclaration() {
    String code = "let snake_case_variable : string;";
    Interpreter interpreter = createInterpreter(code);
    interpreter.interpret();
    ExecutionVisitor visitor = interpreter.getVisitor();
    assertTrue(
        visitor.getInventory().getVariableContext().hasStringVariable("snake_case_variable"));
  }
}
