package com.ltap.usermanagement.repository;

import com.ltap.usermanagement.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserLogsRepo extends CrudRepository<UserInfo, Integer> {
}
