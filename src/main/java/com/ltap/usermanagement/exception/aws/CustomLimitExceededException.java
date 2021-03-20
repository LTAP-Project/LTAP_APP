package com.ltap.usermanagement.exception.aws;

public class CustomLimitExceededException extends RuntimeException{

  public CustomLimitExceededException(String msg){
    super(msg);
  }
}
