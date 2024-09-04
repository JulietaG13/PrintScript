package edu;

import static edu.InterpreterFactory.createInterpreterV1;
import static edu.LexerFactory.createLexerV1;
import static edu.ParserFactory.createParserV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.context.VariableContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class TestIne {
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
  public void testVariableDeclaration() {
    String string = "let numberResult: number = 5 * 5 - 8;\n" + "println(numberResult);\n";
    InputStream codeIterator = createInputStreamFromString(string);
    Lexer lexer = createLexerV1(codeIterator);
    Parser parser = createParserV1(lexer);
    Interpreter interpreter = createInterpreterV1(parser);
    interpreter.interpret();
    VariableContext variableContext = interpreter.getVisitor().getInventory().getVariableContext();
    assertTrue(variableContext.hasNumberVariable("numberResult"));
    assertEquals(new BigDecimal(17), variableContext.getNumberVariable("numberResult"));
  }

  @Test
  public void testConstantDeclarationInVersion1_0() {
    // Código fuente que debería fallar en la versión 1.0
    String code =
        "const a: string = \"constant declaration" + " should not be allowed in version 1.0\";";

    // Crear el intérprete para la versión 1.0
    Interpreter interpreter = createInterpreter(code);

    // Asegurarse de que se lanza una excepción durante la interpretación
    Exception exception = assertThrows(RuntimeException.class, interpreter::interpret);

    String expectedMessage = "constant declaration is not allowed in version 1.0";
    String actualMessage = exception.getMessage();

    System.out.println(actualMessage);
  }
}
