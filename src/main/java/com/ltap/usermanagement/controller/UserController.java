package com.ltap.usermanagement.controller;

import com.ltap.usermanagement.Service.UserService;
import com.ltap.usermanagement.controller.dtos.UserDto;
import com.ltap.usermanagement.entities.UserInfo;
import com.ltap.usermanagement.outboundServices.feignClient.authenticationService.CognitoAuthenticationService;
import com.ltap.usermanagement.outboundServices.feignClient.dto.HobbiesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static com.ltap.usermanagement.controller.urlconstants.UriContents.USER_CONTROLLER;

@RestController
@RequestMapping(USER_CONTROLLER)
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CognitoAuthenticationService cognitoAuthenticationService;

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserInfo> save(@Valid @RequestBody UserDto user) {
        cognitoAuthenticationService.signUp(user.getEmail(), user.getFirstName(), user.getPassword());
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/{userId}")
    public UserInfo getUser(@PathVariable Long userId) {
        return userService.getUserInfo(userId);
    }


    @GetMapping("/{userId}/predictor")
    public HobbiesDTO getUserPredictorHobbies(@PathVariable Long userId, @RequestParam String time,
                                              @RequestParam String status,
                                              @RequestParam String emotion,
                                              @RequestParam String duration) {
        return userService.getUserPredictedHobby(userId, time, status, emotion, duration);
    }


    @GetMapping("/{uEmail}/info")
    public UserInfo getUserByEmail(@PathVariable @Valid @Email(message = "Email should be valid") String uEmail) {
        return userService.getUserByEmail(uEmail);
    }


    @PutMapping("/{userId}")
    public UserInfo updateUser(
            @PathVariable @NotNull(message = "User Id can't be null") Long userId,
            @Valid @RequestBody UserDto user) {
        return userService.updateUserInfo(userId, user);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable @NotNull(message = "User Id can't be null") Long userId) {
        cognitoAuthenticationService.deleteUser(userService.getUserEmail(userId));
        userService.deleteUser(userId);
        return "User SuccessFully Deleted";
    }
}
