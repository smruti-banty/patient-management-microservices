package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.PatientUpdateDTO;
import com.pm.patientservice.model.Patient;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

public class PatientMapper {
  public static PatientResponseDTO toDTO(Patient patient) {
    var patientResponseDTO = new PatientResponseDTO();
    patientResponseDTO.setId(patient.getId().toString());
    BeanUtils.copyProperties(patient, patientResponseDTO);
    patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
    return patientResponseDTO;
  }

  public static Patient toModel(PatientRequestDTO patientRequestDTO) {
    var patient = new Patient();
    BeanUtils.copyProperties(patientRequestDTO, patient);
    patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
    patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
    return patient;
  }

  public static Patient toModel(PatientUpdateDTO patientUpdateDTO) {
    var patient = new Patient();
    BeanUtils.copyProperties(patientUpdateDTO, patient);
    patient.setDateOfBirth(LocalDate.parse(patientUpdateDTO.getDateOfBirth()));
    return patient;
  }
}
