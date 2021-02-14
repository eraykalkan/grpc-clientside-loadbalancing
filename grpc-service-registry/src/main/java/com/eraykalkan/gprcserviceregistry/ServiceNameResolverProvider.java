package com.eraykalkan.gprcserviceregistry;

import io.grpc.NameResolver;
import io.grpc.NameResolverProvider;

import java.net.URI;

public class ServiceNameResolverProvider extends NameResolverProvider {

    @Override
    protected boolean isAvailable() {
        return true;
    }

    @Override
    protected int priority() {
        return 5;
    }

    @Override
    public String getDefaultScheme() {
        return "dns";
    }

    @Override
    public NameResolver newNameResolver(URI targetUri, NameResolver.Args args) {
        System.out.println(
                "Looking for service: " + targetUri.toString()
        );
        return new ServiceNameResolver(targetUri.toString());
    }
}
