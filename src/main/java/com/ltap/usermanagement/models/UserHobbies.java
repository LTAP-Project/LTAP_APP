package com.ltap.usermanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserHobbies {

  private String hobbyId;
  private Integer precedenceOrder;
}
