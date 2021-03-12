package com.ltap.usermanagement.controller;

import com.ltap.usermanagement.controller.dtos.UserDto;
import com.ltap.usermanagement.entities.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ltap.usermanagement.controller.urlconstants.UriContants.USER_HOBBY_CONTROLLER;

@RequestMapping(USER_HOBBY_CONTROLLER)
public class UserHobbyController {

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public void save(@Valid @RequestBody UserDto user) {
        cognitoAuthenticationService.signUp(user.getEmail(), user.getFirstName(), user.getPassword());

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
