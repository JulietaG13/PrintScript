import static edu.FileReader.openFile;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.google.gson.JsonObject;
import edu.commands.ValidatorCommand;
import edu.utils.CommandContext;
import edu.utils.JsonConfigLoader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ValidatorCommandTest {

  private JsonObject getRules(String configFile) throws IOException {
    return JsonConfigLoader.loadFromFile(configFile);
  }

  @Test
  public void testValidatorCommandWithValidInput() throws IOException {
    String testFilePath = "src/test/java/resources/input.txt";
    ValidatorCommand cmd = new ValidatorCommand(openFile(testFilePath), "1.0");
    cmd.run();
    CommandContext commandContext = cmd.getCommandContext();

    assertFalse(commandContext.hasError());
  }

  @Test
  public void testValidatorV2() throws IOException {
    String testFilePath = "src/test/java/resources/version2.txt";
    ValidatorCommand cmd = new ValidatorCommand(openFile(testFilePath), "1.1");
    cmd.run();
    CommandContext commandContext = cmd.getCommandContext();

    assertFalse(commandContext.hasError());
  }
}
