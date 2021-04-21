package br.gov.pbh.prodabel.hubsmsa.exception.handler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.ServicoException;

/**
 * Classe responsável por capturar e manipular os erros lançados através da {@link NegocioException}
 *
 * @author diogo.matos
 */
@Provider
public class ServicoExceptionHandler implements ExceptionMapper<ServicoException> {

    @Override
  public Response toResponse(ServicoException e) {
       return Response
        		.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(e.getMessages()).build();
    }
}