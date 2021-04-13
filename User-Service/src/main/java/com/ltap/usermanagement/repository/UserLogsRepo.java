package com.ltap.usermanagement.repository;

import com.ltap.usermanagement.entities.UserLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogsRepo extends CrudRepository<UserLog, Long> {}
