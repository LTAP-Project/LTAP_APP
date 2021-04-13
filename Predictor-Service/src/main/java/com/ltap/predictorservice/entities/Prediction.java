package com.ltap.predictorservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prediction {

  @Id private String id;

  @ManyToMany
  @JoinTable(
      name = "PREDICTION_HOBBY",
      joinColumns = @JoinColumn(name = "logic_id"),
      inverseJoinColumns = @JoinColumn(name = "hobby_id"))
  private List<Hobby> hobbies = new ArrayList<>();

  public void addHobby(Hobby hobby) {
    if (hobbies==null || hobbies.isEmpty()) {
      hobbies.add(hobby);
    } else if (!hobbies.contains(hobby)) {
      hobbies.add(hobby);
    }
  }

  @Override
  public String toString() {
    return "Prediction{" + "id='" + id + '\'' + '}';
  }
}
