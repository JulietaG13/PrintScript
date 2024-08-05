package ast;

import ast.interfaces.Node;
import ast.interfaces.StatementNode;
import utils.LexicalRange;
import java.util.ArrayList;
import java.util.List;

public class ProgramNode implements Node {
  private final List<StatementNode> body = new ArrayList<>();

  public List<StatementNode> getBody() {
    return body;
  }

  public void addStatement(StatementNode statement) {
    body.add(statement);
  }

  @Override
  public LexicalRange start() {
    return new LexicalRange(0,0,0);
  }
  
  @Override
  public LexicalRange end() {
    return null;  // TODO()
  }
}
