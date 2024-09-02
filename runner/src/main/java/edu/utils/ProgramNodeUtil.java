package edu.utils;

import static edu.FileReader.openFile;

import edu.Lexer;
import edu.Parser;
import edu.ast.ProgramNode;
import java.io.IOException;

public class ProgramNodeUtil {

  public static ProgramNode getProgramNode(String sourceFile, String version) throws IOException {
    VersionFactory versionFactory = new VersionFactory(version);
    Lexer lexer = versionFactory.createLexer(openFile(sourceFile));
    lexer.tokenize();
    Parser parser = versionFactory.createParser(lexer);
    return parser.parse(lexer.getTokens());
  }

  public static Parser getParser(String sourceFile, String version) throws IOException {
    VersionFactory versionFactory = new VersionFactory(version);
    Lexer lexer = versionFactory.createLexer(openFile(sourceFile));
    lexer.tokenize();
    Parser parser = versionFactory.createParser(lexer);
    return parser;
  }
}
