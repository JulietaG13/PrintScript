package edu;

import edu.patterns.BooleanPattern;
import edu.patterns.IdentifierPattern;
import edu.patterns.KeywordPattern;
import edu.patterns.LiteralPattern;
import edu.patterns.LiteralsPattern;
import edu.patterns.NumberPattern;
import edu.patterns.OperatorPattern;
import edu.patterns.StringPattern;
import edu.patterns.SyntaxPattern;
import edu.patterns.TokenPattern;
import java.util.Iterator;
import java.util.List;

public class LexerFactory {

  public static Lexer createLexerV1(Iterator<String> file) {
    List<String> keywords = List.of("string", "number", "let");
    List<String> operators = List.of("+=", "-=", "*=", "/=", "%", "+", "-", "*", "/", "=");
    List<String> syntaxes = List.of(";", ":", "(", ")", "{", "}", ",");
    List<LiteralPattern> types = List.of(new StringPattern(), new NumberPattern());
    List<TokenPattern> patterns =
        List.of(
            new LiteralsPattern(types),
            new KeywordPattern(keywords),
            new OperatorPattern(operators),
            new SyntaxPattern(syntaxes),
            new IdentifierPattern());
    return new Lexer(file, patterns);
  }

  public static Lexer createLexerV2(Iterator<String> file) {
    List<String> keywords =
        List.of("else if", "if", "else", "string", "number", "let", "const", "boolean");
    List<String> operators = List.of("+=", "-=", "*=", "/=", "%", "+", "-", "*", "/", "=");
    List<String> syntaxes = List.of(";", ":", "(", ")", "{", "}", ",");
    List<LiteralPattern> types =
        List.of(new StringPattern(), new NumberPattern(), new BooleanPattern());
    List<TokenPattern> patterns =
        List.of(
            new LiteralsPattern(types),
            new KeywordPattern(keywords),
            new OperatorPattern(operators),
            new SyntaxPattern(syntaxes),
            new IdentifierPattern());
    return new Lexer(file, patterns);
  }
}
