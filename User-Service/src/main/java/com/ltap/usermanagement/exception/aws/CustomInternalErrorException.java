package com.ltap.usermanagement.exception.aws;

public class CustomInternalErrorException extends RuntimeException {
  public CustomInternalErrorException(String msg) {
    super(msg);
  }
}
