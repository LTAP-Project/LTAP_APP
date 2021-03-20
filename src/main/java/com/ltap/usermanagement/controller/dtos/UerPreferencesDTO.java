package com.ltap.usermanagement.controller.dtos;

import com.ltap.usermanagement.entities.UserPreference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UerPreferencesDTO {
  @NotNull(message = "PreferenceOrder cannot be null")
  private Integer preferenceOrder;

  @NotNull(message = "Hobby cannot be null")
  private String hobbyId;

  public static UerPreferencesDTO converter(UserPreference userPreference) {

    if (userPreference == null) {
      return new UerPreferencesDTO();
    }

    UerPreferencesDTO uerPreferencesDTO = new UerPreferencesDTO();

    BeanUtils.copyProperties(userPreference, uerPreferencesDTO);

    return uerPreferencesDTO;
  }
}
