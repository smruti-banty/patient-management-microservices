package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.PatientUpdateDTO;
import com.pm.patientservice.excetption.EmailAlreadyExistsException;
import com.pm.patientservice.excetption.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {
  private final PatientRepository patientRepository;
  private final BillingServiceGrpcClient billingServiceGrpcClient;
  private final KafkaProducer kafkaProducer;

  public List<PatientResponseDTO> getPatients() {
    var patients = patientRepository.findAll();
    return patients.stream().map(PatientMapper::toDTO).toList();
  }

  public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
    if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
      throw new EmailAlreadyExistsException(String.format(
          "A patient with this email already exists %s", patientRequestDTO.getEmail()));
    }

    var patient = patientRepository.save(
        PatientMapper.toModel(patientRequestDTO));

    billingServiceGrpcClient.createBillingAccount(patient.getId().toString(),
        patient.getName(), patient.getEmail());

    kafkaProducer.sendEvent(patient);

    return PatientMapper.toDTO(patient);
  }

  public PatientResponseDTO updatePatient(UUID id, PatientUpdateDTO patientUpdateDTO) {
    var patient = patientRepository.findById(id).orElseThrow(() ->
        new PatientNotFoundException("Patient not found with ID: " + id));

    Patient newPatientDetail = PatientMapper.toModel(patientUpdateDTO);
    newPatientDetail.setId(patient.getId());
    newPatientDetail.setEmail(patient.getEmail());
    patientRepository.save(patient);

    return PatientMapper.toDTO(newPatientDetail);
  }

  public void deletePatient(UUID id) {
    patientRepository.deleteById(id);
  }
}
