package edu.utils;

import edu.Lexer;
import edu.Parser;
import java.io.InputStream;

public class ProgramNodeUtil {

  public static Parser getParser(InputStream inputStream, String version) {
    VersionFactory versionFactory = new VersionFactory(version);
    Lexer lexer = versionFactory.createLexer(inputStream);
    Parser parser = versionFactory.createParser(lexer);
    return parser;
  }
}
