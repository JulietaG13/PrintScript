package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

public class TestAnalysisCommand {
  private static final JsonObject noRules;
  private static final JsonObject nonExpression;

  static {
    noRules = new JsonObject();
    noRules.addProperty("lower_camel_case", true);

    nonExpression = new JsonObject();
    nonExpression.addProperty("lower_camel_case", true);
    nonExpression.addProperty("println_non_expressions", true);
  }

  @Test
  public void testAnalyzerCommandWithNonExpressionRule() {
    String code = "println(10);";
    String[] args = {"Analyzing", code, "1.0", noRules.toString()};
    Cli cli = new Cli(args);
    cli.run();
    Report report = cli.getCommandContext().getLinterReport();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testLiteralStringPrint() {
    String code = "println(\"Hello\");";
    String[] args = {"Analyzing", code, "1.0", nonExpression.toString()};
    Cli cli = new Cli(args);
    cli.run();
    Report report = cli.getCommandContext().getLinterReport();
    assertTrue(report.getReport().isEmpty());
  }

  @Test
  public void testExpressionError() {
    String code = "println(\"Hello\" + \"World\");";
    String[] args = {"Analyzing", code, "1.0", nonExpression.toString()};
    Cli cli = new Cli(args);
    cli.run();
    Report report = cli.getCommandContext().getLinterReport();
    assertFalse(report.getReport().isEmpty());

    String expectedOutput =
        "Error in println function: The println function "
            + "only accepts identifiers or literals as arguments.\n"
            + "Argument 1 is invalid:\n"
            + " - Type: BinaryExpressionNode\n"
            + " - Position: LexicalRange(offset=8, line=0, column=8)\n"
            + " - Content: \"Hello\" + \"World\"";

    assertEquals(1, report.getMessages().size());
    assertEquals(expectedOutput, report.getMessages().get(0));
  }

  @Test
  public void testExpressionCallError() {
    String code = "println(hello());";
    String[] args = {"Analyzing", code, "1.0", nonExpression.toString()};
    Cli cli = new Cli(args);
    cli.run();
    Report report = cli.getCommandContext().getLinterReport();
    assertFalse(report.getReport().isEmpty());

    String expectedOutput =
        "Error in println function: The println function only "
            + "accepts identifiers or literals as arguments.\n"
            + "Argument 1 is invalid:\n"
            + " - Type: CallExpressionNode\n"
            + " - Position: LexicalRange(offset=8, line=0, column=8)\n"
            + " - Content: hello()";

    assertEquals(1, report.getMessages().size());
    assertEquals(expectedOutput, report.getMessages().get(0));
  }
}
