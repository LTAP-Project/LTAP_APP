package com.ltap.usermanagement.controller.dtos;

import com.ltap.usermanagement.entities.UserLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogDTO {

  private Long id;
  private String logicId;
  private String suggestionStatus;
  private Double feedBack;

  public static UserLogDTO converter(UserLog log) {

    if (log == null) {
      return new UserLogDTO();
    }

    UserLogDTO userLog = new UserLogDTO();

    BeanUtils.copyProperties(log, userLog);

    return userLog;
  }
}
