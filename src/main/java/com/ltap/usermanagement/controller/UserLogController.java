package com.ltap.usermanagement.controller;

import com.ltap.usermanagement.Service.UserService;
import com.ltap.usermanagement.controller.dtos.UserLogDTO;
import com.ltap.usermanagement.entities.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.ltap.usermanagement.controller.urlconstants.UriContents.USER_LOG_CONTROLLER;

@RequestMapping(USER_LOG_CONTROLLER)
@RestController
@Validated
public class UserLogController {

  @Autowired private UserService userService;

  @PutMapping("/{userId}/logs")
  @ResponseStatus(code = HttpStatus.CREATED)
  public void updateUserHobby(
      @PathVariable Long userId, @Valid @RequestBody UserLogDTO userLogDTO) {

    userService.addUserLog(userId, UserLog.converter(userLogDTO));
  }

  @GetMapping("/{userId}/logs")
  public List<UserLogDTO> getUserLogs(
      @PathVariable @NotNull(message = "User Id can't be null") Long userId) {
    return userService.getAllUserLog(userId);
  }
}
