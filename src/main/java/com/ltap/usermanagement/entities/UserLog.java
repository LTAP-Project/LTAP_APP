package com.ltap.usermanagement.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "UserLog")
public class UserLog {

  @Id private Integer id;
  private String logicId;
  @LastModifiedDate private DateTime lastLogin;
  private String suggestionStatus;
}
