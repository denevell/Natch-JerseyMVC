package org.denevell.natch.jerseymvc.loggers;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

@Provider
public class FivehundredLogger implements ExceptionMapper<ServerErrorException> {

	@Override
	public Response toResponse(ServerErrorException exception) {
		Logger.getLogger(getClass()).error("5xx exception", exception);
		return Response
			.serverError()
			.build();
	}

}
