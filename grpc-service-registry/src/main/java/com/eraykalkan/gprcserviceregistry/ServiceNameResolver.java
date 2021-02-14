package com.eraykalkan.gprcserviceregistry;

import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;

import java.util.List;

public class ServiceNameResolver extends NameResolver {

    private final String serviceName;

    public ServiceNameResolver(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String getServiceAuthority() {
        return "temp";
    }

    @Override
    public void shutdown() {

    }

    /**
     * this method can be used to refresh instance list if some error / exception occurs
     */
    @Override
    public void refresh() {
        super.refresh();
    }

    /**
     * sends available instance list to subscribers / listeners
     * @param listener
     */
    @Override
    public void start(Listener2 listener) {
        List<EquivalentAddressGroup> addressGroups = ServiceRegistry.getInstances(this.serviceName);
        ResolutionResult resolutionResult = ResolutionResult.newBuilder().setAddresses(addressGroups).build();
        listener.onResult(resolutionResult);
    }
}
