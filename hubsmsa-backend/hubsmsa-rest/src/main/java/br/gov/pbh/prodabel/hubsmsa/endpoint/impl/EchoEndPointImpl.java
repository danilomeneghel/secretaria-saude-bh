package br.gov.pbh.prodabel.hubsmsa.endpoint.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import br.gov.pbh.prodabel.hubsmsa.dto.EchoDTO;
import br.gov.pbh.prodabel.hubsmsa.endpoint.EchoEndPoint;

public class EchoEndPointImpl implements EchoEndPoint {

	@Override
	public Response echo(String message) {
		return Response.status(Status.OK).entity(message).build();
	}

	@Override
	public Response add(EchoDTO echoDTO) {
		return Response.status(Status.CREATED).entity(echoDTO).build();
	}

	@Override
	public Response update(Long codigo, EchoDTO echoDTO) {
		return Response.status(Status.OK).entity(echoDTO).build();
	}

	@Override
	public Response delete(Long codigo) {
		return Response.status(Status.OK).entity("Echo com código " + codigo + " removido com sucesso!").build();
	}

}
