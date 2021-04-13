package com.ltap.usermanagement.exception.aws;

public class CustomNotAuthorizedException extends RuntimeException{

  public CustomNotAuthorizedException(String msg){

    super(msg);
  }
}
