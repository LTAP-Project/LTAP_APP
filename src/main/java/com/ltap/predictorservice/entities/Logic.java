package com.ltap.predictorservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Logic {

  @Id private String id;

  private String status;
  private String emotion;

  private String duration;

  private String timeId;
}
