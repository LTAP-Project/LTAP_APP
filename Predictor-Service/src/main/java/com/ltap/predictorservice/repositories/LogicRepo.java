package com.ltap.predictorservice.repositories;

import com.ltap.predictorservice.entities.Logic;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LogicRepo extends CrudRepository<Logic, String> {

  Optional<Logic> findByStatusAndEmotionAndDurationAndTimeId(
      String status, String emotion, String duration, String timeId);
}
