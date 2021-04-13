package com.ltap.predictorservice.exception.aws;

public class CustomInvalidPasswordException extends RuntimeException {

  public CustomInvalidPasswordException(String msg) {
    super(msg);
  }
}
