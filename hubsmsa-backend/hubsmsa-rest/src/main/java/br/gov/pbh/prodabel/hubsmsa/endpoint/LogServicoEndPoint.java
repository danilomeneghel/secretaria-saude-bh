package br.gov.pbh.prodabel.hubsmsa.endpoint;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.logservico.FiltroPesquisaLogServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.logservico.VisualizarLogServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.security.JWTToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// TODO: Auto-generated Javadoc
@Path("/log-servicos")
@JWTToken
@Tag(name = "LogServicos", description = "Recurso para consultas de log de serviços")
public interface LogServicoEndPoint {


	/**
	 * Consultar log servico.
	 *
	 * @param pesquisarLogServico the pesquisar log servico
	 * @return the paginacao publica DTO
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Listar Logs de serviços de acordo com os filtros",
        summary = "Listar log de serviços", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
    PaginacaoPublicaDTO<VisualizarLogServicoDTO> consultarLogServico(@Parameter(
        description = "Objeto serviço que será retornado") @Valid @BeanParam FiltroPesquisaLogServicoDTO filtro);
	

}
