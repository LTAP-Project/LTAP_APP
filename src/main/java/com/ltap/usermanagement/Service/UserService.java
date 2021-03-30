package com.ltap.usermanagement.Service;

import com.ltap.usermanagement.controller.dtos.UerPreferencesDTO;
import com.ltap.usermanagement.controller.dtos.UserDto;
import com.ltap.usermanagement.controller.dtos.UserLogDTO;
import com.ltap.usermanagement.entities.UserInfo;
import com.ltap.usermanagement.entities.UserLog;
import com.ltap.usermanagement.entities.UserPreference;
import com.ltap.usermanagement.exception.RecordNotFoundException;
import com.ltap.usermanagement.outboundServices.feignClient.dto.HobbiesDTO;
import com.ltap.usermanagement.outboundServices.feignClient.predictorProxyService.PredictorProxy;
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

    @Autowired
    UserRepo userRepo;

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserPreferenceRepo userPreferenceRepo;

    @Autowired
    PredictorProxy predictorProxy;


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

    public List<UserLogDTO> getAllUserLog(Long userId) {
        UserInfo userInfo = entityManager.find(UserInfo.class, userId);

        if (userInfo == null) throw new RecordNotFoundException("No User Found");

        List<UserLog> userLogs = userInfo.getUserLogs();

        if (userLogs == null || userLogs.isEmpty()) return Collections.emptyList();

        return userLogs.stream().map(UserLogDTO::converter).collect(Collectors.toList());
    }

    public HobbiesDTO getUserPredictedHobby(Long userId, String time,
                                            String status,
                                            String emotion,
                                            String duration) {
        UserInfo userInfo = entityManager.find(UserInfo.class, userId);
        if (userInfo == null) throw new RecordNotFoundException("No User Found");
        List<HobbiesDTO> hobbiesDTOS = predictorProxy.getPredictorHobbies(time, status, emotion, duration);
        List<UserPreference> userPreferences = userInfo.getUserPreferences();
        int min = 1000;
        HobbiesDTO hobbiesDTO = new HobbiesDTO();

        for (UserPreference userPreference : userPreferences) {

            for (HobbiesDTO hobbiesDTO1 : hobbiesDTOS) {
                if (userPreference.getHobbyId().equals(hobbiesDTO1.getId())) {
                    if (min > userPreference.getPreferenceOrder()) {
                        min = userPreference.getPreferenceOrder();
                        hobbiesDTO = hobbiesDTO1;
                    }
                }
            }
        }

        if (hobbiesDTO.getId() == null || hobbiesDTO.getId().isEmpty())
            hobbiesDTO = new HobbiesDTO("H15", "Take a nap");

        return hobbiesDTO;
    }


    public UserInfo getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(new UserInfo());
    }
}
