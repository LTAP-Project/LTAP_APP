package com.ltap.usermanagement.outboundServices.feignClient.authenticationService;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.ltap.usermanagement.exception.aws.InternalServerErrorException;
import com.ltap.usermanagement.outboundServices.AmazonClient;
import com.ltap.usermanagement.outboundServices.feignClient.authenticationService.AuthenticationServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CognitoAuthenticationService implements AuthenticationServiceProxy {

  @Value("${aws.userPoolId}")
  private String userPoolId;

  @Value("${aws.clientId}")
  private String clientId;

  private @Autowired
  AmazonClient amazonClientService;

  private static final String rolePrefix = "ROLE_";

  @Override
  public Boolean signUp(String email, String username, String password) {

    try {
      AWSCognitoIdentityProvider cognitoClient =
          amazonClientService.getAmazonCognitoIdentityClient();

      SignUpRequest signUpRequest =
          new SignUpRequest()
              .withClientId(clientId)
              .withPassword(password)
              .withUsername(username)
              .withUserAttributes(new AttributeType().withName("email").withValue(email));
      SignUpResult createUserResult = cognitoClient.signUp(signUpRequest);

      if (createUserResult.getUserSub() != null && !createUserResult.getUserSub().isEmpty()) {

        AdminUpdateUserAttributesRequest adminUpdateUserAttributesRequest = new AdminUpdateUserAttributesRequest()
            .withUserPoolId(userPoolId).withUsername(username)
            .withUserAttributes(new AttributeType().withName("email_verified").withValue("true"));

        AdminUpdateUserAttributesResult adminUpdateUserAttributesResult = cognitoClient
            .adminUpdateUserAttributes(adminUpdateUserAttributesRequest);
        adminConfirmSignUp(username);
        addUserToGroup(username,"super admin");
        return true;
      }
      return false;

    } catch (InvalidPasswordException ex) {
      throw new InternalServerErrorException("Invalid password");
    } catch (NotAuthorizedException ex) {
      throw new InternalServerErrorException("Request is not authorized");
    } catch (UsernameExistsException ex) {
      throw new InternalServerErrorException("Username already exists");
    } catch (InternalErrorException ex) {
      throw new InternalServerErrorException("Something went wrong. Please try again later");
    }
  }

  @Override
  public Boolean addUserToGroup(String username, String userType) {

    try {
      AWSCognitoIdentityProvider cognitoClient =
          amazonClientService.getAmazonCognitoIdentityClient();

      AdminAddUserToGroupRequest addUserToGroupRequest =
          new AdminAddUserToGroupRequest()
              .withGroupName(convertToRoleTypeName(userType))
              .withUsername(username)
              .withUserPoolId(userPoolId);

      AdminAddUserToGroupResult adminAddUserToGroupResult =
          cognitoClient.adminAddUserToGroup(addUserToGroupRequest);

      if (adminAddUserToGroupResult != null) {
        return true;
      }
      return false;

    } catch (UserNotFoundException ex) {
      throw new InternalServerErrorException("User is not found");
    } catch (NotAuthorizedException ex) {
      throw new InternalServerErrorException("Request is not authorized");
    } catch (InternalErrorException ex) {
      throw new InternalServerErrorException("Something went wrong. Please try again later");
    } catch (Exception ex) {
      throw new InternalServerErrorException(ex.getLocalizedMessage());
    }
  }

  @Override
  public Boolean deleteUser(String username) {
    try {
      AWSCognitoIdentityProvider cognitoClient =
          amazonClientService.getAmazonCognitoIdentityClient();

      AdminDeleteUserRequest adminDeleteUserRequest =
          new AdminDeleteUserRequest().withUsername(username).withUserPoolId(userPoolId);

      AdminDeleteUserResult adminDeleteUserResult =
          cognitoClient.adminDeleteUser(adminDeleteUserRequest);

      if (adminDeleteUserResult != null) {
        return true;
      }
      return false;
    } catch (NotAuthorizedException ex) {
      throw new InternalServerErrorException("Request is not authorized");
    } catch (UserNotFoundException ex) {
      throw new InternalServerErrorException("User is not found");
    } catch (InternalErrorException ex) {
      throw new InternalServerErrorException("Something went wrong. Please try again later");
    }
  }

  @Override
  public Boolean adminConfirmSignUp(String username) {
    try {
      AWSCognitoIdentityProvider cognitoClient =
          amazonClientService.getAmazonCognitoIdentityClient();

      AdminConfirmSignUpRequest adminConfirmSignUpRequest =
          new AdminConfirmSignUpRequest().withUsername(username).withUserPoolId(userPoolId);

      AdminConfirmSignUpResult adminConfirmSignUpResult =
          cognitoClient.adminConfirmSignUp(adminConfirmSignUpRequest);

      if (adminConfirmSignUpResult != null) {
        return true;
      }
      return false;

    } catch (NotAuthorizedException ex) {
      throw new InternalServerErrorException("Request is not authorized");
    } catch (UserNotFoundException ex) {
      throw new InternalServerErrorException("User is not found");
    } catch (InternalErrorException ex) {
      throw new InternalServerErrorException("Something went wrong. Please try again later");
    }
  }

  @Override
  public Boolean enableUser(String username) {
    try {
      AWSCognitoIdentityProvider cognitoClient =
          amazonClientService.getAmazonCognitoIdentityClient();

      AdminEnableUserRequest adminEnableUserRequest =
          new AdminEnableUserRequest().withUsername(username).withUserPoolId(userPoolId);

      AdminEnableUserResult adminEnableUserResult =
          cognitoClient.adminEnableUser(adminEnableUserRequest);

      if (adminEnableUserResult != null) {
        return true;
      }
      return false;

    } catch (NotAuthorizedException ex) {
      throw new InternalServerErrorException("Request is not authorized");
    } catch (UserNotFoundException ex) {
      throw new InternalServerErrorException("User is not found");
    } catch (InternalErrorException ex) {
      throw new InternalServerErrorException("Something went wrong. Please try again later");
    }


  }

  @Override
  public Boolean disableUser(String username) {
    try {
      AWSCognitoIdentityProvider cognitoClient =
          amazonClientService.getAmazonCognitoIdentityClient();

      AdminDisableUserRequest adminDisableUserRequest =
          new AdminDisableUserRequest().withUsername(username).withUserPoolId(userPoolId);

      AdminDisableUserResult adminDisableUserResult =
          cognitoClient.adminDisableUser(adminDisableUserRequest);

      if (adminDisableUserResult != null) {
        return true;
      }
      return false;

    } catch (NotAuthorizedException ex) {
      throw new InternalServerErrorException("Request is not authorized");
    } catch (UserNotFoundException ex) {
      throw new InternalServerErrorException("User is not found");
    } catch (InternalErrorException ex) {
      throw new InternalServerErrorException("Something went wrong. Please try again later");
    }
  }

  @Override
  public String getUserNameByAccessToken(String accessToken) {
    try {
      AWSCognitoIdentityProvider cognitoClient =
              amazonClientService.getAmazonCognitoIdentityClient();

      GetUserRequest getUserRequest = new GetUserRequest().withAccessToken(accessToken);
      GetUserResult getUserResult = cognitoClient.getUser(getUserRequest);
      return getUserResult.getUsername();

    } catch (NotAuthorizedException ex) {
      throw new InternalServerErrorException("Request is not authorized");
    } catch (UserNotFoundException ex) {
      throw new InternalServerErrorException("User is not found");
    } catch (InternalErrorException ex) {
      throw new InternalServerErrorException("Something went wrong. Please try again later");
    }
  }
  @Override
  public Boolean updateUser(String updatedUsername,String existingUserName) {
    try {
      AWSCognitoIdentityProvider cognitoClient =
          amazonClientService.getAmazonCognitoIdentityClient();
      AdminUpdateUserAttributesRequest adminUpdateUserAttributesRequest = new AdminUpdateUserAttributesRequest()
          .withUserPoolId(userPoolId).withUsername(existingUserName)
          .withUserAttributes(new AttributeType().withName("preferred_username").withValue(updatedUsername));
      AdminUpdateUserAttributesResult adminUpdateUserAttributesResult = cognitoClient
          .adminUpdateUserAttributes(adminUpdateUserAttributesRequest);
      return false;
    } catch (NotAuthorizedException ex) {
      throw new InternalServerErrorException("Request is not authorized");
    } catch (UserNotFoundException ex) {
      throw new InternalServerErrorException("User is not found");
    } catch (InternalErrorException ex) {
      throw new InternalServerErrorException("Something went wrong. Please try again later");
    }
  }

  private String convertToRoleTypeName(String userType){
    return rolePrefix + userType.replaceAll(" ", "_").toUpperCase();
  }
}
