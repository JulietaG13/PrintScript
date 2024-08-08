package ast.interfaces;

import interpreter.interfaces.*;
import utils.LexicalRange;

public interface Node {
  LexicalRange start();
  LexicalRange end();
  void accept(ASTVisitor visitor);
}
