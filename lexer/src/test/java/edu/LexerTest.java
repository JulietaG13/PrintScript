package edu;

import static edu.LexerFactory.createLexerV1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.tokens.Token;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LexerTest {

  private Lexer lexer;

  @Test
  public void testTokenize() throws FileNotFoundException {
    String testDirectory = "src/test/resources/test1/";
    File srcFile = new File(testDirectory + "input.txt");
    InputStream fileInputStream = new FileInputStream(srcFile);

    if (!srcFile.exists()) {
      throw new FileNotFoundException("File not found: " + srcFile.getAbsolutePath());
    }

    lexer = createLexerV1(fileInputStream);

    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    for (Token token : tokens) {
      System.out.println(token.tokenString());
    }
  }

  @Test
  public void testLexicalRangeToString() {
    LexicalRange range = new LexicalRange(15, 5, 1);
    assertEquals("LexicalRange(offset=15, line=5, column=1)", range.toString());
  }
}
