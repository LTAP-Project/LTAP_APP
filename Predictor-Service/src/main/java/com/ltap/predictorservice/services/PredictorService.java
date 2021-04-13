package com.ltap.predictorservice.services;

import com.ltap.predictorservice.entities.Hobby;
import com.ltap.predictorservice.entities.Logic;
import com.ltap.predictorservice.entities.Prediction;
import com.ltap.predictorservice.repositories.LogicRepo;
import com.ltap.predictorservice.repositories.RequestTimeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class PredictorService {

  @Autowired RequestTimeRepo requestTimeRepo;

  @Autowired LogicRepo logicRepo;

  @Autowired EntityManager entityManager;

  public List<Hobby> findHobbies(String time, String status, String emotion, String duration) {
    LocalTime localTime = LocalTime.parse(time);
    String timeId =
        requestTimeRepo
            .findByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(localTime, localTime)
            .get()
            .getId();

    Logic logic =
        logicRepo
            .findByStatusAndEmotionAndDurationAndTimeId(status, emotion, duration, timeId)
            .get();
    Prediction prediction = entityManager.find(Prediction.class, logic.getId());

    return prediction.getHobbies();
  }
}
