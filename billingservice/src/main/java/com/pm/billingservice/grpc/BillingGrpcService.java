package com.pm.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
  @Override
  public void createBillingAccount(BillingRequest request, StreamObserver<BillingResponse> responseObserver) {
    log.info("CreateBillingAccount request received {}", request.toString());

    // Business logic - e.g save to database, perform calculates etc
    var response = BillingResponse.newBuilder()
        .setAccountId("12345")
        .setStatus("ACTIVE")
        .build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
