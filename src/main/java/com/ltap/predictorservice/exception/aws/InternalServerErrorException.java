package com.ltap.predictorservice.exception.aws;

public class InternalServerErrorException extends RuntimeException {

  public InternalServerErrorException(String msg) {
    super(msg);
  }
}
