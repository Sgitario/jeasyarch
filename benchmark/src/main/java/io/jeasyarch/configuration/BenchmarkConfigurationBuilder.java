package io.jeasyarch.configuration;

import java.util.Optional;

import io.jeasyarch.core.JEasyArchContext;

public final class BenchmarkConfigurationBuilder
        extends BaseConfigurationBuilder<io.jeasyarch.api.BenchmarkConfiguration, BenchmarkConfiguration> {

    private static final String OUTPUT_LOCATION = "benchmark.output-location";

    @Override
    public BenchmarkConfiguration build() {
        BenchmarkConfiguration config = new BenchmarkConfiguration();
        loadString(OUTPUT_LOCATION, a -> a.outputLocation()).ifPresent(config::setOutputLocation);
        return config;
    }

    @Override
    protected Optional<io.jeasyarch.api.BenchmarkConfiguration> getAnnotationConfig(String serviceName,
            JEasyArchContext context) {
        return context.getAnnotatedConfiguration(io.jeasyarch.api.BenchmarkConfiguration.class);
    }
}
