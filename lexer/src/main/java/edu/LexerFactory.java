package edu;

import edu.finders.PatternManager;
import edu.finders.RegexPatternMatcher;
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
import java.io.InputStream;
import java.util.List;

public class LexerFactory {

  public static Lexer createLexerV1(InputStream file) {
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
    return new Lexer(new Reader(file), new PatternManager(patterns, new RegexPatternMatcher()));
  }

  public static Lexer createLexerV2(InputStream file) {
    List<String> keywords = List.of("if", "else", "string", "number", "let", "const", "boolean");
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
    return new Lexer(new Reader(file), new PatternManager(patterns, new RegexPatternMatcher()));
  }
}
