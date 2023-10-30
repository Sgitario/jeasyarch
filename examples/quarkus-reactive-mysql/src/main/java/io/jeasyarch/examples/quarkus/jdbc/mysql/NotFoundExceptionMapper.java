package io.jeasyarch.examples.quarkus.jdbc.mysql;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import jakarta.ws.rs.NotFoundException;

// workaround for Quarkus providing its own NotFoundExceptionMapper
// which is more specific than our ApplicationExceptionMapper
public class NotFoundExceptionMapper {

    @ServerExceptionMapper
    public RestResponse<String> mapException(NotFoundException exception) {
        return new ApplicationExceptionMapper().mapException(exception);
    }
}
