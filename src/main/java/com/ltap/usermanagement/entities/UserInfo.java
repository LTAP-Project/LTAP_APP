package com.ltap.usermanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "UserInfo")
public class UserInfo {

  @Id @GeneratedValue private Integer id;
  private String email;
  private String firstName;
  private String lastName;
  private String dob;
  private String occupation;
  private String gender;
  private String status;
  private String education;
  private Integer age;
}
