package com.ltap.usermanagement.controller;

import com.ltap.usermanagement.Service.UserService;
import com.ltap.usermanagement.controller.dtos.UserDto;
import com.ltap.usermanagement.entities.UserInfo;
import com.ltap.usermanagement.outboundServices.feignClient.authenticationService.CognitoAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ltap.usermanagement.controller.urlconstants.UriContants.USER_CONTROLLER;

@RestController
@RequestMapping(USER_CONTROLLER)
@Validated
public class UserController {

  @Autowired UserService userService;

  @Autowired CognitoAuthenticationService cognitoAuthenticationService;

  @PostMapping()
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<UserInfo> save(@Valid @RequestBody UserDto user) {
    cognitoAuthenticationService.signUp(user.getEmail(), user.getFirstName(), user.getPassword());
    return ResponseEntity.ok(userService.saveUser(user));
  }

  @GetMapping("/{userId}")
  public UserInfo getUser(@PathVariable Integer userId) {
    return userService.getUserInfo(userId);
  }

  @PutMapping("/{userId}")
  public UserInfo updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto user) {
    return userService.updateUserInfo(userId, user);
  }

  @DeleteMapping("/{userId}")
  public String deleteUser(@PathVariable Integer userId) {
    cognitoAuthenticationService.deleteUser(userService.getUserEmail(userId));
    userService.deleteUser(userId);
    return "User SuccessFully Deleted";
  }
}
