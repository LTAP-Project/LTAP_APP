package com.ltap.predictorservice.exception.aws;

public class CustomUsernameExistsException extends RuntimeException {
  public CustomUsernameExistsException(String msg) {
    super(msg);
  }
}