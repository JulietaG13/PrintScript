package edu.progress;

public class ConsoleProgressObserver implements ProgressObserver {

  @Override
  public void updateProgress(long bytesRead, long totalBytes) {
    int progress = (int) ((bytesRead * 100) / totalBytes);
    StringBuilder progressBar = new StringBuilder("[");
    int progressBars = progress / 2; // Barra de 50 caracteres

    for (int i = 0; i < 50; i++) {
      if (i < progressBars) {
        progressBar.append("=");
      } else {
        progressBar.append(" ");
      }
    }
    progressBar.append("] ").append(progress).append("%");
    System.out.print(progressBar + "\n\n");
  }
}
