package com.eraykalkan.grpcloadbalancedinstance;

import com.eraykalkan.gprcserviceregistry.ServiceNameResolverProvider;
import com.eraykalkan.gprcserviceregistry.ServiceRegistry;
import com.eraykalkan.model.Balance;
import com.eraykalkan.model.BalanceCheckRequest;
import com.eraykalkan.model.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolverRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BankServiceDemo {

    public static void main(String[] args) {

        List<String> instanceList = new ArrayList<>();
        instanceList.add("localhost:3333");
        instanceList.add("localhost:3334");

        //ServiceRegistry.register("bank-service", List.of("localhost:3333","localhost:3334"));
        ServiceRegistry.register("bank-service", instanceList);

        NameResolverRegistry.getDefaultRegistry().register(new ServiceNameResolverProvider());

        // instead of using forAddress, now we should use forTarget
        // loadbalancing algorithm can be specified
        ManagedChannel managedChannel = ManagedChannelBuilder
                //.forAddress("localhost",8585)
                .forTarget("bank-service")
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext()
                .build();

        BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub =
                BankServiceGrpc.newBlockingStub(managedChannel);

        for (int i = 0; i < 10; i++) {
            BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder().setAccountNumber(
                    ThreadLocalRandom.current().nextInt(1,25)
            )
                    .build();
            Balance balance = bankServiceBlockingStub.getBalance(balanceCheckRequest);
            System.out.println(
                    "Received: " + balance.getAmount()
            );
        }

    }

}
