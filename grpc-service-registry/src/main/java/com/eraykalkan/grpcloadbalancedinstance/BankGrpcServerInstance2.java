package com.eraykalkan.grpcloadbalancedinstance;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class BankGrpcServerInstance2 {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(3334)
                .addService(new BankService())
                .build();

        server.start();

        server.awaitTermination();

    }

}
