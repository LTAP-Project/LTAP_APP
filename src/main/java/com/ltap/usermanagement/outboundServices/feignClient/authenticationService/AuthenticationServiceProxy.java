package com.ltap.usermanagement.outboundServices.feignClient.authenticationService;

public interface AuthenticationServiceProxy {

  Boolean signUp(String email, String username, String password);

  Boolean addUserToGroup(String email, String userType);

  Boolean deleteUser(String username);

  Boolean adminConfirmSignUp(String username);

  Boolean enableUser(String username);

  Boolean disableUser(String username);

  String getUserNameByAccessToken(String accessToken);

  Boolean updateUser(String updatedUsername,String existingUserName);
}
