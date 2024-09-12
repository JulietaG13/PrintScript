package edu.progress;

import java.io.IOException;
import java.io.InputStream;

public class ProgressInputStreamWrapper extends InputStream {
  private final InputStream inputStream;
  private final long totalBytes;
  private final ProgressObserver observer;
  private long bytesRead;

  public ProgressInputStreamWrapper(InputStream inputStream, ProgressObserver observer) {
    this.inputStream = inputStream;
    try {
      this.totalBytes = inputStream.available();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.observer = observer;
    this.bytesRead = 0;
  }

  public static InputStream setProgress(InputStream inputStream) {
    ProgressObserver observer = new ConsoleProgressObserver();
    return new ProgressInputStreamWrapper(inputStream, observer);
  }

  @Override
  public int read() throws IOException {
    int byteRead = inputStream.read();
    if (byteRead != -1) {
      bytesRead++;
      notifyObserver();
    }
    return byteRead;
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    int numBytesRead = inputStream.read(b, off, len);
    if (numBytesRead != -1) {
      bytesRead += numBytesRead;
      notifyObserver();
    }
    return numBytesRead;
  }

  @Override
  public int available() throws IOException {
    return inputStream.available();
  }

  @Override
  public void close() throws IOException {
    inputStream.close();
  }

  private void notifyObserver() {
    if (observer != null) {
      observer.updateProgress(bytesRead, totalBytes);
    }
  }
}
