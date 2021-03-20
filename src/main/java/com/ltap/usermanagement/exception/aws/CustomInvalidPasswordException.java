package com.ltap.usermanagement.exception.aws;

public class CustomInvalidPasswordException extends RuntimeException {

  public CustomInvalidPasswordException(String msg) {
    super(msg);
  }
}
