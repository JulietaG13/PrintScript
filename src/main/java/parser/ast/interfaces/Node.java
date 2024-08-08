package parser.ast.interfaces;

import interpreter.interfaces.*;
import lexer.utils.LexicalRange;

public interface Node {
  LexicalRange start();
  LexicalRange end();
  void accept(ASTVisitor visitor);
}
