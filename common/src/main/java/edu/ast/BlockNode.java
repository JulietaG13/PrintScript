package edu.ast;

import edu.LexicalRange;
import edu.ast.interfaces.Node;
import edu.ast.interfaces.StatementNode;
import java.util.List;

public record BlockNode(LexicalRange start, LexicalRange end, List<StatementNode> statements)
    implements Node {

  @Override
  public void accept(AstVisitor visitor) { // TODO
    visitor.visit(this);
  }
}
