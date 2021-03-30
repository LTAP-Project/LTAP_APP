package com.ltap.predictorservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hobby {

  @Id private String id;

  private String name;

  @ManyToMany(mappedBy = "hobbies")
  private List<Prediction> prediction = new ArrayList<>();

  public void addPrediction(Prediction obj) {
    if (prediction == null || prediction.isEmpty()) {
      if (prediction == null) prediction = new ArrayList<>();
      prediction.add(obj);
    } else if (!prediction.contains(obj)) {
      prediction.add(obj);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Hobby)) return false;
    Hobby hobby = (Hobby) o;
    return getId().equals(hobby.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  @Override
  public String toString() {
    return "Hobby{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
  }
}
