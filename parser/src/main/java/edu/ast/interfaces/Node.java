package edu.ast.interfaces;

import edu.ast.AstVisitor;
import edu.utils.LexicalRange;

public interface Node {
  LexicalRange start();

  LexicalRange end();

  void accept(AstVisitor visitor);
}
