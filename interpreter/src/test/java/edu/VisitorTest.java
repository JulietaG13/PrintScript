package edu;

import edu.ast.ProgramNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VisitorTest {
  @Test
  public void testVariableAssignment() {
    String code = "let my_cool_variable: String = \"ciclon\";";

    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    Parser parser = new Parser();
    ProgramNode program = parser.parse(tokens);

    VariableContext context = new VariableContext();
    Reader reader = new Reader(new VariableContext());
    ExecutionVisitor visitor = new ExecutionVisitor(reader);
    visitor.visit(program);

    assertTrue(reader.hasStringVariable("my_cool_variable"));
    assertEquals("\"ciclon\"", reader.getStringVariable("my_cool_variable"));
  }

  @Test
  public void testPrintln() {
    String code = "let my_cool_variable: String = \"ciclon\";\nprintln(my_cool_variable);";

    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    Parser parser = new Parser();
    ProgramNode program = parser.parse(tokens);

    VariableContext context = new VariableContext();
    Reader reader = new Reader(new VariableContext());
    ExecutionVisitor visitor = new ExecutionVisitor(reader);

    String expectedOutput = "\"ciclon\"\n";
    String actualOutput = getOutputFromInterpreter(() -> visitor.visit(program));
    assertEquals(expectedOutput, actualOutput);
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

    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    Parser parser = new Parser();
    ProgramNode program = parser.parse(tokens);

    VariableContext context = new VariableContext();
    Reader reader = new Reader(new VariableContext());
    ExecutionVisitor visitor = new ExecutionVisitor(reader);

    String expectedOutput = "\"Hello World\"\n";
    String actualOutput = getOutputFromInterpreter(() -> visitor.visit(program));
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testPrintlnLiteralNumber() {
    String code = "println(15);";

    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    Parser parser = new Parser();
    ProgramNode program = parser.parse(tokens);

    VariableContext context = new VariableContext();
    Reader reader = new Reader(new VariableContext());
    ExecutionVisitor visitor = new ExecutionVisitor(reader);

    double expectedOutput = 15.0;
    String actualOutput = getOutputFromInterpreter(() -> visitor.visit(program));
    assertEquals(expectedOutput, Double.parseDouble(actualOutput), 0.0);
  }

  @Test
  public void testVariableDeclarationBinary() {
    String code = "let age:Number = 10 + 5;";

    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    Parser parser = new Parser();
    ProgramNode program = parser.parse(tokens);

    VariableContext context = new VariableContext();
    Reader reader = new Reader(new VariableContext());
    ExecutionVisitor visitor = new ExecutionVisitor(reader);

    visitor.visit(program);

    assertTrue(reader.hasNumberVariable("age"));
    assertEquals(15, reader.getNumberVariable("age"));
  }



}
