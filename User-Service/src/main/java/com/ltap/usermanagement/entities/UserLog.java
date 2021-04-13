package com.ltap.usermanagement.entities;

import com.ltap.usermanagement.controller.dtos.UserLogDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class UserLog {

  @Id @GeneratedValue private Long id;
  private String logicId;
  private LocalDateTime loginTime;
  private String suggestionStatus;
  private Double feedBack;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserInfo userDetail;

  public static UserLog converter(UserLogDTO log) {

    if (log == null) {
      return new UserLog();
    }

    UserLog userLog = new UserLog();

    BeanUtils.copyProperties(log, userLog);

    return userLog;
  }

  @Override
  public String toString() {
    return "UserLog{"
        + "id="
        + id
        + ", logicId='"
        + logicId
        + '\''
        + ", loginTime="
        + loginTime
        + ", suggestionStatus='"
        + suggestionStatus
        + '\''
        + ", feedBack="
        + feedBack
        + '}';
  }
}
