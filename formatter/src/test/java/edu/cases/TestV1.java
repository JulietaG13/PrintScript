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

public class TestV1 {
  private static final JsonObject jsonDefaultRules;
  private static final JsonObject jsonNoExtraSpaceRules;

  private static final FormatterRuleProvider defaultRules;
  private static final FormatterRuleProvider noExtraSpaceRules;

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
    jsonDefaultRules.addProperty("println_new_lines_before_call", 1);
    defaultRules = FormatterRuleParser.parseRules(jsonDefaultRules);

    jsonNoExtraSpaceRules = new JsonObject();
    jsonNoExtraSpaceRules.addProperty("declaration_space_before_colon", false);
    jsonNoExtraSpaceRules.addProperty("declaration_space_after_colon", false);
    jsonNoExtraSpaceRules.addProperty("assignment_space_before_equals", false);
    jsonNoExtraSpaceRules.addProperty("assignment_space_after_equals", false);
    jsonNoExtraSpaceRules.addProperty("println_new_lines_before_call", 0);
    noExtraSpaceRules = FormatterRuleParser.parseRules(jsonNoExtraSpaceRules);
  }

  private static final String path = "src/test/java/resources/v1_0/";

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
  public void allSpaces() {
    TestCase test = new TestCase(path + "input-all-spaces.ps", path + "expected-all-spaces.ps");
    Map<String, Object> result = runTest(test, "1.0", defaultRules);
    assertTrue(isSuccess(result));
  }

  @Test
  public void noSpaces() {
    TestCase test = new TestCase(path + "input-no-spaces.ps", path + "expected-no-spaces.ps");
    Map<String, Object> result = runTest(test, "1.0", noExtraSpaceRules);
    assertTrue(isSuccess(result));
  }

  @Test
  public void allRules() {
    TestCase test = new TestCase(path + "input-all-rules.ps", path + "expected-all-rules.ps");
    Map<String, Object> result = runTest(test, "1.0", defaultRules);
    assertTrue(isSuccess(result));
  }

  @Test
  public void println0() {
    TestCase test =
        new TestCase(path + "print/input-print-0.ps", path + "print/expected-print-0.ps");
    Map<String, Object> result = runTest(test, "1.0", getRulesForPrint(0));
    assertTrue(isSuccess(result));
  }

  @Test
  public void println1() {
    TestCase test =
        new TestCase(path + "print/input-print-1.ps", path + "print/expected-print-1.ps");
    Map<String, Object> result = runTest(test, "1.0", getRulesForPrint(1));
    assertTrue(isSuccess(result));
  }

  @Test
  public void println2() {
    TestCase test =
        new TestCase(path + "print/input-print-2.ps", path + "print/expected-print-2.ps");
    Map<String, Object> result = runTest(test, "1.0", getRulesForPrint(2));
    assertTrue(isSuccess(result));
  }

  @Test
  public void spaceBeforeEquals() {
    TestCase test =
        new TestCase(
            path + "space_before_equals/input.ps", path + "space_before_equals/expected.ps");

    JsonObject rules = new JsonObject();
    rules.addProperty("assignment_space_before_equals", true);

    Map<String, Object> result = runTest(test, "1.0", FormatterRuleParser.parseRules(rules));
    assertTrue(isSuccess(result));
  }

  @Test
  public void spaceAfterEquals() {
    TestCase test =
        new TestCase(path + "space_after_equals/input.ps", path + "space_after_equals/expected.ps");

    JsonObject rules = new JsonObject();
    rules.addProperty("assignment_space_after_equals", true);

    Map<String, Object> result = runTest(test, "1.0", FormatterRuleParser.parseRules(rules));
    assertTrue(isSuccess(result));
  }

  @Test
  public void spaceBeforeColon() {
    TestCase test =
        new TestCase(path + "space_before_colon/input.ps", path + "space_before_colon/expected.ps");

    JsonObject rules = new JsonObject();
    rules.addProperty("declaration_space_before_colon", true);

    Map<String, Object> result = runTest(test, "1.0", FormatterRuleParser.parseRules(rules));
    assertTrue(isSuccess(result));
  }

  @Test
  public void spaceAfterColon() {
    TestCase test =
        new TestCase(path + "space_after_colon/input.ps", path + "space_after_colon/expected.ps");

    JsonObject rules = new JsonObject();
    rules.addProperty("declaration_space_after_colon", true);

    Map<String, Object> result = runTest(test, "1.0", FormatterRuleParser.parseRules(rules));
    assertTrue(isSuccess(result));
  }

  private FormatterRuleProvider getRulesForPrint(int newLines) {
    JsonObject json = jsonDefaultRules.deepCopy();
    json.addProperty("println_new_lines_before_call", newLines);
    return FormatterRuleParser.parseRules(json);
  }
}
