package edu.utils;

import static edu.FileReader.openFile;
import static edu.LexerFactory.createLexerV1;

import edu.FileReader;
import edu.Lexer;
import edu.Parser;
import edu.ast.ProgramNode;
import java.io.IOException;

public class ProgramNodeUtils {

  public static ProgramNode getProgramNode(String sourceFile) throws IOException {
    Lexer lexer = createLexerV1(openFile(sourceFile));
    lexer.tokenize();
    Parser parser = new Parser();
    return parser.parse(lexer.getTokens());
  }

  public static String getFileAsString(String sourceFile) throws IOException {
    return FileReader.readFileToString(sourceFile);
  }
}
