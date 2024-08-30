package edu;

import static edu.LexerFactory.createLexerV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ast.ProgramNode;
import edu.reader.Reader;
import edu.tokens.Token;
import edu.visitor.ExecutionVisitor;
import java.util.List;
import org.junit.jupiter.api.Test;

public class VisitorTest {

  private final String lineSeparator = System.lineSeparator();

  @Test
  public void testVariableAssignment() {
    String code = "let my_cool_variable: String = \"ciclon\";";
    VariableContext variableContext = process(code);
    assertTrue(variableContext.hasStringVariable("my_cool_variable"));
    assertEquals("ciclon", variableContext.getStringVariable("my_cool_variable"));
  }

  @Test
  public void testPrintln() {
    String code = "let my_cool_variable: String = \"ciclon\";\nprintln(my_cool_variable);";
    String expectedOutput = "ciclon" + lineSeparator;
    assertEquals(expectedOutput, getOutput(code));
  }

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

  @Test
  public void testPrintlnLiteralString() {
    String code = "println(\"Hello World\");";
    String expectedOutput = "Hello World" + lineSeparator;
    assertEquals(expectedOutput, getOutput(code));
  }

  @Test
  public void testPrintlnLiteralNumber() {
    String code = "println(15);";
    double expectedOutput = 15.0;
    assertEquals(expectedOutput, Double.parseDouble(getOutput(code)), 0.0);
  }

  public VariableContext process(String code) {
    Lexer lexer = createLexerV1(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    Parser parser = new Parser();
    ProgramNode program = parser.parse(tokens);

    VariableContext variableContext =
        new VariableContext(new java.util.HashMap<>(), new java.util.HashMap<>());
    Reader reader = new Reader(variableContext, new java.util.Stack<>(), new java.util.Stack<>());
    ExecutionVisitor visitor = new ExecutionVisitor(reader);

    visitor.visit(program);

    return visitor.getReader().getVariables();
  }

  public String getOutput(String code) {
    Lexer lexer = createLexerV1(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    Parser parser = new Parser();
    ProgramNode program = parser.parse(tokens);

    VariableContext variableContext =
        new VariableContext(new java.util.HashMap<>(), new java.util.HashMap<>());
    Reader reader = new Reader(variableContext, new java.util.Stack<>(), new java.util.Stack<>());
    ExecutionVisitor visitor = new ExecutionVisitor(reader);

    String actualOutput = getOutputFromInterpreter(() -> visitor.visit(program));
    return actualOutput;
  }
}
