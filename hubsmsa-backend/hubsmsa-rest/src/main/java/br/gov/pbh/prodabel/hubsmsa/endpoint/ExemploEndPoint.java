package br.gov.pbh.prodabel.hubsmsa.endpoint;

import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.PESQUISAR_EXEMPLO;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.PESQUISAR_MANTER_EXEMPLO;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.FiltroRevisaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.CadastrarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.EditarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.FiltroPesquisaExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.VisualizarExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.security.JWTToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/exemplo")
@JWTToken
@Tag(name = "exemplo", description = "Recurso para consultas e cadastros de Exemplo")
public interface ExemploEndPoint {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({PESQUISAR_EXEMPLO, PESQUISAR_MANTER_EXEMPLO})
	@Operation(description = "Listar os Exemplos cadastrados no sistema", summary = "Listar Exemplos", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	PaginacaoPublicaDTO<VisualizarExemploDTO> consultarExemplo(@Parameter(description = "Objeto Exemplo que será retornado")
	@Valid	@BeanParam FiltroPesquisaExemploDTO pesquisarExemplo);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(PESQUISAR_MANTER_EXEMPLO)
	@Operation(description = "Recurso para cadastro de um nov Exemplo", summary = "Recurso para cadastro de um nov Exemplo", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	ResponseDTO<EntityDTO> cadastrarExemplo(
			@RequestBody(description = "Objeto Exemplo que será adicionado", required = true, content = @Content(schema = @Schema(implementation = CadastrarExemploDTO.class)))
			@Valid CadastrarExemploDTO exemploDTO);
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(PESQUISAR_MANTER_EXEMPLO)
	@Operation(description = "Recurso para alterar exemplo por id", summary = "Recurso para alterar exemplo por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	ResponseDTO<EntityDTO> editarExemplo(
			@Parameter(description = "Id do Exemplo que será editado", required = true)
			@PathParam("id")Long id,
			@RequestBody(description = "Objeto Exemplo para edição", required = true, content = @Content(schema = @Schema(implementation = EditarExemploDTO.class)))
			@Valid EditarExemploDTO editarExemploDTO);
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({PESQUISAR_EXEMPLO, PESQUISAR_MANTER_EXEMPLO})
	@Operation(description = "Recurso para recuperar Exemplo por id", summary = "Recurso para recuperar Exemplo por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisualizarExemploDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public VisualizarExemploDTO consultarExemplo(@PathParam(value = "id") Long id);
	
	@GET
	@Path("historico-revisoes/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({PESQUISAR_EXEMPLO, PESQUISAR_MANTER_EXEMPLO})
	@Operation(description = "Recurso para buscar histórico de alterações de Exemplo", summary = "Recurso para buscar histórico de alterações de Exemplo", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistoricoAlteracaoDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(@PathParam("id") Long id, @BeanParam @Valid FiltroRevisaoDTO revisaoDTO);
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(PESQUISAR_MANTER_EXEMPLO)
	@Operation(description = "Recurso para excluir um exemplo por id", summary = "Recurso para excluir um exemplo por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EntityDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public ResponseDTO<EntityDTO> excluirExemplo(@PathParam(value = "id") Long id);
}
