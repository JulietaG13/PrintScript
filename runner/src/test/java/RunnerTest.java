import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.Runner;
import org.junit.jupiter.api.Test;

class RunnerTest {

  private Runner runner;
  private String version;
  private String testFilePath;
  private String configFilePath;

  @Test
  void testRunnerInitialization() {
    version = "1.0";
    testFilePath = "src/test/java/resources/test.txt";
    configFilePath = "src/test/java/resources/config.txt";
    runner = new Runner(version);
    assertNotNull(runner.versionFactory, "VersionFactory no debería ser nulo.");
    assertNotNull(runner.commandContext, "CommandContext no debería ser nulo.");
  }
}
