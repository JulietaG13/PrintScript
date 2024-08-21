package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.JsonObject;
import edu.utils.JsonConfigLoader;
import org.junit.jupiter.api.Test;

public class JsonConfigLoaderTest {
  @Test
  public void testLoadFromString() {
    String jsonString =
        "{\"declaration_space_before_colon\":true,\"declaration_space_after_colon\":true}";
    JsonObject jsonObject = JsonConfigLoader.loadFromString(jsonString);

    assertNotNull(jsonObject);
    assertEquals(true, jsonObject.get("declaration_space_before_colon").getAsBoolean());
    assertEquals(true, jsonObject.get("declaration_space_after_colon").getAsBoolean());
  }
}
