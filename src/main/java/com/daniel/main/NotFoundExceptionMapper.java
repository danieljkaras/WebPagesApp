package com.daniel.main;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        return Response.status(Response.Status.NOT_FOUND).entity("Resource could not be found. !!! ").build();
    }
}
