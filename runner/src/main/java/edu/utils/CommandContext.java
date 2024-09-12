package edu.utils;

import edu.FormatterResult;
import edu.Report;

public class CommandContext {

  private Report linterReport;

  private FormatterResult formatterResult;

  private String name;

  private boolean hasError;

  public boolean hasError() {
    return hasError;
  }

  public void setHasError(boolean hasError) {
    this.hasError = hasError;
  }

  public Report getLinterReport() {
    return linterReport;
  }

  public void setLinterReport(Report linterReport) {
    this.linterReport = linterReport;
  }

  public FormatterResult getFormatterResult() {
    return formatterResult;
  }

  public void setFormatterResult(FormatterResult formatterResult) {
    this.formatterResult = formatterResult;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
