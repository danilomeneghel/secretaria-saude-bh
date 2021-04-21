package br.gov.pbh.prodabel.hubsmsa.endpoint;

import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CADASTRAR_TIPO_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CONSULTAR_EMPRESA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CONSULTAR_TIPO_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.EDITAR_TIPO_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.EXCLUIR_TIPO_DE_PARA;
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
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.FiltroPesquisaLogTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.FiltroPesquisaTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.TipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.VisualizarTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.security.JWTToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// TODO: Auto-generated Javadoc
@JWTToken
@Path("/tipos-de-para")
@Tag(name = "Tipos de De/Para", description = "Recurso para consultas e cadastros de Tipo De/Para")
public interface TipoDeParaEndPoint {

	/**
	 * Consultar tipo de para.
	 *
	 * @param pesquisarTipoDePara the pesquisar tipo de para
	 * @return the paginacao publica DTO
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ CONSULTAR_TIPO_DE_PARA })
	@Operation(description = "Listar os Tipos de De/Para cadastrados no sistema", summary = "Listar Tipos de De/Para", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	PaginacaoPublicaDTO<VisualizarTipoDeParaDTO> consultarTipoDePara(
			@Parameter(description = "Objeto TipoDePara que será retornado") @Valid @BeanParam FiltroPesquisaTipoDeParaDTO pesquisarTipoDePara);

	/**
	 * Cadastrar tipo de para.
	 *
	 * @param tipoDeParaDTO the tipo de para DTO
	 * @return the response DTO
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(CADASTRAR_TIPO_DE_PARA)
	@Operation(description = "Recurso para cadastro de um novo TipoDePara", summary = "Recurso para cadastro de um novo TipoDePara", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	ResponseDTO<EntityDTO> cadastrarTipoDePara(
			@RequestBody(description = "Objeto TipoDePara que será adicionado", required = true, content = @Content(schema = @Schema(implementation = TipoDeParaDTO.class))) @Valid TipoDeParaDTO tipoDeParaDTO);

	/**
	 * Editar tipo de para.
	 *
	 * @param id the id
	 * @param editarTipoDeParaDTO the editar tipo de para DTO
	 * @return the response DTO
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(EDITAR_TIPO_DE_PARA)
	@Operation(description = "Recurso para alterar TipoDePara por id", summary = "Recurso para alterar TipoDePara por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	ResponseDTO<EntityDTO> editarTipoDePara(
			@Parameter(description = "Id do TipoDePara que será editado", required = true) @PathParam("id") Long id,
			@RequestBody(description = "Objeto TipoDePara para edição", required = true, content = @Content(schema = @Schema(implementation = TipoDeParaDTO.class))) @Valid TipoDeParaDTO editarTipoDeParaDTO);

	/**
	 * Consultar tipo de para.
	 *
	 * @param id the id
	 * @return the visualizar tipo de para DTO
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(CONSULTAR_TIPO_DE_PARA)
	@Operation(description = "Recurso para recuperar TipoDePara por id", summary = "Recurso para recuperar TipoDePara por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisualizarTipoDeParaDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public VisualizarTipoDeParaDTO consultarTipoDePara(@PathParam(value = "id") Long id);

	/**
	 * Excluir tipo de para.
	 *
	 * @param id the id
	 * @return the response DTO
	 */
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(EXCLUIR_TIPO_DE_PARA)
	@Operation(description = "Recurso para excluir um TipoDePara por id", summary = "Recurso para excluir um TipoDePara por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EntityDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public ResponseDTO<EntityDTO> excluirTipoDePara(@PathParam(value = "id") Long id);

	/**
	 * Consultar selecao.
	 *
	 * @return the list
	 */
	@GET
	@Path("selecao")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(CONSULTAR_TIPO_DE_PARA)
	@Operation(description = "Recurso para recuperar TipoDePara para Selecao", summary = "Recurso para recuperar TipoDePara para Selecao", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisualizarTipoDeParaDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public List<SelecaoDTO> consultarSelecao();

	/**
	 * Gerar csv.
	 *
	 * @param pesquisarTipoDePara the pesquisar tipo de para
	 * @return the response
	 */
	@GET
	@Path("/csv")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed(CONSULTAR_TIPO_DE_PARA)
	@Operation(description = "Recurso para gerar csv", summary = "Recurso para gerar csv", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/vnd.ms-excel", schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public Response gerarCsv(
			@Parameter(description = "Filtro da exportação da consulta de TipoDePara") @Valid @BeanParam FiltroPesquisaTipoDeParaDTO pesquisarTipoDePara);
	
	/**
	 * Gerar excel.
	 *
	 * @param pesquisarTipoDePara the pesquisar tipo de para
	 * @return the response
	 */
	@GET
	@Path("/excel")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed(CONSULTAR_TIPO_DE_PARA)
	@Operation(description = "Recurso para gerar Excel", summary = "Recurso para gerar Excel", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/vnd.ms-excel", schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public Response gerarExcel(
			@Parameter(description = "Filtro da exportação da consulta de TipoDePara") @Valid @BeanParam FiltroPesquisaTipoDeParaDTO pesquisarTipoDePara);
	
	/**
	 * Consultar log tipo de para.
	 *
	 * @param revisaoDTO the revisao DTO
	 * @return the paginacao publica DTO
	 */
	@GET
	@Path("/log-tipos-de-para")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({CONSULTAR_TIPO_DE_PARA})
    @Operation(description = "Recurso para buscar histórico de alterações de TipoDePara",
        summary = "Recurso para buscar histórico de alterações de TipoDePara", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistoricoAlteracaoDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
    public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarLogTipoDePara(
        @BeanParam @Valid FiltroPesquisaLogTipoDeParaDTO revisaoDTO);

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
    @Operation(description = "Recurso para buscar histórico de alterações de TipoDePara",
        summary = "Recurso para buscar histórico de alterações de TipoDePara",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = HistoricoAlteracaoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
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
        summary = "Recurso para gerar log de alterações de Tipo De Para para csv",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/vnd.ms-excel",
                    schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    Response gerarLogCsv(@Parameter(
        description = "Filtro da exportação da consulta de tipo de para") @Valid @BeanParam FiltroPesquisaLogTipoDeParaDTO filtro);

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
        summary = "Recurso para gerar log de alterações de Tipo de Para para excel",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/vnd.ms-excel",
                    schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    Response gerarLogExcel(@Parameter(
        description = "Filtro da exportação da consulta de tipo de para") @Valid @BeanParam FiltroPesquisaLogTipoDeParaDTO filtro);
}
