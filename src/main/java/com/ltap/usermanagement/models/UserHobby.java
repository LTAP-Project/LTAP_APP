package com.ltap.usermanagement.models;

import com.ltap.usermanagement.entities.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Embeddable
public class UserHobby {

  @Id @GeneratedValue private String hobbyId;
  private Integer precedenceOrder;

}
