package com.ltap.usermanagement.controller;

import com.ltap.usermanagement.Service.UserService;
import com.ltap.usermanagement.controller.dtos.UerPreferencesDTO;
import com.ltap.usermanagement.entities.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static com.ltap.usermanagement.controller.urlconstants.UriContents.USER_PREFERENCE_CONTROLLER;

@RequestMapping(USER_PREFERENCE_CONTROLLER)
@RestController
@Validated
public class UserHobbyController {

  @Autowired private UserService userService;

  @PutMapping("/{userId}/hobbies")
  @ResponseStatus(code = HttpStatus.CREATED)
  public void updateUserHobby(
      @PathVariable Long userId, @RequestBody List<UerPreferencesDTO> uerPreferencesDTOS) {

    List<UserPreference> userPreferences =
        uerPreferencesDTOS.parallelStream()
            .map(UserPreference::converter)
            .collect(Collectors.toList());
    userService.updateUserHobbies(userId, userPreferences);
  }

  @GetMapping("/{userId}/hobbies")
  public List<UerPreferencesDTO> getUserPreferences(
      @PathVariable @NotNull(message = "User Id can't be null") Long userId) {
    return userService.getUserHobby(userId);
  }
}
