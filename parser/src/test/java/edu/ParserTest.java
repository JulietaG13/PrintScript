package edu;

import static edu.ParserTestUtil.createInputStreamFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.IfStatementNode;
import edu.ast.statements.VariableDeclarationNode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ParserTest {

  @Test
  public void test() {
    String filePath = "src/test/java/resources/input.txt";
    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromFile(filePath));
    Parser parser = ParserFactory.createParserV2(lexer);

    int numberOfStatements = 10;
    List<StatementNode> statements = new ArrayList<>(numberOfStatements);
    while (parser.hasNext()) {
      statements.add(parser.next());
    }

    assertEquals(numberOfStatements, statements.size());

    assert statements.get(0) instanceof VariableDeclarationNode;
    assert statements.get(1) instanceof VariableDeclarationNode;
    assert statements.get(2) instanceof VariableDeclarationNode;

    assert statements.get(3) instanceof ExpressionStatementNode;

    assert statements.get(4) instanceof VariableDeclarationNode;
    assert statements.get(5) instanceof VariableDeclarationNode;

    assert statements.get(6) instanceof ExpressionStatementNode;

    assert statements.get(7) instanceof VariableDeclarationNode;

    assert statements.get(8) instanceof IfStatementNode;
    assert statements.get(9) instanceof IfStatementNode;
  }
}
