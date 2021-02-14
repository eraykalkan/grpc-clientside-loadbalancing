package com.eraykalkan.gprcserviceregistry;

import io.grpc.EquivalentAddressGroup;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceRegistry {

    public static final Map<String, List<EquivalentAddressGroup>> MAP = new HashMap<>();

    public static void register(String serviceName, List<String> instances) {

        List<EquivalentAddressGroup> addressGroups = instances.stream()
                .map(instance -> instance.split(":"))
                .map(instanceArray -> new InetSocketAddress(instanceArray[0], Integer.parseInt(instanceArray[1])))
                .map(EquivalentAddressGroup::new)
                .collect(Collectors.toList());

        MAP.put(serviceName, addressGroups);
    }

    public static List<EquivalentAddressGroup> getInstances(String serviceName) {
        return MAP.get(serviceName);
    }

}
