package edu;

import edu.ast.ProgramNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VisitorTest {
  @Test
  public void testVariableAssignment() {
    String code = "let my_cool_variable: String = \"ciclon\";";
    VariableContext variableContext = process(code);
    assertTrue(variableContext.hasStringVariable("my_cool_variable"));
    assertEquals("\"ciclon\"", variableContext.getStringVariable("my_cool_variable"));
  }

  @Test
  public void testPrintln() {
    String code = "let my_cool_variable: String = \"ciclon\";\nprintln(my_cool_variable);";
    String expectedOutput = "\"ciclon\"\n";
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
    String expectedOutput = "\"Hello World\"\n";
    assertEquals(expectedOutput, getOutput(code));
  }

  @Test
  public void testPrintlnLiteralNumber() {
    String code = "println(15);";
    double expectedOutput = 15.0;
    assertEquals(expectedOutput, Double.parseDouble(getOutput(code)), 0.0);
  }

  public VariableContext process(String code) {
    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    Parser parser = new Parser();
    ProgramNode program = parser.parse(tokens);

    VariableContext variableContext = new VariableContext();
    Reader reader = new Reader(variableContext);
    ExecutionVisitor visitor = new ExecutionVisitor(reader);

    visitor.visit(program);

    return variableContext;
  }


  public String getOutput(String code) {
    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    Parser parser = new Parser();
    ProgramNode program = parser.parse(tokens);

    VariableContext variableContext = new VariableContext();
    Reader reader = new Reader(variableContext);
    ExecutionVisitor visitor = new ExecutionVisitor(reader);

    String actualOutput = getOutputFromInterpreter(() -> visitor.visit(program));
    return actualOutput;
  }


}
