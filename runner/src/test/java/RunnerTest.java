import static edu.FileReader.openFile;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.JsonObject;
import edu.Runner;
import edu.utils.JsonConfigLoader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class RunnerTest {

  private Runner runner;
  private String version;
  private String testFilePath;
  private String configFilePath;

  private final String lineSeparator = System.lineSeparator();

  @Test
  void testRunnerInitialization() {
    version = "1.0";
    testFilePath = "src/test/java/resources/test.txt";
    configFilePath = "src/test/java/resources/config.txt";
    runner = new Runner(version);
    assertNotNull(runner.versionFactory, "VersionFactory no debería ser nulo.");
  }

  @Test
  void testFormattingExceptionFromRunner() {
    version = "1.0";
    String filePath = "src/test/java/resources/input.txt";
    String rulesPath = "src/test/java/resources/rules.txt";

    assertDoesNotThrow(() -> new Runner(version).format(openFile(filePath), getRules(rulesPath)));
  }

  @Test
  void testAnalyzerExceptionFromRunner() {
    version = "1.0";
    String rulesPath = "src/test/java/resources/analysis_rules.txt";
    String filePath = "src/test/java/resources/analysis_input.txt";

    assertDoesNotThrow(() -> new Runner(version).analyze(openFile(filePath), getRules(rulesPath)));
  }

  private JsonObject getRules(String configFile) throws IOException {
    return JsonConfigLoader.loadFromFile(configFile);
  }
}
