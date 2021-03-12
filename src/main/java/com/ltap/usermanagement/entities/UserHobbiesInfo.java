package com.ltap.usermanagement.entities;

import com.ltap.usermanagement.models.UserHobbies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "UserHobbiesInfo")
public class UserHobbiesInfo {

  @Id private Integer id;
  private Integer user_id;
  @ElementCollection private List<UserHobbies> hobbies = new ArrayList<>();
}
