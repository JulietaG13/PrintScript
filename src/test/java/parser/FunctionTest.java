package parser;

import parser.ast.ProgramNode;
import lexer.Token;
import org.junit.jupiter.api.Test;
import java.util.List;

import static parser.TokenProvider.*;

public class FunctionTest {

  @Test
  public void noArgs() {
    List<Token> input = List.of(
        getIdentifier("println"),
        getOpenParen(),
        getCloseParen(),
        getSemicolon()
    );

    ProgramNode root = new Parser().parse(input);
    System.out.println(root);
  }

  @Test
  public void oneArg() {
    List<Token> input = List.of(
        getIdentifier("println"),
        getOpenParen(),
        getLiteral("hola"),
        getCloseParen(),
        getSemicolon()
    );

    ProgramNode root = new Parser().parse(input);
    System.out.println(root);
  }

  @Test
  public void twoArgs() {
    List<Token> input = List.of(
        getIdentifier("println"),
        getOpenParen(),
        getLiteral("hola"),
        getComma(),
        getLiteral(123.0),
        getCloseParen(),
        getSemicolon()
    );

    ProgramNode root = new Parser().parse(input);
    System.out.println(root);
  }

  @Test
  public void varAsArg() {
    List<Token> input = List.of(
        getIdentifier("println"),
        getOpenParen(),
        getIdentifier("var"),
        getCloseParen(),
        getSemicolon()
    );

    ProgramNode root = new Parser().parse(input);
    System.out.println(root);
  }

  @Test
  public void functionAsArg() {
    List<Token> input = List.of(
        getIdentifier("println"),
        getOpenParen(),
        getIdentifier("foo"),
        getOpenParen(),
        getCloseParen(),
        getCloseParen(),
        getSemicolon()
    );

    ProgramNode root = new Parser().parse(input);
    System.out.println(root);
  }
}
