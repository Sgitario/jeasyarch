package io.jeasyarch.configuration;

import java.util.Optional;

import io.jeasyarch.api.JEasyArch;
import io.jeasyarch.core.JEasyArchContext;

public final class JEasyArchConfigurationBuilder extends BaseConfigurationBuilder<JEasyArch, JEasyArchConfiguration> {

    private static final String TARGET = "target";
    private static final String ENABLE_PROFILING = "enable.profiling";

    @Override
    public JEasyArchConfiguration build() {
        JEasyArchConfiguration config = new JEasyArchConfiguration();
        loadString(TARGET, a -> a.target()).ifPresent(config::setTarget);
        loadBoolean(ENABLE_PROFILING, a -> a.enableProfiling()).ifPresent(config::setProfilingEnabled);
        return config;
    }

    @Override
    protected Optional<JEasyArch> getAnnotationConfig(String serviceName, JEasyArchContext context) {
        return context.getAnnotatedConfiguration(JEasyArch.class);
    }
}
