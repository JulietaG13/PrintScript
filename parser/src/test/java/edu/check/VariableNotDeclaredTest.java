package edu.check;

import static edu.TokenProvider.getCloseParen;
import static edu.TokenProvider.getColon;
import static edu.TokenProvider.getEquals;
import static edu.TokenProvider.getIdentifier;
import static edu.TokenProvider.getLet;
import static edu.TokenProvider.getLiteral;
import static edu.TokenProvider.getNumber;
import static edu.TokenProvider.getOpenParen;
import static edu.TokenProvider.getOperator;
import static edu.TokenProvider.getSemicolon;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.Parser;
import edu.tokens.Token;
import java.util.List;
import org.junit.jupiter.api.Test;

class VariableNotDeclaredTest {

  @Test
  public void simpleNotDeclared() { // var = 123.45
    String var = "var";
    double value = 123.45;

    List<Token> input = List.of(getIdentifier(var), getEquals(), getLiteral(value), getSemicolon());

    assertThrows(
        VariableNotDeclaredException.class,
        () -> {
          new Parser().parse(input);
        });
  }

  @Test
  public void similarNames() { // let Var : Number; var = 123.45;
    double value = 123.45;

    List<Token> input =
        List.of(
            getLet(),
            getIdentifier("Var"),
            getColon(),
            getNumber(),
            getSemicolon(),
            getIdentifier("var"),
            getEquals(),
            getLiteral(value),
            getSemicolon());

    assertThrows(
        VariableNotDeclaredException.class,
        () -> {
          new Parser().parse(input);
        });
  }

  @Test
  public void notDeclaredInExpression() { // let var : Number; var = 123.45 + 2 * other;
    String var = "var";
    double value = 123.45;

    List<Token> input =
        List.of(
            getLet(),
            getIdentifier(var),
            getColon(),
            getNumber(),
            getSemicolon(),
            getIdentifier(var),
            getEquals(),
            getLiteral(value),
            getOperator("+"),
            getLiteral(2),
            getOperator("*"),
            getIdentifier("other"),
            getSemicolon());

    assertThrows(
        VariableNotDeclaredException.class,
        () -> {
          new Parser().parse(input);
        });
  }

  @Test
  public void notDeclaredInFunction() { // f(var);
    String function = "f";
    String var = "var";

    List<Token> input =
        List.of(
            getIdentifier(function),
            getOpenParen(),
            getIdentifier(var),
            getCloseParen(),
            getSemicolon());

    assertThrows(
        VariableNotDeclaredException.class,
        () -> {
          new Parser().parse(input);
        });
  }

  @Test
  public void noExceptionInFunction() { // f("var");
    String function = "f";
    String var = "var";

    List<Token> input =
        List.of(
            getIdentifier(function),
            getOpenParen(),
            getLiteral(var),
            getCloseParen(),
            getSemicolon());

    assertDoesNotThrow(
        () -> {
          new Parser().parse(input);
        });
  }
}
