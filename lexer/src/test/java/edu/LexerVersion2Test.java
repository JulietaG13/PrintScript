package edu;

import static edu.LexerFactory.createLexerV2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.tokens.Token;
import edu.tokens.TokenType;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LexerVersion2Test {
  @Test
  public void testBoolean() {
    String code = "let X: Boolean = true;";
    Lexer lexer = createLexerV2(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();
    assertEquals(tokens.get(0).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(0).getEnd().getOffset(), 2);
    assertEquals(tokens.get(1).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(1).getEnd().getOffset(), 4);
    assertEquals(tokens.get(2).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(2).getEnd().getOffset(), 5);
    assertEquals(tokens.get(3).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(3).getEnd().getOffset(), 13);
    assertEquals(tokens.get(4).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(4).getEnd().getOffset(), 15);
    assertEquals(tokens.get(5).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(5).getEnd().getOffset(), 20);
    assertEquals(tokens.get(6).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(6).getEnd().getOffset(), 21);
  }

  @Test
  public void testConst() {
    String code = "const X: Boolean = true;";
    Lexer lexer = createLexerV2(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();
    assertEquals(tokens.get(0).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(0).getEnd().getOffset(), 4);
    assertEquals(tokens.get(1).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(1).getEnd().getOffset(), 6);
    assertEquals(tokens.get(2).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(2).getEnd().getOffset(), 7);
    assertEquals(tokens.get(3).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(3).getEnd().getOffset(), 15);
    assertEquals(tokens.get(4).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(4).getEnd().getOffset(), 17);
    assertEquals(tokens.get(5).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(5).getEnd().getOffset(), 22);
    assertEquals(tokens.get(6).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(6).getEnd().getOffset(), 23);
  }

  @Test
  public void testIf() {
    String code = "if (true) {\n  x = 1;\n}";
    Lexer lexer = createLexerV2(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();
    assertEquals(tokens.get(0).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(0).getEnd().getOffset(), 1);
    assertEquals(tokens.get(1).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(1).getEnd().getOffset(), 3);
    assertEquals(tokens.get(2).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(2).getEnd().getOffset(), 7);
    assertEquals(tokens.get(3).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(3).getEnd().getOffset(), 8);
    assertEquals(tokens.get(4).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(4).getEnd().getOffset(), 10);
    assertEquals(tokens.get(5).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(5).getEnd().getOffset(), 14);
    assertEquals(tokens.get(5).getEnd().getLine(), 1);
    assertEquals(tokens.get(6).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(6).getEnd().getOffset(), 16);
    assertEquals(tokens.get(7).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(7).getEnd().getOffset(), 18);
    assertEquals(tokens.get(8).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(8).getEnd().getOffset(), 19);
    assertEquals(tokens.get(9).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(9).getEnd().getOffset(), 21);
    assertEquals(tokens.get(9).getEnd().getLine(), 2);
  }

  @Test
  public void testElse() {
    String code = "if (true) {\n  x = 1;\n}else{\n  x = 2;\n}";
    Lexer lexer = createLexerV2(code);
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();
    assertEquals(tokens.get(0).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(0).getEnd().getOffset(), 1);
    assertEquals(tokens.get(1).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(1).getEnd().getOffset(), 3);
    assertEquals(tokens.get(2).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(2).getEnd().getOffset(), 7);
    assertEquals(tokens.get(3).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(3).getEnd().getOffset(), 8);
    assertEquals(tokens.get(4).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(4).getEnd().getOffset(), 10);
    assertEquals(tokens.get(5).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(5).getEnd().getOffset(), 14);
    assertEquals(tokens.get(5).getEnd().getLine(), 1);
    assertEquals(tokens.get(6).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(6).getEnd().getOffset(), 16);
    assertEquals(tokens.get(7).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(7).getEnd().getOffset(), 18);
    assertEquals(tokens.get(8).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(8).getEnd().getOffset(), 19);
    assertEquals(tokens.get(9).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(9).getEnd().getOffset(), 21);
    assertEquals(tokens.get(9).getEnd().getLine(), 2);
    assertEquals(tokens.get(10).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(10).getEnd().getOffset(), 25);
    assertEquals(tokens.get(10).getEnd().getLine(), 2);
    assertEquals(tokens.get(11).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(11).getEnd().getOffset(), 26);
    assertEquals(tokens.get(11).getEnd().getLine(), 2);
    assertEquals(tokens.get(12).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(12).getEnd().getOffset(), 30);
    assertEquals(tokens.get(12).getEnd().getLine(), 3);
    assertEquals(tokens.get(13).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(13).getEnd().getOffset(), 32);
    assertEquals(tokens.get(13).getEnd().getLine(), 3);
    assertEquals(tokens.get(14).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(14).getEnd().getOffset(), 34);
    assertEquals(tokens.get(14).getEnd().getLine(), 3);
    assertEquals(tokens.get(15).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(15).getEnd().getOffset(), 35);
  }
}
