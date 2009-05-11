package com.monsur.boxqueue.adaptor;

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
