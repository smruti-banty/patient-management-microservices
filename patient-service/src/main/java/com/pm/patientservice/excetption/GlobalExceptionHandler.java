package com.pm.patientservice.excetption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setTitle("Validation failed");
    problemDetail.setDetail("One or more fields are invalid");

    var errors = new HashMap<String, Object>();
    ex.getBindingResult().getFieldErrors().forEach(
        error -> errors.put(error.getField(), error.getDefaultMessage()));
    problemDetail.setProperty("fieldErrors", errors);

    return problemDetail;
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ProblemDetail handleEmailAlreadyExistsException(
      EmailAlreadyExistsException ex) {
    log.warn("Email address already exist {}", ex.getMessage());
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setTitle("Validation failed");
    problemDetail.setDetail("Email address already exists");
    return problemDetail;
  }

  @ExceptionHandler(PatientNotFoundException.class)
  public ProblemDetail handlePatientNotFoundException(
      PatientNotFoundException ex) {
    log.warn("Patient not found {}", ex.getMessage());
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setTitle("Validation failed");
    problemDetail.setDetail("Patient not found");
    return problemDetail;
  }
}
