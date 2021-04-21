package br.gov.pbh.prodabel.hubsmsa.endpoint;

import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CADASTRAR_SISTEMA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CONSULTAR_EMPRESA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CONSULTAR_SISTEMA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.EDITAR_SISTEMA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.EXCLUIR_SISTEMA;
import java.util.List;
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
import javax.ws.rs.core.Response;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.FiltroPesquisaLogSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.FiltroPesquisaSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.SistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.VisualizarSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.security.JWTToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// TODO: Auto-generated Javadoc
@Path("/sistemas")
@JWTToken
@Tag(name = "Sistemas", description = "Recurso para consultas e cadastros de Sistemas")
public interface SistemaEndPoint {

	/**
	 * Consultar sistema.
	 *
	 * @param pesquisarSistema the pesquisar sistema
	 * @return the paginacao publica DTO
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ CONSULTAR_SISTEMA })
	@Operation(description = "Listar os Exemplos cadastrados no sistema", summary = "Listar Sistemas", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	PaginacaoPublicaDTO<VisualizarSistemaDTO> consultarSistema(
			@Parameter(description = "Objeto Exemplo que será retornado") @Valid @BeanParam FiltroPesquisaSistemaDTO pesquisarSistema);


    /**
     * Consultar sistema.
     *
     * @return the list
     */
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({CONSULTAR_SISTEMA})
    @Operation(description = "Listar os Exemplos cadastrados no sistema",
        summary = "Listar Sistemas",
        responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    List<VisualizarSistemaDTO> consultarSistema();


	/**
	 * Consultar sistema.
	 *
	 * @param id the id
	 * @return the visualizar sistema DTO
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ CONSULTAR_SISTEMA })
	@Operation(description = "Recurso para recuperar um sistema por id", summary = "Recurso para recuperar um sistema por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisualizarSistemaDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public VisualizarSistemaDTO consultarSistema(@PathParam(value = "id") Long id);

    /**
     * Consultar sistema por empresa.
     *
     * @param idEmpresa the id empresa
     * @return the visualizar sistema DTO
     */
    @GET
    @Path("{idEmpresa}/empresa")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({CONSULTAR_SISTEMA})
    @Operation(description = "Recurso para recuperar os sistemas por id da empresa",
        summary = "Recurso para recuperar os sistema por id da empresa",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VisualizarSistemaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public List<VisualizarSistemaDTO> consultarSistemaPorEmpresa(
        @PathParam(value = "idEmpresa") Long idEmpresa);

	/**
	 * Cadastrar sistema.
	 *
	 * @param cadastrarSistema the cadastrar sistema
	 * @return the response DTO
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(CADASTRAR_SISTEMA)
	@Operation(description = "Recurso para cadastro de um novo sistema", summary = "Recurso para cadastro de um novo sistema", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	ResponseDTO<EntityDTO> cadastrarSistema(
			@RequestBody(description = "Objeto Sistema que será adicionado", required = true, content = @Content(schema = @Schema(implementation = SistemaDTO.class))) @Valid SistemaDTO cadastrarSistema);

	/**
	 * Editar sistema.
	 *
	 * @param id the id
	 * @param editarSistema the editar sistema
	 * @return the response DTO
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(EDITAR_SISTEMA)
	@Operation(description = "Recurso para alterar Sistema por id", summary = "Recurso para alterar Sistema por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	ResponseDTO<EntityDTO> editarSistema(
			@Parameter(description = "Id do Exemplo que será editado", required = true) @PathParam("id") Long id,
			@RequestBody(description = "Objeto Exemplo para edição", required = true, content = @Content(schema = @Schema(implementation = SistemaDTO.class))) @Valid SistemaDTO editarSistema);

	/**
	 * Excluir sistema.
	 *
	 * @param id the id
	 * @return the response DTO
	 */
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(EXCLUIR_SISTEMA)
	@Operation(description = "Recurso para excluir um sistema por id", summary = "Recurso para excluir um sistema por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EntityDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public ResponseDTO<EntityDTO> excluirSistema(@PathParam(value = "id") Long id);

	/**
	 * Consultar selecao.
	 *
	 * @return the list
	 */
	@GET
	@Path("selecao")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(CONSULTAR_SISTEMA)
	@Operation(description = "Recurso para recuperar Sistema para Selecao", summary = "Recurso para recuperar Sistema para Selecao", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisualizarSistemaDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public List<SelecaoDTO> consultarSelecao();

	/**
	 * Gerar csv.
	 *
	 * @param pesquisarSistema the pesquisar sistema
	 * @return the response
	 */
	@GET
	@Path("/csv")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed(CONSULTAR_SISTEMA)
	@Operation(description = "Recurso para gerar csv", summary = "Recurso para gerar csv", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/vnd.ms-excel", schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public Response gerarCsv(
			@Parameter(description = "Filtro da exportação da consulta de Sistemas das Empresas") @Valid @BeanParam FiltroPesquisaSistemaDTO pesquisarSistema);
	
	/**
	 * Gerar excel.
	 *
	 * @param pesquisarSistema the pesquisar sistema
	 * @return the response
	 */
	@GET
	@Path("/excel")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed(CONSULTAR_SISTEMA)
	@Operation(description = "Recurso para gerar Excel", summary = "Recurso para gerar Excel", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/vnd.ms-excel", schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public Response gerarExcel(
			@Parameter(description = "Filtro da exportação da consulta de Sistemas das Empresas") @Valid @BeanParam FiltroPesquisaSistemaDTO pesquisarSistema);
	
	/**
	 * Consultar historico revisoes.
	 *
	 * @param revisaoDTO the revisao DTO
	 * @return the paginacao publica DTO
	 */
	@GET
	@Path("/historico-revisoes")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({CONSULTAR_SISTEMA})
	@Operation(description = "Recurso para buscar histórico de alterações dos Sistemas das Empresas", summary = "Recurso para buscar histórico de alterações dos Sistemas das Empresas", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistoricoAlteracaoDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(@BeanParam @Valid FiltroPesquisaLogSistemaDTO revisaoDTO);

	/**
	 * Consultar detalhe revisao.
	 *
	 * @param id the id
	 * @return the historico alteracao detalhe DTO
	 */
	@GET
	@Path("/historico-revisoes/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({CONSULTAR_EMPRESA})
	@Operation(description = "Recurso para buscar histórico de alterações de Empresa", summary = "Recurso para buscar histórico de alterações de Empresa", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistoricoAlteracaoDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public HistoricoAlteracaoDetalheDTO consultarDetalheRevisao(@PathParam(value = "id") Long id);
	
    /**
     * Gerar log csv.
     *
     * @param pesquisarTipoDePara the pesquisar tipo de para
     * @return the response
     */
    @GET
    @Path("/csv/log")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Recurso para gerar csv",
        summary = "Recurso para gerar log de alterações de sistema das empresas para csv",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/vnd.ms-excel",
                    schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    Response gerarLogCsv(@Parameter(
        description = "Filtro da exportação da consulta de sistema das empresas") @Valid @BeanParam FiltroPesquisaLogSistemaDTO filtro);

    /**
     * Gerar log csv.
     *
     * @param pesquisarEmpresa the pesquisar tipo de para
     * @return the response
     */
    @GET
    @Path("/excel/log")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Recurso para gerar excel",
        summary = "Recurso para gerar log de alterações de sistema das empresas para excel",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/vnd.ms-excel",
                    schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    Response gerarLogExcel(@Parameter(
        description = "Filtro da exportação da consulta de sistema das empresas") @Valid @BeanParam FiltroPesquisaLogSistemaDTO filtro);

}
