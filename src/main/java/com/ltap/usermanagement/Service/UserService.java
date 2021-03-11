package com.ltap.usermanagement.Service;

import com.ltap.usermanagement.controller.dtos.UserDto;
import com.ltap.usermanagement.entities.UserInfo;
import com.ltap.usermanagement.repository.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired UserRepo userRepo;

  public UserInfo saveUser(UserDto userDto) {
    UserInfo user = new UserInfo();
    BeanUtils.copyProperties(userDto, user);
    return userRepo.save(user);
  }
}
