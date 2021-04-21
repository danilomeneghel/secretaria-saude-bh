package br.gov.pbh.prodabel.hubsmsa.endpoint;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import br.gov.pbh.prodabel.hubsmsa.dto.EchoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Endpoint apenas com o intuito de testes da aplicação
 *  
 * @author alysson.cordeiro@ctis.com.br
 *
 */
@Path("api-tests")
@Tag(name = "Echo", description = "Recursos para testes da aplicação")
public interface EchoEndPoint {

	@GET
	@Produces("text/plain")
	@Operation(description = "Recurso para testar a aplicação", summary = "Recurso para testar a aplicação", 
			   responses = {
					   @ApiResponse(responseCode = "200", content = @Content(mediaType = "text/plain")),
					   @ApiResponse(responseCode = "400", description = "Bad Request"),
					   @ApiResponse(responseCode = "404", description = "Not found"),
					   @ApiResponse(responseCode = "500", description = "Internal Server Error") 
			    }
			  )
	Response echo(@Parameter(description = "Mensagem que será exibida no retorno", required = true) @QueryParam("message") @NotNull String message);
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Operation(description = "Recurso para simular o cadastro de um novo recurso Echo", summary = "Adicionar novo Echo", 
	   responses = {
			   @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			   @ApiResponse(responseCode = "400", description = "Bad Request"),
			   @ApiResponse(responseCode = "404", description = "Not found"),
			   @ApiResponse(responseCode = "500", description = "Internal Server Error") 
	    }
	  )
	Response add(@RequestBody(description = "Objeto Echo que será adicionado", required = true, content = @Content(schema = @Schema(implementation = EchoDTO.class))) @Valid EchoDTO echoDTO);
	
	
	@PUT
	@Path("{codigo}")
	@Consumes("application/json")
	@Produces("application/json")
	@Operation(description = "Recurso para simular a atualização de um recurso Echo já cadastrado", summary = "Editar recurso Echo", 
	   responses = {
			   @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			   @ApiResponse(responseCode = "400", description = "Bad Request"),
			   @ApiResponse(responseCode = "404", description = "Not found"),
			   @ApiResponse(responseCode = "500", description = "Internal Server Error") 
	    }
	  )
	Response update(@Parameter(description = "Código do Echo que será editado", required = true) @PathParam("codigo") @NotNull Long codigo,
					@RequestBody(description = "Objeto Echo para edição", required = true, content = @Content(schema = @Schema(implementation = EchoDTO.class))) @Valid EchoDTO echoDTO);
	
	
	@DELETE
	@Path("{codigo}")
	@Operation(description = "Excluir um Echo pelo código", summary = "Excluir recurso Echo", 
	   responses = {
			   @ApiResponse(responseCode = "200", content = @Content(mediaType = "text/plain")),
			   @ApiResponse(responseCode = "400", description = "Bad Request"),
			   @ApiResponse(responseCode = "404", description = "Not found"),
			   @ApiResponse(responseCode = "500", description = "Internal Server Error") 
	    }
	  )
	Response delete(@Parameter(description = "Código do Echo que será removido", required = true) @PathParam("codigo") @NotNull Long codigo);
	
}
