package ast.interfaces;

import utils.LexicalRange;

public interface Node {
  LexicalRange start();
  LexicalRange end();
  // void accept(ASTVisitor visitor);
}
