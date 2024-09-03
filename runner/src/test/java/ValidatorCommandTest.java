import static edu.FileReader.openFile;

import edu.ast.ProgramNode;
import edu.commands.ValidatorCommand;
import edu.utils.CommandContext;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidatorCommandTest {

  @Test
  public void testValidatorCommandWithValidInput() throws IOException {
    String testFilePath = "src/test/java/resources/input.txt";
    ValidatorCommand cmd = new ValidatorCommand(openFile(testFilePath), "1.0");
    try {
      cmd.run();
    } catch (IOException e) {
      Assertions.fail("Execution failed with IOException: " + e.getMessage());
    }

    CommandContext commandContext = cmd.getCommandContext();
    ProgramNode programNode = commandContext.getProgramNode();

    Assertions.assertNotNull(
        programNode, "The ProgramNode should not be " + "null after executing ValidatorCommand.");

    Assertions.assertTrue(
        programNode.getBody() != null && !programNode.getBody().isEmpty(),
        "The ProgramNode should have at least one statement.");
  }
}
