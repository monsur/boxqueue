package com.monsur.boxqueue.adaptor;

// TODO(monsur): Add a field for the original url so it gets logged.

@SuppressWarnings("serial")
public class AdaptorException extends Exception {

  public AdaptorException() {
    super();
  }

  public AdaptorException(String msg) {
    super(msg);
  }

  public AdaptorException(Throwable cause) {
    super(cause);
  }

  public AdaptorException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
