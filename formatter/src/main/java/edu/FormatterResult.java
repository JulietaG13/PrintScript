package edu;

public class FormatterResult {
  private static final String lineSeparator = System.lineSeparator();

  private StringBuilder stringBuilder = new StringBuilder();

  public void write(String string) {
    stringBuilder.append(string);
  }

  public void writeSpace() {
    stringBuilder.append(" ");
  }

  public void lineSeparator(int times, int indent) {
    stringBuilder.append(lineSeparator.repeat(times));
    if (times > 0) {
      stringBuilder.append(" ".repeat(indent));
    }
  }

  public void lineSeparator(int indent) {
    lineSeparator(1, indent);
  }

  public void endLine() {
    stringBuilder.append(";").append(lineSeparator);
  }

  public String getResult() {
    return stringBuilder.toString();
  }
}
