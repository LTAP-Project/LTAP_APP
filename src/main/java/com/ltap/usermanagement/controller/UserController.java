package com.ltap.usermanagement.controller;

import com.ltap.usermanagement.Service.UserService;
import com.ltap.usermanagement.controller.dtos.UserDto;
import com.ltap.usermanagement.entities.UserInfo;
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

  @PostMapping()
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<UserInfo> save(@Valid @RequestBody UserDto user) {
    return ResponseEntity.ok(userService.saveUser(user));
  }
}
