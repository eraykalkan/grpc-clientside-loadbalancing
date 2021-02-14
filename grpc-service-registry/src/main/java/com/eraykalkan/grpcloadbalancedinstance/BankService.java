package com.eraykalkan.grpcloadbalancedinstance;

import com.eraykalkan.model.Balance;
import com.eraykalkan.model.BalanceCheckRequest;
import com.eraykalkan.model.BankServiceGrpc;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {

        System.out.println("Recevied request for account number: " + request.getAccountNumber());

        int accountNumber = request.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(accountNumber * 10)
                .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();

    }

}
