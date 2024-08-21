package edu.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class KeywordProviderTest {

  @Test
  void getKeyword() {
    assertEquals(Keyword.STRING, KeywordProvider.getKeyword("String"));
    assertEquals(Keyword.NUMBER, KeywordProvider.getKeyword("Number"));
  }

  @Test
  void getKeywordName() {
    assertEquals("String", KeywordProvider.getKeywordName(Keyword.STRING));
    assertEquals("Number", KeywordProvider.getKeywordName(Keyword.NUMBER));
  }
}
