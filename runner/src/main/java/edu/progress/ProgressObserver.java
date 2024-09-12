package edu.progress;

public interface ProgressObserver {
  void updateProgress(long bytesRead, long totalBytes);
}
