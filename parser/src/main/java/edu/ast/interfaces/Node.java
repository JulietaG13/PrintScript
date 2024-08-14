package edu.ast.interfaces;

import edu.ast.ASTVisitor;
import edu.utils.LexicalRange;

public interface Node {
  LexicalRange start();

  LexicalRange end();

  void accept(ASTVisitor visitor);
}
