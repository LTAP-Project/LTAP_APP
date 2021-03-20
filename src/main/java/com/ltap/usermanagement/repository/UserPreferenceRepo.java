package com.ltap.usermanagement.repository;

import com.ltap.usermanagement.entities.UserPreference;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferenceRepo extends CrudRepository<UserPreference, Long> {

  //  Optional<UserPreferences> findByUserId(int id);
}
