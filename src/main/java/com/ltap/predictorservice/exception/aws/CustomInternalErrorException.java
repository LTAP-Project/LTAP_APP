package com.ltap.predictorservice.exception.aws;

public class CustomInternalErrorException extends RuntimeException {
  public CustomInternalErrorException(String msg) {
    super(msg);
  }
}
