package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ast.ProgramNode;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

public class GlobalTest {

  @Test
  public void testVariableDeclaration() {
    /* Input: let my_cool_variable: string = "ciclon" */
    String code = "let my_cool_variable: String = \"ciclon\";";
    ProgramNode program = getAst(code);
    VariableContext variableContext = interpret(program);
    assertEquals("ciclon", variableContext.getStringVariable("my_cool_variable"));
  }

  @Test
  public void testPrint() {
    /* Input: "let my_cool_variable: String = \"ciclon\"; println(my_cool_variable);\n" */
    String code = "let my_cool_variable: String = \"ciclon\"; println(my_cool_variable);\n";
    ProgramNode program = getAst(code);
    String printed = getPrintedInfo(program);
    assertEquals("ciclon", printed);
  }

  @Test
  public void testAssignation() {
    /* Input: "let my_cool_variable: String = \"ciclon\"; my_cool_variable = \"hurricane\";" */
    String code = "let my_cool_variable: String = \"ciclon\"; my_cool_variable = \"hurricane\";";
    ProgramNode program = getAst(code);
    VariableContext variableContext = interpret(program);
    assertEquals("hurricane", variableContext.getStringVariable("my_cool_variable"));
  }

  @Test
  public void testBinaryOperation() {
    /* Input: "let age: Number = 10; println(age+5);" */
    String code = "let age: Number = 10; println(age+5);";
    ProgramNode program = getAst(code);
    String printedInfo = getPrintedInfo(program);
    assertEquals("15.0", printedInfo);
  }

  public ProgramNode getAst(String code) {
    Lexer lexer = new Lexer(code);
    lexer.tokenize();
    Parser parser = new Parser();
    return parser.parse(lexer.getTokens());
  }

  public VariableContext interpret(ProgramNode node) {
    VariableContext variableContext =
        new VariableContext(new java.util.HashMap<>(), new java.util.HashMap<>());
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
}
