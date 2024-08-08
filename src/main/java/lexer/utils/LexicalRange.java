package lexer.utils;

public class LexicalRange {
  private final int offset;
  private final int line;
  private final int column;

  public LexicalRange(int offset, int line, int column) {
    this.offset = offset;
    this.line = line;
    this.column = column;
  }

  public int getOffset() {
    return offset;
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }
}
