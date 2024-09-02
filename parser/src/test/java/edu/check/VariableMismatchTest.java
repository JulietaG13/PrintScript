package edu.check;

import static edu.TokenProvider.getColon;
import static edu.TokenProvider.getEquals;
import static edu.TokenProvider.getIdentifier;
import static edu.TokenProvider.getLet;
import static edu.TokenProvider.getLiteral;
import static edu.TokenProvider.getNumber;
import static edu.TokenProvider.getOperator;
import static edu.TokenProvider.getSemicolon;
import static edu.TokenProvider.getString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.Parser;
import edu.tokens.Token;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class VariableMismatchTest {

  @Test
  public void mismatchSimpleExpectedString() { // let a : String = 123.45
    String var = "var";
    BigDecimal value = new BigDecimal(123.45);

    List<Token> input =
        List.of(
            getLet(),
            getIdentifier(var),
            getColon(),
            getString(),
            getEquals(),
            getLiteral(value),
            getSemicolon());

    assertThrows(
        VariableTypeMismatchException.class,
        () -> {
          new Parser().parse(input);
        });
  }

  @Test
  public void mismatchSimpleExpectedNumber() { // let a : Number = "123.45"
    String var = "var";
    String value = "123.45";

    List<Token> input =
        List.of(
            getLet(),
            getIdentifier(var),
            getColon(),
            getNumber(),
            getEquals(),
            getLiteral(value),
            getSemicolon());

    assertThrows(
        VariableTypeMismatchException.class,
        () -> {
          new Parser().parse(input);
        });
  }

  @Test
  public void mismatchExpression() { // let a : String = 123.45 + 1 + 45 * 2
    String var = "var";

    List<Token> input =
        List.of(
            getLet(),
            getIdentifier(var),
            getColon(),
            getString(),
            getEquals(),
            getLiteral(new BigDecimal(123.45)),
            getOperator("+"),
            getLiteral(new BigDecimal(1)),
            getOperator("+"),
            getLiteral(new BigDecimal(45)),
            getOperator("*"),
            getLiteral(new BigDecimal(2)),
            getSemicolon());

    assertThrows(
        VariableTypeMismatchException.class,
        () -> {
          new Parser().parse(input);
        });
  }

  @Test
  public void mismatchMixedExpression() { // let a : Number = 123.45 * 1 + "hola" + 2
    String var = "var";

    List<Token> input =
        List.of(
            getLet(),
            getIdentifier(var),
            getColon(),
            getNumber(),
            getEquals(),
            getLiteral(new BigDecimal(123.45)),
            getOperator("*"),
            getLiteral(new BigDecimal(123.45)),
            getOperator("+"),
            getLiteral("hola"),
            getOperator("+"),
            getLiteral(new BigDecimal(2)),
            getSemicolon());

    assertThrows(
        VariableTypeMismatchException.class,
        () -> {
          new Parser().parse(input);
        });
  }

  @Test
  public void notMismatchMixedExpression() { // let a : String = 123.45 * 1 + "hola" + 2
    String var = "var";

    List<Token> input =
        List.of(
            getLet(),
            getIdentifier(var),
            getColon(),
            getString(),
            getEquals(),
            getLiteral(new BigDecimal(123.45)),
            getOperator("*"),
            getLiteral(new BigDecimal(1)),
            getOperator("+"),
            getLiteral("hola"),
            getOperator("+"),
            getLiteral(new BigDecimal(2)),
            getSemicolon());

    assertDoesNotThrow(
        () -> {
          new Parser().parse(input);
        });
  }
}
