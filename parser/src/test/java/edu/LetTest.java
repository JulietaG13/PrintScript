package edu;

import edu.ast.ProgramNode;
import org.junit.jupiter.api.Test;
import java.util.List;

import static edu.TokenProvider.*;


public class LetTest {

  @Test
  public void noInitialValue() {
    List<Token> input = List.of(
        getLet(),
        getIdentifier("a"),
        getColon(),
        getNumber(),
        getSemicolon()
    );

    ProgramNode root = new Parser().parse(input);
    System.out.println(root);
  }

  @Test
  public void initialNumberValue() {
    List<Token> input = List.of(
        getLet(),
        getIdentifier("number-123"),
        getColon(),
        getNumber(),
        getEquals(),
        getLiteral(123.0),
        getSemicolon()
    );

    ProgramNode root = new Parser().parse(input);
    System.out.println(root);
  }

  @Test
  public void initialStringValue() {
    List<Token> input = List.of(
        getLet(),
        getIdentifier("string-hola"),
        getColon(),
        getNumber(),
        getEquals(),
        getLiteral("hola"),
        getSemicolon()
    );

    ProgramNode root = new Parser().parse(input);
    System.out.println(root);
  }
}
