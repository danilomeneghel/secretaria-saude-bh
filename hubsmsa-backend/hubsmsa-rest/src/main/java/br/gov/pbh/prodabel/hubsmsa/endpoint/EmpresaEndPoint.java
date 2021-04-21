package br.gov.pbh.prodabel.hubsmsa.endpoint;

import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CADASTRAR_EMPRESA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CONSULTAR_EMPRESA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.EDITAR_EMPRESA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.EXCLUIR_EMPRESA;
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
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.EmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.FiltroPesquisaEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.FiltroPesquisaLogEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.VisualizarEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
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
@Path("/empresa")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "empresas", description = "Recurso para consultas e cadastros da tabela Empresa")
public interface EmpresaEndPoint {

	/**
	 * Consultar empresa.
	 *
	 * @param pesquisarEmpresa the pesquisar empresa
	 * @return the paginacao publica DTO
	 */
	@GET
	@RolesAllowed({ CONSULTAR_EMPRESA })
	@Operation(description = "Listar as Empresa cadastradas no sistema", summary = "Listar Empresas", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	PaginacaoPublicaDTO<VisualizarEmpresaDTO> consultarEmpresa(
			@Parameter(description = "Objeto Empresa que será retornado") @Valid @BeanParam FiltroPesquisaEmpresaDTO pesquisarEmpresa);
	
	/**
	 * Consultar empresa droplist.
	 *
	 * @param pesquisarEmpresa the pesquisar empresa
	 * @return the paginacao publica DTO
	 */
	@GET
	@Path("/droplist")
	@RolesAllowed({ CONSULTAR_EMPRESA })
	@Operation(description = "Listar as Empresa cadastradas no sistema", summary = "Listar Empresas", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	PaginacaoPublicaDTO<VisualizarEmpresaDTO> consultarEmpresaDroplist(
			@Parameter(description = "Objeto Empresa que será retornado") @Valid @BeanParam FiltroPesquisaEmpresaDTO pesquisarEmpresa);

	/**
	 * Selecao empresa.
	 *
	 * @return the list
	 */
	@GET
	@Path("/selecao")
	@RolesAllowed({ CONSULTAR_EMPRESA })
	@Operation(description = "Listar as Empresa cadastradas no sistema para selecao", summary = "Listar Empresas para selecao", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SelecaoDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	List<SelecaoDTO> selecaoEmpresa();

	/**
	 * Consultar sistemas da empresa.
	 *
	 * @param idEmpresa the id empresa
	 * @return the list
	 */
	@GET
	@Path("{id}/sistemas")
	@RolesAllowed({ CONSULTAR_EMPRESA })
	@Operation(description = "Listar os sistemas da empresa para selecao", summary = "Listar Sistemas para selecao", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SelecaoDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	List<SelecaoDTO> consultarSistemasDaEmpresa(
			@Parameter(description = "Id da Empresa para listar os sistemas", required = true) @PathParam("id") Long idEmpresa);

	/**
	 * Cadastrar empresa.
	 *
	 * @param empresaDTO the empresa DTO
	 * @return the response DTO
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ CADASTRAR_EMPRESA })
	@Operation(description = "Recurso para cadastro de um nov Empresa", summary = "Recurso para cadastro de um nov Empresa", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	ResponseDTO<EntityDTO> cadastrarEmpresa(
			@RequestBody(description = "Objeto Empresa que será adicionado", required = true, content = @Content(schema = @Schema(implementation = EmpresaDTO.class))) @Valid EmpresaDTO empresaDTO);

	/**
	 * Editar empresa.
	 *
	 * @param id the id
	 * @param editarEmpresaDTO the editar empresa DTO
	 * @return the response DTO
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ EDITAR_EMPRESA })
	@Operation(description = "Recurso para alterar empresa por id", summary = "Recurso para alterar empresa por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	ResponseDTO<EntityDTO> editarEmpresa(
			@Parameter(description = "Id do Empresa que será editado", required = true) @PathParam("id") Long id,
			@RequestBody(description = "Objeto Empresa para edição", required = true, content = @Content(schema = @Schema(implementation = EmpresaDTO.class))) @Valid EmpresaDTO editarEmpresaDTO);

	/**
	 * Consultar empresa.
	 *
	 * @param id the id
	 * @return the visualizar empresa DTO
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ CONSULTAR_EMPRESA })
	@Operation(description = "Recurso para recuperar Empresa por id", summary = "Recurso para recuperar Empresa por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisualizarEmpresaDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public VisualizarEmpresaDTO consultarEmpresa(@PathParam(value = "id") Long id);

	/**
	 * Excluir empresa.
	 *
	 * @param id the id
	 * @return the response DTO
	 */
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ EXCLUIR_EMPRESA })
	@Operation(description = "Recurso para excluir um empresa por id", summary = "Recurso para excluir um empresa por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EntityDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public ResponseDTO<EntityDTO> excluirEmpresa(@PathParam(value = "id") Long id);

	/**
	 * Gerar excel.
	 *
	 * @param pesquisarEmpresa the pesquisar empresa
	 * @return the response
	 */
	@GET
	@Path("/excel")
	@RolesAllowed({ CONSULTAR_EMPRESA })
	@Operation(description = "Recurso para gerar Excel", summary = "Recurso para gerar Excel", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/vnd.ms-excel", schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	Response gerarExcel(
			@Parameter(description = "Filtro da exportação da consulta de empresas") @Valid @BeanParam FiltroPesquisaEmpresaDTO pesquisarEmpresa);

	/**
	 * Gerar csv.
	 *
	 * @param pesquisarEmpresa the pesquisar empresa
	 * @return the response
	 */
	@GET
	@Path("/csv")
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(description = "Recurso para gerar csv", summary = "Recurso para gerar csv", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/vnd.ms-excel", schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	Response gerarCsv(
			@Parameter(description = "Filtro da exportação da consulta de empresas") @Valid @BeanParam FiltroPesquisaEmpresaDTO pesquisarEmpresa);
	
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
	@RolesAllowed({CONSULTAR_EMPRESA})
	@Operation(description = "Recurso para buscar histórico de alterações de Empresa", summary = "Recurso para buscar histórico de alterações de Empresa", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistoricoAlteracaoDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(@BeanParam @Valid FiltroPesquisaLogEmpresaDTO revisaoDTO);
	
	
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
     * Gerar historico csv.
     *
     * @param pesquisarEmpresa the pesquisar empresa
     * @return the response
     */
    @GET
    @Path("/csv/log")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Recurso para gerar csv",
        summary = "Recurso para gerar histórico de alterações de Empresa para csv",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/vnd.ms-excel",
                    schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    Response gerarHistoricoCsv(@Parameter(
        description = "Filtro da exportação da consulta de empresas") @Valid @BeanParam FiltroPesquisaLogEmpresaDTO pesquisarEmpresa);

    /**
     * Gerar historico csv.
     *
     * @param pesquisarEmpresa the pesquisar empresa
     * @return the response
     */
    @GET
    @Path("/excel/log")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Recurso para gerar excel",
        summary = "Recurso para gerar histórico de alterações de Empresa para excel",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/vnd.ms-excel",
                    schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    Response gerarHistoricoExcel(@Parameter(
        description = "Filtro da exportação da consulta de empresas") @Valid @BeanParam FiltroPesquisaLogEmpresaDTO pesquisarEmpresa);
}