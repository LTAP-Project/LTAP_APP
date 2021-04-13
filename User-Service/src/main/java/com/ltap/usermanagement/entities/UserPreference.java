package com.ltap.usermanagement.entities;

import com.ltap.usermanagement.controller.dtos.UerPreferencesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreference {

  @Id @GeneratedValue private Long id;

  private Integer preferenceOrder;

  private String hobbyId;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserInfo userInfo;

  public static UserPreference converter(UerPreferencesDTO uerPreferencesDTO) {

    if (uerPreferencesDTO == null) {
      return new UserPreference();
    }

    UserPreference userPreference = new UserPreference();

    BeanUtils.copyProperties(uerPreferencesDTO, userPreference);

    return userPreference;
  }

  @Override
  public String toString() {
    return "UserPreference{" + "id=" + id + ", hobbyId='" + hobbyId + '\'' + '}';
  }
}
