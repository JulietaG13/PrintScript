package edu;

import static edu.InterpreterFactory.createInterpreterV1;
import static edu.InterpreterFactory.createInterpreterV2;
import static edu.LexerFactory.createLexerV1;
import static edu.LexerFactory.createLexerV2;
import static edu.ParserFactory.createParserV1;
import static edu.ParserFactory.createParserV2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.context.VariableContext;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

public class TestIne {
  private Iterator<String> createIteratorFromString(String code) {
    return new BufferedReader(new java.io.StringReader(code)).lines().iterator();
  }

  private Interpreter createInterpreter(String code) {
    Iterator<String> codeIterator = createIteratorFromString(code);
    Lexer lexer = createLexerV2(codeIterator);
    Parser parser = createParserV2(lexer);
    return createInterpreterV2(parser);
  }

  @Test
  public void testVariableDeclaration() {
    String string = "let numberResult: number = 5 * 5 - 8;\n" + "println(numberResult);\n";
    Iterator<String> codeIterator = createIteratorFromString(string);
    Lexer lexer = createLexerV1(codeIterator);
    Parser parser = createParserV1(lexer);
    Interpreter interpreter = createInterpreterV1(parser);
    interpreter.interpret();
    VariableContext variableContext = interpreter.getVisitor().getInventory().getVariableContext();
    assertTrue(variableContext.hasNumberVariable("numberResult"));
    assertEquals(new BigDecimal(17), variableContext.getNumberVariable("numberResult"));
  }
}
