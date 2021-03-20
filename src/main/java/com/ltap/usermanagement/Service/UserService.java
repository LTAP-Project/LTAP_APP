package com.ltap.usermanagement.Service;

import com.ltap.usermanagement.controller.dtos.UerPreferencesDTO;
import com.ltap.usermanagement.controller.dtos.UserDto;
import com.ltap.usermanagement.entities.UserInfo;
import com.ltap.usermanagement.entities.UserLog;
import com.ltap.usermanagement.entities.UserPreference;
import com.ltap.usermanagement.exception.RecordNotFoundException;
import com.ltap.usermanagement.repository.UserPreferenceRepo;
import com.ltap.usermanagement.repository.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

  @Autowired UserRepo userRepo;

  @Autowired EntityManager entityManager;

  @Autowired UserPreferenceRepo userPreferenceRepo;

  public UserInfo saveUser(UserDto userDto) {
    UserInfo user = new UserInfo();
    BeanUtils.copyProperties(userDto, user);
    return userRepo.save(user);
  }

  public UserInfo getUserInfo(Long userId) {
    UserInfo userInfo = entityManager.find(UserInfo.class, userId);

    if (userInfo == null) throw new RecordNotFoundException("No User Found");
    return userInfo;
  }

  public UserInfo updateUserInfo(Long userId, UserDto userDto) {
    UserInfo userInfo = entityManager.find(UserInfo.class, userId);
    if (userInfo == null) throw new RecordNotFoundException("No User Found");
    BeanUtils.copyProperties(userDto, userInfo);
    return userRepo.save(userInfo);
  }

  public String getUserEmail(Long userId) {
    UserInfo userInfo = entityManager.find(UserInfo.class, userId);
    if (userInfo == null) throw new RecordNotFoundException("No User Found");
    return userInfo.getEmail();
  }

  public void deleteUser(Long userId) {
    userRepo.deleteById(userId);
  }

  public void updateUserHobbies(Long userId, List<UserPreference> UserPreference) {

    UserInfo userInfo = entityManager.find(UserInfo.class, userId);
    if (userInfo == null) throw new RecordNotFoundException("No User Found");
    userInfo.setUserPreferences(UserPreference);

    UserPreference.forEach(
        x -> {
          x.setUserInfo(userInfo);

          entityManager.persist(x);
        });

    entityManager.persist(userInfo);
  }

  public void addUserLog(Long userId, UserLog log) {

    UserInfo userInfo = entityManager.find(UserInfo.class, userId);

    UserLog userLog = new UserLog();

    BeanUtils.copyProperties(log, userLog);

    userLog.setLoginTime(LocalDateTime.now());

    userInfo.addULog(userLog);
    userLog.setUserDetail(userInfo);

    entityManager.persist(userInfo);
  }

  public List<UerPreferencesDTO> getUserHobby(Long userId) {

    UserInfo userInfo = entityManager.find(UserInfo.class, userId);

    if (userInfo == null) throw new RecordNotFoundException("No User Found");

    List<UserPreference> userPreferences = userInfo.getUserPreferences();

    if (userPreferences == null || userPreferences.isEmpty()) return Collections.emptyList();

    return userPreferences.stream().map(UerPreferencesDTO::converter).collect(Collectors.toList());
  }
}
