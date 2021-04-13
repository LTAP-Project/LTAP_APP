package com.ltap.predictorservice.controllers;

import com.ltap.predictorservice.entities.Hobby;
import com.ltap.predictorservice.services.PredictorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PredictorController {

  @Autowired PredictorService predictorService;

  @GetMapping("/hobbies")
  public List<Hobby> findHobbies(
      @RequestParam String time,
      @RequestParam String status,
      @RequestParam String emotion,
      @RequestParam String duration) {

    return predictorService.findHobbies(time, status, emotion, duration);
  }
}
