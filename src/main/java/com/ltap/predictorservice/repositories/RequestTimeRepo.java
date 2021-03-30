package com.ltap.predictorservice.repositories;

import com.ltap.predictorservice.entities.RequestTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface RequestTimeRepo extends CrudRepository<RequestTime, String> {

  Optional<RequestTime> findByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(LocalTime time,LocalTime time1);
}
