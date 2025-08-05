package com.pm.patientservice.kafka;

import com.pm.patientservice.model.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
  private final KafkaTemplate<String, byte[]> kafkaTemplate;

  public void sendEvent(Patient patient) {
    PatientEvent patientEvent = PatientEvent.newBuilder()
        .setPatientId(patient.getId().toString())
        .setName(patient.getName())
        .setEmail(patient.getEmail())
        .setEventType("PATIENT_CREATED")
        .build();

    try {
      kafkaTemplate.send("patient", patientEvent.toByteArray());
    } catch (Exception e) {
      log.error("Error sending PatientCreated event: {}, Error {}", patientEvent,
          e.getMessage());
    }
  }
}
