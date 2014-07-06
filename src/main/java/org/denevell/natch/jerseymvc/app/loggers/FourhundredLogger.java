package org.denevell.natch.jerseymvc.app.loggers;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

@Provider
public class FourhundredLogger implements ExceptionMapper<BadRequestException> {

	@Override
	public Response toResponse(BadRequestException exception) {
		Logger.getLogger(getClass()).error("4xx exception", exception);
		return Response
			.status(exception.getResponse().getStatus())
			.build();
	}

}
