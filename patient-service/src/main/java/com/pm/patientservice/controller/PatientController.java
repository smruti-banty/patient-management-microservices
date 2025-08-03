package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.PatientUpdateDTO;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
  private PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping
  @Operation(summary = "Get patients")
  public ResponseEntity<List<PatientResponseDTO>> getPatients() {
    return ResponseEntity.ok(patientService.getPatients());
  }

  @PostMapping
  @Operation(summary = "Create a new patients")
  public ResponseEntity<PatientResponseDTO> createPatient(
      @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
    return ResponseEntity.status(201).body(
        patientService.createPatient(patientRequestDTO));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update a patients")
  public ResponseEntity<PatientResponseDTO> updatePatient(
      @Valid @Size(max = 36, min = 36,
          message = "Size must be 36") @PathVariable String id,
      @Valid @RequestBody PatientUpdateDTO patientUpdateDTO) {
    return ResponseEntity.ok(
        patientService.updatePatient(UUID.fromString(id), patientUpdateDTO));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a patients")
  public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }
}
