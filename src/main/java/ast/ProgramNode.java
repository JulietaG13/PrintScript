package ast;

import ast.interfaces.Node;
import utils.LexicalRange;

public class ProgramNode implements Node {
  private final LexicalRange start;
  private final LexicalRange end;
  
  public ProgramNode(LexicalRange start, LexicalRange end) {
    this.start = start;
    this.end = end;
  }
  
  @Override
  public LexicalRange start() {
    return start;
  }
  
  @Override
  public LexicalRange end() {
    return end;
  }
}
