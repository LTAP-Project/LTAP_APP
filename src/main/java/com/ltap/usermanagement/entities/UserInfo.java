package com.ltap.usermanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class UserInfo {

  @Id @GeneratedValue private Long id;
  private String email;
  private String firstName;
  private String lastName;
  private String dob;
  private String occupation;
  private String gender;
  private String status;
  private String education;
  private Integer age;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfo")
  private List<UserPreference> userPreferences;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "userDetail")
  private List<UserLog> userLogs;

  public void addULog(UserLog userLog) {
    userLogs.add(userLog);
  }
}
