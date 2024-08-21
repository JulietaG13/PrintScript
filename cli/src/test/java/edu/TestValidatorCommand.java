package edu;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ast.ProgramNode;
import org.junit.jupiter.api.Test;

public class TestValidatorCommand {

  @Test
  public void testValidatorCommandWithValidInput() {
    String input = "let a: Number = 5; println(a);";
    String[] args = {"Validation", input, "1.0"};
    Cli cli = new Cli(args);
    cli.run();
    ProgramNode programNode = cli.getCommandContext().getProgramNode();
    assertNotNull(
        programNode, "El ProgramNode no debería ser nulo después de ejecutar ValidatorCommand.");
    assertTrue(
        programNode.getBody().size() > 0, "El ProgramNode debería tener al menos un statement.");
  }
}
