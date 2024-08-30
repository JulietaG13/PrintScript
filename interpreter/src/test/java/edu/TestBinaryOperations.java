package edu;

import static edu.LexerFactory.createLexerV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ast.ProgramNode;
import edu.reader.InterpreterReader;
import edu.visitor.ExecutionVisitor;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

public class TestBinaryOperations {

  private Iterator<String> createIteratorFromString(String code) {
    return new BufferedReader(new java.io.StringReader(code)).lines().iterator();
  }

  public VariableContext interpret(ProgramNode node) {
    VariableContext variableContext =
        new VariableContext(
            new java.util.HashMap<>(),
            new java.util.HashMap<>(),
            new java.util.HashMap<>(),
            new java.util.HashSet<>());
    InterpreterReader reader =
        new InterpreterReader(variableContext, new java.util.Stack<>(), new java.util.Stack<>());
    ExecutionVisitor visitor = new ExecutionVisitor(reader);
    visitor.visit(node);
    return visitor.getReader().getVariables();
  }

  public String getPrintedInfo(ProgramNode node) {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));
    try {
      interpret(node);
    } finally {
      System.setOut(originalOut);
    }
    String actualOutput = outContent.toString().trim();
    return actualOutput;
  }

  @Test
  public void testBinaryStringDeclaration() {
    String code = "let greeting: string = \"Hello \" + \"World\";";
    ProgramNode program = compile(code);
    VariableContext variableContext = interpret(program);
    assertEquals("Hello World", variableContext.getStringVariable("greeting"));
  }

  @Test
  public void testDoubleBinaryStringDeclaration() {
    String code = "let age: string = \"I am \" + 20 + \" years old\";";
    ProgramNode program = compile(code);
    VariableContext variableContext = interpret(program);
    assertEquals("I am 20.0 years old", variableContext.getStringVariable("age"));
  }

  @Test
  public void testBinaryNumberDeclaration() {
    String code = "let age: number = 10 + 5;";
    ProgramNode program = compile(code);
    VariableContext variableContext = interpret(program);
    assertTrue(variableContext.hasNumberVariable("age"));
    assertEquals(15.0, variableContext.getNumberVariable("age"));
  }

  @Test
  public void testPrintBinaryPrint() {
    String code = "println(10 + 5);";
    ProgramNode program = compile(code);
    String output = getPrintedInfo(program);
    assertEquals(15.0, Double.parseDouble(output));
  }

  @Test
  public void testPrintBinaryIdentifierNumber() {
    String code = "let age: number = 10; println(age + 5);";
    ProgramNode program = compile(code);
    String output = getPrintedInfo(program);
    assertEquals(15.0, Double.parseDouble(output));
  }

  @Test
  public void testPrintBinaryIdentifiers() {
    String code = "let age: number = 10; let plus: number = 5; println(age + plus);";
    ProgramNode program = compile(code);
    String actualOutput = getPrintedInfo(program);
    assertEquals(15.0, Double.parseDouble(actualOutput));
  }

  @Test
  public void testDoubleBinaryStringPrint() {
    String code = "println(\"I am \" + 20 + \" years old\");";
    ProgramNode program = compile(code);
    String printedInfo = getPrintedInfo(program);
    assertEquals("I am 20.0 years old", printedInfo);
  }

  private ProgramNode compile(String code) {
    Lexer lexer = createLexerV1(createIteratorFromString(code));
    lexer.tokenize();
    Parser parser = new Parser();
    ProgramNode program = parser.parse(lexer.getTokens());
    return program;
  }

  @Test
  public void testBinaryStringIdentifierPrint() {
    String code = "let age: number = 20; println(\"I am \" + age + \" years old\");";
    ProgramNode program = compile(code);
    String printedInfo = getPrintedInfo(program);
    assertEquals("I am 20.0 years old", printedInfo);
  }

  @Test
  public void testBinaryDoubleIdentifierPrint() {
    String code =
        "let age: number = 20; let intro: string = \"I am \"; let measures: "
            + "string =\" years old\"; println(intro + age + measures);";
    ProgramNode program = compile(code);
    String printedInfo = getPrintedInfo(program);
    assertEquals("I am 20.0 years old", printedInfo);
  }

  @Test
  public void testBinaryIdentifierFirst() {
    String code = "let greet: string = \"Hola \"; println(greet + \"ine\");";
    ProgramNode program = compile(code);
    String printedInfo = getPrintedInfo(program);
    assertEquals("Hola ine", printedInfo);
  }

  @Test
  public void testBinaryDifferentPriorities() {
    String code = "let age: number = 5 + 2 * 5;";
    ProgramNode program = compile(code);
    VariableContext info = interpret(program);
    assertEquals(15.0, info.getNumberVariable("age"));
  }

  @Test
  public void testBinaryDifferentPrioritiesAltered() {
    String code = "let age: number = 5 * 2 + 5;";
    ProgramNode program = compile(code);
    VariableContext info = interpret(program);
    assertEquals(15.0, info.getNumberVariable("age"));
  }
}
