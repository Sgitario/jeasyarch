package io.jeasyarch.api;

@FunctionalInterface
public interface HookAction {
    void handle(Service service);
}
