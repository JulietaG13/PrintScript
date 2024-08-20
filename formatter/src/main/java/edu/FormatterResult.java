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

  public void writeLineSeparator(int times) {
    stringBuilder.append(lineSeparator.repeat(times));
  }

  public void endLine() {
    stringBuilder.append(";").append(lineSeparator);
  }

  public String getResult() {
    return stringBuilder.toString();
  }
}
