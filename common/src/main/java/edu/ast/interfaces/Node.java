package edu.ast.interfaces;

import edu.LexicalRange;
import edu.ast.AstVisitor;

public interface Node {
  LexicalRange start();

  LexicalRange end();

  void accept(AstVisitor visitor);
}
