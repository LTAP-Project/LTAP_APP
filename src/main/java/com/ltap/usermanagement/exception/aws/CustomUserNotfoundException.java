package com.ltap.usermanagement.exception.aws;

public class CustomUserNotfoundException extends RuntimeException {

  public CustomUserNotfoundException(String msg){
    super(msg);
  }

}
