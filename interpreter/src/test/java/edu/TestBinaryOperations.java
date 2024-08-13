package edu;

import edu.ast.ProgramNode;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBinaryOperations {
  public VariableContext interpret(ProgramNode node) {
    VariableContext variableContext = new VariableContext( new java.util.HashMap<>(), new java.util.HashMap<>());
    Reader reader = new Reader(variableContext, new java.util.Stack<>(), new java.util.Stack<>());
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
    /* Input: let greeting: String = "Hello" + "World"; */
    String code = "let greeting: String = \"Hello \" + \"World\";";
    ProgramNode program = compile(code);
    VariableContext variableContext = interpret(program);
    assertEquals("Hello World", variableContext.getStringVariable("greeting"));
  }

  @Test
  public void testDoubleBinaryStringDeclaration() {
    /* Input: let greeting: String = "I am" + 20 + "years old"; */
    String code = "let age: String = \"I am \" + 20 + \" years old\";";
    ProgramNode program = compile(code);
    VariableContext variableContext = interpret(program);
    assertEquals("I am 20.0 years old", variableContext.getStringVariable("age"));
  }

  @Test
  public void testBinaryNumberDeclaration() {
    /* Input: let age:Number = 10 + 5; */
    String code = "let age: Number = 10 + 5;";
    ProgramNode program = compile(code);
    VariableContext variableContext = interpret(program);
    assertTrue(variableContext.hasNumberVariable("age"));
    assertEquals(15.0, variableContext.getNumberVariable("age"));
  }


  @Test
  public void testPrintBinaryPrint() {
    /* Input: println(10 + 5); */
    String code = "println(10 + 5);";
    ProgramNode program = compile(code);
    String output = getPrintedInfo(program);
    assertEquals(15.0, Double.parseDouble(output));
  }

  @Test
  public void testPrintBinaryIdentifierNumber() {
    /* Input: let age: Number = 10; println(age + 5); */
    String code = "let age: Number = 10; println(age + 5);";
    ProgramNode program = compile(code);
    String output = getPrintedInfo(program);
    assertEquals(15.0, Double.parseDouble(output));
  }


  @Test
  public void testPrintBinaryIdentifiers() {
    /* Input: let age: Number = 10; let plus: Number = 5; println(age + plus); */
    String code = "let age: Number = 10; let plus: Number = 5; println(age + plus);";
    ProgramNode program = compile(code);
    String actualOutput = getPrintedInfo(program);
    assertEquals(15.0, Double.parseDouble(actualOutput));
  }

  @Test
  public void testDoubleBinaryStringPrint() {
    /* Input: println("I am" + 20 + "years old"); */
    String code = "println(\"I am \" + 20 + \" years old\");";
    ProgramNode program = compile(code);
    String printedInfo = getPrintedInfo(program);
    assertEquals("I am 20.0 years old", printedInfo);
  }

  private static ProgramNode compile(String code) {
    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    Parser parser = new Parser();
    ProgramNode program = parser.parse(lexer.getTokens());
    return program;
  }

  @Test
  public void testBinaryStringIdentifierPrint() {
    /* Input: let age:Number = 20; println("I am" + age + "years old"); */
    String code = "let age: Number = 20; println(\"I am \" + age + \" years old\");";
    ProgramNode program = compile(code);
    String printedInfo = getPrintedInfo(program);
    assertEquals("I am 20.0 years old", printedInfo);
  }

  @Test
  public void testBinaryDoubleIdentifierPrint() {
    /* Input: let age:Number = 20; let intro:String = "I am "; let measures: String =" years old"; println(intro + age + measures); */
    String code = "let age: Number = 20; let intro: String = \"I am \"; let measures: String =\" years old\"; println(intro + age + measures);";
    ProgramNode program = compile(code);
    String printedInfo = getPrintedInfo(program);
    assertEquals("I am 20.0 years old", printedInfo);
  }

  @Test
  public void testBinaryIdentifierFirst() {
    /* Input: let greet:String = "Hola "; println(greet + "ine"); */
    String code = "let greet: String = \"Hola \"; println(greet + \"ine\");";
    ProgramNode program = compile(code);
    String printedInfo = getPrintedInfo(program);
    assertEquals("Hola ine", printedInfo);
  }

  @Test
  public void testBinaryDifferentPriorities() {
    /* Input: let age: Number = 5 + 2 * 5; */
    String code = "let age: Number = 5 + 2 * 5;";
    ProgramNode program = compile(code);
    VariableContext info = interpret(program);
    assertEquals(15.0, info.getNumberVariable("age"));
  }

  @Test
  public void testBinaryDifferentPrioritiesAltered() {
    /* Input: let age: Number = 5 * 2 + 5; */
    String code = "let age: Number = 5 * 2 + 5;";
    ProgramNode program = compile(code);
    VariableContext info = interpret(program);
    assertEquals(15.0, info.getNumberVariable("age"));
  }



}
