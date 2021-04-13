package com.ltap.usermanagement.controller.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

  private Long id;

  @NotNull(message = "Email cannot be null")
  @Pattern(
      regexp =
          "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$",
      message = "Email is incorrect")
  private String email;

  @NotNull(message = "First name cannot be null")
  @NotEmpty(message = "First name cannot be empty")
  private String firstName;

  @NotNull(message = "Last name cannot be null")
  @NotEmpty(message = "Last name cannot be empty")
  private String lastName;

  @NotNull(message = "Dob cannot be null")
  @NotEmpty(message = "Dob  cannot be empty")
  private String dob;

  @NotNull(message = "Occupation cannot be null")
  @NotEmpty(message = "Occupation cannot be empty")
  private String occupation;

  @NotNull(message = "Gender cannot be null")
  @NotEmpty(message = "Gender cannot be empty")
  private String gender;

  @NotNull(message = "Status cannot be null")
  @NotEmpty(message = "Status cannot be empty")
  private String status;

  @NotNull(message = "Education cannot be null")
  @NotEmpty(message = "Education cannot be empty")
  private String education;

  @NotNull(message = "Age cannot be null")
  private Integer age;

  @NotNull(message = "Password cannot be null")
  @NotEmpty(message = "Password cannot be empty")
  private String password;
}
