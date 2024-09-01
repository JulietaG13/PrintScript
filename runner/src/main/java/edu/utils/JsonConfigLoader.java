package edu.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonConfigLoader {
  public static JsonObject loadFromFile(String filePath) throws IOException {
    try (FileReader reader = new FileReader(new File(filePath))) {
      return JsonParser.parseReader(reader).getAsJsonObject();
    }
  }

  public static JsonObject loadFromString(String jsonString) {
    return JsonParser.parseString(jsonString).getAsJsonObject();
  }
}
