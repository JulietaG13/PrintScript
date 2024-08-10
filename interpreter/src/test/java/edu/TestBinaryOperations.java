package edu;

import edu.ast.ProgramNode;
import edu.ast.expressions.*;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.Kind;
import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import edu.utils.LexicalRange;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

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
    LiteralStringNode left = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "Hello ");
    LiteralStringNode right = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "World");
    BinaryExpressionNode sum = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", left, right);
    VariableDeclarationNode greeting = new VariableDeclarationNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "greeting"), Type.STRING, Kind.LET, sum);
    ProgramNode program = new ProgramNode();
    program.addStatement(greeting);
    VariableContext variableContext = interpret(program);
    assertEquals("Hello World", variableContext.getStringVariable("greeting"));
  }

  @Test
  public void testDoubleBinaryStringDeclaration() {
    /* Input: let greeting: String = "I am" + 20 + "years old"; */
    LiteralStringNode left = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "I am ");
    LiteralNumberNode right = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 20);
    LiteralStringNode right2 = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), " years old");
    BinaryExpressionNode sum = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", left, right);
    BinaryExpressionNode sum2 = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", sum, right2);
    VariableDeclarationNode greeting = new VariableDeclarationNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "age"), Type.STRING, Kind.LET, sum2);
    ProgramNode program = new ProgramNode();
    program.addStatement(greeting);
    VariableContext variableContext = interpret(program);
    assertEquals("I am 20.0 years old", variableContext.getStringVariable("age"));
  }

  @Test
  public void testBinaryNumberDeclaration() {
    /* Input: let age:Number = 10 + 5; */
    ProgramNode program = new ProgramNode();
    LiteralNumberNode left = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 10);
    LiteralNumberNode right = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 5);
    BinaryExpressionNode sum = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", left, right);
    VariableDeclarationNode declaration = new VariableDeclarationNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "age"), Type.NUMBER, Kind.LET, sum);
    program.addStatement(declaration);
    VariableContext variableContext = interpret(program);
    assertTrue(variableContext.hasNumberVariable("age"));
    assertEquals(15.0, variableContext.getNumberVariable("age"));
  }


  @Test
  public void testPrintBinaryPrint() {
    /* Input: println(10 + 5); */
    ProgramNode program = new ProgramNode();
    LiteralNumberNode left = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 10);
    LiteralNumberNode right = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 5);
    BinaryExpressionNode sum = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", left, right);
    IdentifierNode identifierNode = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "println");
    CallExpressionNode print = new CallExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), identifierNode , List.of(sum));
    ExpressionStatementNode printNode = new ExpressionStatementNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), print);
    program.addStatement(printNode);
    String output = getPrintedInfo(program);
    assertEquals(15.0, Double.parseDouble(output));
  }

  @Test
  public void testPrintBinaryIdentifierNumber() {
    /* Input: let age: Number = 10; println(age + 5); */
    ProgramNode program = new ProgramNode();
    LiteralNumberNode numberNode = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 10);
    IdentifierNode identifierNode = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "age");
    VariableDeclarationNode declarationNode = new VariableDeclarationNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), identifierNode, Type.NUMBER, Kind.LET, numberNode);
    IdentifierNode left = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "age");
    LiteralNumberNode right = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 5);
    BinaryExpressionNode sum = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", left, right);
    IdentifierNode identifierNode2 = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "println");
    CallExpressionNode print = new CallExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), identifierNode2 , List.of(sum));
    ExpressionStatementNode printNode = new ExpressionStatementNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), print);
    program.addStatement(declarationNode);
    program.addStatement(printNode);
    String actualOutput = getPrintedInfo(program);
    assertEquals(15.0, Double.parseDouble(actualOutput));
  }


  @Test
  public void testPrintBinaryIdentifiers() {
    /* Input: let age: Number = 10; let plus: Number = 5; println(age + plus); */
    ProgramNode program = new ProgramNode();
    LiteralNumberNode numberNode = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 10);
    IdentifierNode identifierNode = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "age");
    VariableDeclarationNode declarationNode = new VariableDeclarationNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), identifierNode, Type.NUMBER, Kind.LET, numberNode);
    LiteralNumberNode numberNode1 = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 5);
    IdentifierNode identifierNode1 = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "plus");
    VariableDeclarationNode declarationNode1 = new VariableDeclarationNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), identifierNode1, Type.NUMBER, Kind.LET, numberNode1);
    IdentifierNode left = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "age");
    IdentifierNode right = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "plus");
    BinaryExpressionNode sum = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", left, right);
    IdentifierNode identifierNode2 = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "println");
    CallExpressionNode print = new CallExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), identifierNode2 , List.of(sum));
    ExpressionStatementNode printNode = new ExpressionStatementNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), print);
    program.addStatement(declarationNode);
    program.addStatement(declarationNode1);
    program.addStatement(printNode);
    String actualOutput = getPrintedInfo(program);
    assertEquals(15.0, Double.parseDouble(actualOutput));
  }

  @Test
  public void testDoubleBinaryStringPrint() {
    /* Input: println("I am" + 20 + "years old"); */
    LiteralStringNode left = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "I am ");
    LiteralNumberNode right = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 20);
    LiteralStringNode right2 = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), " years old");
    BinaryExpressionNode sum = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", left, right);
    BinaryExpressionNode sum2 = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", sum, right2);
    IdentifierNode identifier = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "println");
    CallExpressionNode print = new CallExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), identifier, List.of(sum2));
    ExpressionStatementNode greeting = new ExpressionStatementNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), print);
    ProgramNode program = new ProgramNode();
    program.addStatement(greeting);
    String printedInfo = getPrintedInfo(program);
    assertEquals("I am 20.0 years old", printedInfo);
  }

  @Test
  public void testBinaryStringIdentifierPrint() {
    /* Input: let age:Number = 20; println("I am" + age + "years old"); */
    LiteralNumberNode numberNode = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 20);
    IdentifierNode age = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "age");
    VariableDeclarationNode declarationNode = new VariableDeclarationNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), age, Type.NUMBER, Kind.LET, numberNode);
    LiteralStringNode left = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "I am ");
    LiteralStringNode right2 = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), " years old");
    BinaryExpressionNode sum = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", left, age);
    BinaryExpressionNode sum2 = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", sum, right2);
    IdentifierNode identifier = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "println");
    CallExpressionNode print = new CallExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), identifier, List.of(sum2));
    ExpressionStatementNode greeting = new ExpressionStatementNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), print);
    ProgramNode program = new ProgramNode();
    program.addStatement(declarationNode);
    program.addStatement(greeting);
    String printedInfo = getPrintedInfo(program);
    assertEquals("I am 20.0 years old", printedInfo);
  }

  @Test
  public void testBinaryDoubleIdentifierPrint() {
    /* Input: let age:Number = 20; let intro:String = "I am "; let measures: String =" years old"; println(intro + age + measures); */
    LiteralNumberNode numberNode = new LiteralNumberNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), (double) 20);
    IdentifierNode age = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "age");
    VariableDeclarationNode declarationNode = new VariableDeclarationNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), age, Type.NUMBER, Kind.LET, numberNode);
    LiteralStringNode introString = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "I am ");
    IdentifierNode intro = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "intro");
    VariableDeclarationNode introDeclaration = new VariableDeclarationNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), intro, Type.STRING, Kind.LET, introString);
    LiteralStringNode measuresString = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), " years old");
    IdentifierNode measures = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "measures");
    VariableDeclarationNode measuresDeclaration = new VariableDeclarationNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), measures, Type.STRING, Kind.LET, measuresString);
    BinaryExpressionNode sum = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", intro, age);
    BinaryExpressionNode sum2 = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0),"+", sum, measures);
    IdentifierNode identifier = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "println");
    CallExpressionNode print = new CallExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), identifier, List.of(sum2));
    ExpressionStatementNode greeting = new ExpressionStatementNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), print);
    ProgramNode program = new ProgramNode();
    program.addStatement(declarationNode);
    program.addStatement(introDeclaration);
    program.addStatement(measuresDeclaration);
    program.addStatement(greeting);
    String printedInfo = getPrintedInfo(program);
    assertEquals("I am 20.0 years old", printedInfo);
  }

  @Test
  public void testBinaryIdentifierFirst() {
    /* Input: let greet:String = "Hola "; println(greet + "ine"); */

    LiteralStringNode hola = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "Hola ");
    IdentifierNode greet = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "greet");
    VariableDeclarationNode greetDeclaration = new VariableDeclarationNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), greet, Type.STRING, Kind.LET, hola);

    LiteralStringNode ine = new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "ine");
    BinaryExpressionNode sum = new BinaryExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "+", greet, ine);
    IdentifierNode println = new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "println");
    CallExpressionNode print = new CallExpressionNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), println, List.of(sum));
    ExpressionStatementNode printNode = new ExpressionStatementNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), print);

    ProgramNode program = new ProgramNode();
    program.addStatement(greetDeclaration);
    program.addStatement(printNode);

    String printedInfo = getPrintedInfo(program);
    assertEquals("Hola ine", printedInfo);

  }


}
