package io.jeasyarch.core.extensions;

import io.jeasyarch.api.extensions.ExtensionBootstrap;
import io.jeasyarch.configuration.BenchmarkConfigurationBuilder;
import io.jeasyarch.core.EnableBenchmark;
import io.jeasyarch.core.JEasyArchContext;

public class BenchmarkExtensionBootstrap implements ExtensionBootstrap {

    public static final String CONFIGURATION = "benchmark";

    @Override
    public boolean appliesFor(JEasyArchContext context) {
        return EnableBenchmark.class.isAssignableFrom(context.getTestContext().getRequiredTestClass());
    }

    @Override
    public void beforeAll(JEasyArchContext context) {
        context.loadCustomConfiguration(CONFIGURATION, new BenchmarkConfigurationBuilder());
    }
}
