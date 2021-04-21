package br.gov.pbh.prodabel.hubsmsa.endpoint;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.servico.VisualizarServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.security.JWTToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// TODO: Auto-generated Javadoc
@Path("/servicos")
@JWTToken
@Tag(name = "Servicos", description = "Recurso para consultas e servicos de Sistemas")
public interface ServicoEndPoint {
  
    /**
     * Consultar servico.
     *
     * @return the list
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Listar os Servicos cadastrados no sistema",
        summary = "Listar Servicos",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    List<VisualizarServicoDTO> consultarServico();


	/**
	 * Consultar servico.
	 *
	 * @param idSistema the id sistema
	 * @return the list
	 */
	@GET
    @Path("{id}/sistema")
	@Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Listar os Servicos cadastrados no sistema por sistema",
        summary = "Listar Servicos por sistema", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
    List<VisualizarServicoDTO> consultarServicoPorSistema(@PathParam(value = "id") Long idSistema);


    /**
     * Consultar servico por id.
     *
     * @param id the id
     * @return the visualizar servico DTO
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Recurso para recuperar um servico por id",
        summary = "Recurso para recuperar um servico por id",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VisualizarServicoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public VisualizarServicoDTO consultarServico(@PathParam(value = "id") Long id);


    /**
     * Selecao servico.
     *
     * @return the list
     */
    @GET
    @Path("{idSistema}/selecao")
    @Operation(description = "Listar os serviço cadastradas no sistema para selecao",
        summary = "Listar Serviços para selecao",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SelecaoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    List<SelecaoDTO> selecaoServico(@PathParam(value = "idSistema") Long idSistema);


	
}
