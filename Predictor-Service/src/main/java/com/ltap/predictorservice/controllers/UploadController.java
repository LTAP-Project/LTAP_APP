package com.ltap.predictorservice.controllers;

import com.ltap.predictorservice.entities.Hobby;
import com.ltap.predictorservice.entities.Logic;
import com.ltap.predictorservice.entities.Prediction;
import com.ltap.predictorservice.services.PredictorService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UploadController {

  @Autowired PredictorService predictorService;

  @Autowired EntityManager entityManager;

  @PostMapping("/upload")
  @Transactional(rollbackOn = Exception.class)
  public void upload(
      @RequestParam("file") MultipartFile reapExcelDataFile,
      @RequestParam("fileP") MultipartFile reapExcelPrediction,
      @RequestParam("fileL") MultipartFile reapExcelLogic)
      throws IOException {
    List<Hobby> hobbies = new ArrayList<>();
    List<Prediction> predictions = new ArrayList<>();

    XSSFWorkbook workbook1 = new XSSFWorkbook(reapExcelPrediction.getInputStream());
    XSSFSheet worksheet1 = workbook1.getSheetAt(0);

    XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
    XSSFSheet worksheet = workbook.getSheetAt(0);

    XSSFWorkbook workbook2 = new XSSFWorkbook(reapExcelLogic.getInputStream());
    XSSFSheet worksheet2 = workbook2.getSheetAt(0);

    for (int i = 1; i < 121; i++) {

      XSSFRow row = worksheet2.getRow(i);
      String logicId = row.getCell(0).getRawValue().toString();
      String status = row.getCell(1).getStringCellValue();
      String emotion = row.getCell(2).getStringCellValue();
      String duration = row.getCell(3).getStringCellValue();
      String time = row.getCell(4).getStringCellValue();
      Logic logic =
          Logic.builder()
              .id(logicId)
              .status(status)
              .emotion(emotion)
              .duration(duration)
              .timeId(time)
              .build();
      entityManager.persist(logic);
    }

    for (int i = 1; i < 15; i++) {

      XSSFRow row = worksheet.getRow(i);
      String hobbyId = row.getCell(0).getStringCellValue();
      String hobbyName = row.getCell(1).getStringCellValue();

      hobbies.add(Hobby.builder().id(hobbyId).name(hobbyName).build());
    }

    for (int i = 1; i < 121; i++) {
      XSSFRow row = worksheet1.getRow(i);
      String logicId = row.getCell(0).getRawValue().toString();
      String hobbyIds = row.getCell(1).getStringCellValue();
      Prediction prediction = new Prediction();
      prediction.setId(logicId);
      List<String> hobbiez = Arrays.stream(hobbyIds.split(",")).collect(Collectors.toList());
      hobbiez.forEach(
          x -> {
            hobbies.forEach(
                k -> {
                  if (k.getId().equalsIgnoreCase(x)) {
                    Hobby hobby = findHobby(k);
                    Prediction prediction1 = findPrediction(prediction);
                    prediction1.addHobby(hobby);
                    entityManager.persist(prediction1);
                    hobby.addPrediction(prediction1);
                    entityManager.persist(hobby);
                  }
                });
          });
    }
  }

  public Hobby findHobby(Hobby hobby1) {
    Hobby hobby = entityManager.find(Hobby.class, hobby1.getId());

    if (hobby == null) return hobby1;

    return hobby;
  }

  public Prediction findPrediction(Prediction prediction1) {
    Prediction prediction = entityManager.find(Prediction.class, prediction1.getId());

    if (prediction == null) return prediction1;

    return prediction;
  }
}
