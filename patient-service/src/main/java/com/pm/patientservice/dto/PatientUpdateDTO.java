package com.pm.patientservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientUpdateDTO {
  @NotBlank(message = "Name is required")
  @Size(max = 100, message = "Name cannot exceed 100 characters")
  private String name;

  @NotBlank(message = "Address is required")
  private String address;

  @NotBlank(message = "Date of birth is required")
  private String dateOfBirth;
}
