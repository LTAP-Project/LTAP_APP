package com.ltap.predictorservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTime {

  @Id private String id;

  @Column(columnDefinition = "TIME")
  private LocalTime startTime;

  @Column(columnDefinition = "TIME")
  private LocalTime endTime;
}
