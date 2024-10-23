import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.JsonObject;
import edu.utils.DefaultRulesFactory;
import org.junit.jupiter.api.Test;

class DefaultRulesFactoryTest {

  @Test
  void testGetDefaultFormattingRulesV1() {
    DefaultRulesFactory factory = new DefaultRulesFactory("1.0");
    JsonObject rules = factory.getDefaultFormattingRules();

    assertNotNull(rules);

    assertEquals(true, rules.get("declaration_space_before_colon").getAsBoolean());
    assertEquals(true, rules.get("declaration_space_after_colon").getAsBoolean());
    assertEquals(true, rules.get("assignment_space_before_equals").getAsBoolean());
    assertEquals(true, rules.get("assignment_space_after_equals").getAsBoolean());
    assertEquals(1, rules.get("println_new_lines_before_call").getAsInt());
  }

  @Test
  void testGetDefaultFormattingRulesV2() {
    DefaultRulesFactory factory = new DefaultRulesFactory("1.1");
    JsonObject rules = factory.getDefaultFormattingRules();

    assertNotNull(rules);

    assertEquals(true, rules.get("declaration_space_before_colon").getAsBoolean());
    assertEquals(true, rules.get("declaration_space_after_colon").getAsBoolean());
    assertEquals(true, rules.get("assignment_space_before_equals").getAsBoolean());
    assertEquals(true, rules.get("assignment_space_after_equals").getAsBoolean());
    assertEquals(1, rules.get("println_new_lines_before_call").getAsInt());
    assertEquals(4, rules.get("indent").getAsInt());
  }

  @Test
  void testGetDefaultLintingRulesV1() {
    DefaultRulesFactory factory = new DefaultRulesFactory("1.0");
    JsonObject lintingRules = factory.getDefaultLintingRules();

    assertNotNull(lintingRules);

    assertEquals("camel case", lintingRules.get("identifier_format").getAsString());
    assertEquals(
        true, lintingRules.get("mandatory-variable-or-literal-in-readInput").getAsBoolean());
  }

  @Test
  void testGetDefaultLintingRulesV2() {
    DefaultRulesFactory factory = new DefaultRulesFactory("1.1");
    JsonObject lintingRules = factory.getDefaultLintingRules();

    assertNotNull(lintingRules);

    assertEquals("snake case", lintingRules.get("identifier_format").getAsString());
    assertEquals(true, lintingRules.get("mandatory-variable-or-literal-in-println").getAsBoolean());
  }

  @Test
  void testInvalidVersion() {
    DefaultRulesFactory factory = new DefaultRulesFactory("2.0");

    assertThrows(IllegalArgumentException.class, factory::getDefaultFormattingRules);
    assertThrows(IllegalArgumentException.class, factory::getDefaultLintingRules);
  }
}
