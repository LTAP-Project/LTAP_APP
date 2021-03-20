package com.ltap.usermanagement;

import com.ltap.usermanagement.entities.UserInfo;
import com.ltap.usermanagement.entities.UserPreference;
import com.ltap.usermanagement.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
class UserInfoManagementApplicationTests {

  @Autowired EntityManager entityManager;

  @Autowired UserRepo userRepo;

  @Test
  @Transactional
  void contextLoads() {

    //    UserInfo userInfo = userRepo.findById(2L).get();

//    System.out.println(userInfo);

    UserInfo userInfo = entityManager.getReference(UserInfo.class, 2L);

    System.out.println(userInfo);

    //    if (userInfo == null) new RecordNotFoundException("No User Found");

    List<UserPreference> userPreferences = userInfo.getUserPreferences();
    userPreferences.forEach(System.out::println);
  }
}
