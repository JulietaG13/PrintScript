package classesRequirements;

import lexer.Lexer;
import parser.Parser;
import interpreter.Interpreter;
import parser.ast.ProgramNode;
import lexer.Token;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class firstClassCiclonTest {
  @Test
  public void testVariableAssignmentAndPrintln() {
    String code = "let my_cool_variable: String = \"ciclon\";\nprintln(my_cool_variable);";

    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    Parser parser = new Parser();
    ProgramNode program = parser.parse(tokens);

    Interpreter interpreter = new Interpreter();
    interpreter.interpret(program);

    String expectedOutput = "\"ciclon\"\n";
    String actualOutput = getOutputFromInterpreter(() -> interpreter.interpret(program));
    assertEquals(expectedOutput, actualOutput);
  }

  //capturar la salida de println
  private String getOutputFromInterpreter(Runnable interpreterExecution) {
    java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
    java.io.PrintStream originalOut = System.out;
    System.setOut(new java.io.PrintStream(outContent));
    try {
      interpreterExecution.run();
    } finally {
      System.setOut(originalOut);
    }
    return outContent.toString();
  }
}

