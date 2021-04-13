package com.ltap.usermanagement.repository;

import com.ltap.usermanagement.entities.UserInfo;
import com.ltap.usermanagement.entities.UserLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<UserInfo, Long> {

    Optional<UserInfo> findByEmail(String email);
}
