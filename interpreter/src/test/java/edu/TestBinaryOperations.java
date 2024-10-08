package edu;

import static edu.LexerFactory.createLexerV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ast.ProgramNode;
import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.RuleProviderV1;
import edu.visitor.ExecutionVisitor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestBinaryOperations {

  private InputStream createInputStreamFromString(String code) {
    return new ByteArrayInputStream(code.getBytes());
  }

  public VariableContext interpret(ProgramNode node) {
    VariableContext variableContext =
        new VariableContext(
            new java.util.HashMap<>(), new java.util.HashMap<>(), new java.util.HashMap<>());
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());
    List context = new ArrayList();
    context.add(variableContext);
    Inventory inventory = new Inventory(context);
    ExecutionVisitor visitor =
        new ExecutionVisitor(
            reader,
            inventory,
            InterpreterFactory.createHandlerRegistryV1(new RuleProviderV1(), new ConsolePrinter()));
    visitor.visit(node);
    return visitor.getInventory().getVariableContext();
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
    assertEquals("I am 20 years old", variableContext.getStringVariable("age"));
  }

  @Test
  public void testBinaryNumberDeclaration() {
    String code = "let age: number = 10 + 5;";
    ProgramNode program = compile(code);
    VariableContext variableContext = interpret(program);
    assertTrue(variableContext.hasNumberVariable("age"));
    assertEquals(new BigDecimal(15), variableContext.getNumberVariable("age"));
  }

  @Test
  public void testPrintBinaryPrint() {
    String code = "println(10 + 5);";
    ProgramNode program = compile(code);
    String output = getPrintedInfo(program);
    assertEquals("15", output);
  }

  @Test
  public void testPrintBinaryIdentifierNumber() {
    String code = "let age: number = 10; println(age + 5);";
    ProgramNode program = compile(code);
    String output = getPrintedInfo(program);
    assertEquals("15", output);
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
    assertEquals("I am 20 years old", printedInfo);
  }

  private ProgramNode compile(String code) {
    Lexer lexer = createLexerV1(createInputStreamFromString(code));
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
    assertEquals("I am 20 years old", printedInfo);
  }

  @Test
  public void testBinaryDoubleIdentifierPrint() {
    String code =
        "let age: number = 20; let intro: string = \"I am \"; let measures: "
            + "string =\" years old\"; println(intro + age + measures);";
    ProgramNode program = compile(code);
    String printedInfo = getPrintedInfo(program);
    assertEquals("I am 20 years old", printedInfo);
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
    assertEquals(new BigDecimal(15), info.getNumberVariable("age"));
  }

  @Test
  public void testBinaryDifferentPrioritiesAltered() {
    String code = "let age: number = 5 * 2 + 5;";
    ProgramNode program = compile(code);
    VariableContext info = interpret(program);
    assertEquals(new BigDecimal(15), info.getNumberVariable("age"));
  }
}
