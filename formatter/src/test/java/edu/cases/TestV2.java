package edu.cases;

import static edu.testrunner.TestRunner.isSuccess;
import static edu.testrunner.TestRunner.runTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import edu.rules.FormatterRuleParser;
import edu.rules.FormatterRuleProvider;
import edu.testrunner.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestV2 {
  private static final JsonObject jsonDefaultRules;
  private static final FormatterRuleProvider defaultRules;

  /* Configurable
   * declaration_space_before_colon
   * declaration_space_after_colon
   * assignment_space_before_equals
   * assignment_space_after_equals
   * println_new_lines_before_call
   */

  static {
    jsonDefaultRules = new JsonObject();
    jsonDefaultRules.addProperty("declaration_space_before_colon", true);
    jsonDefaultRules.addProperty("declaration_space_after_colon", true);
    jsonDefaultRules.addProperty("assignment_space_before_equals", true);
    jsonDefaultRules.addProperty("assignment_space_after_equals", true);
    jsonDefaultRules.addProperty("println_new_lines_before_call", 0);

    defaultRules = FormatterRuleParser.parseRules(jsonDefaultRules);
  }

  private static final String path = "src/test/java/resources/v1_1/";

  private static final ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
  private static final PrintStream originalOut = System.out;

  @BeforeAll
  static void setup() {
    System.setOut(new PrintStream(testOutput));
  }

  @AfterAll
  static void cleanup() {
    System.setOut(originalOut);
    testOutput.reset();
  }

  @Test
  public void ifIndent0() {
    TestCase test = new TestCase(path + "input-if-indent-0.ps", path + "expected-if-indent-0.ps");
    Map<String, Object> result = runTest(test, "1.1", getRulesForIndent(0));
    assertTrue(isSuccess(result));
  }

  @Test
  public void ifIndent2() {
    TestCase test = new TestCase(path + "input-if-indent-2.ps", path + "expected-if-indent-2.ps");
    Map<String, Object> result = runTest(test, "1.1", getRulesForIndent(2));
    assertTrue(isSuccess(result));
  }

  @Test
  public void ifElseBrackets() {
    TestCase test =
        new TestCase(path + "input-if-else-brackets.ps", path + "expected-if-else-brackets.ps");
    Map<String, Object> result = runTest(test, "1.1", getRulesForIndent(0));
    assertTrue(isSuccess(result));
  }

  private FormatterRuleProvider getRulesForIndent(int indent) {
    JsonObject json = jsonDefaultRules.deepCopy();
    json.addProperty("indent", indent);
    return FormatterRuleParser.parseRules(json);
  }
}
