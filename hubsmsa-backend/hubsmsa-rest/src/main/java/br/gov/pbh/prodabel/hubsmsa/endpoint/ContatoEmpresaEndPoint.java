package br.gov.pbh.prodabel.hubsmsa.endpoint;

import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CADASTRAR_CONTATO_EMPRESA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CONSULTAR_CONTATO_EMPRESA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CONSULTAR_EMPRESA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.EDITAR_CONTATO_EMPRESA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.EXCLUIR_CONTATO_EMPRESA;
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
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.ContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.FiltroPesquisaContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.FiltroPesquisaLogContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.VisualizarContatoEmpresaDTO;
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
@Path("/contato-empresas")
@JWTToken
@Tag(name = "ContatoEmpresas", description = "Recurso para consultas e cadastros de Empresas")
public interface ContatoEmpresaEndPoint {

	/**
	 * Consultar contato empresa.
	 *
	 * @param pesquisarContatoEmpresa the pesquisar contato empresa
	 * @return the paginacao publica DTO
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ CONSULTAR_CONTATO_EMPRESA })
	@Operation(description = "Listar os Exemplos cadastrados no contatoEmpresa", summary = "Listar empresas", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	PaginacaoPublicaDTO<VisualizarContatoEmpresaDTO> consultarContatoEmpresa(
			@Parameter(description = "Objeto Exemplo que será retornado") @Valid @BeanParam FiltroPesquisaContatoEmpresaDTO pesquisarContatoEmpresa);

	/**
	 * Consultar contato empresa.
	 *
	 * @param id the id
	 * @return the visualizar contato empresa DTO
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ CONSULTAR_CONTATO_EMPRESA })
	@Operation(description = "Recurso para recuperar um contatoEmpresa por id", summary = "Recurso para recuperar um contatoEmpresa por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VisualizarContatoEmpresaDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public VisualizarContatoEmpresaDTO consultarContatoEmpresa(@PathParam(value = "id") Long id);

	/**
	 * Cadastrar contato empresa.
	 *
	 * @param cadastrarContatoEmpresa the cadastrar contato empresa
	 * @return the response DTO
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(CADASTRAR_CONTATO_EMPRESA)
	@Operation(description = "Recurso para cadastro de um novo contatoEmpresa", summary = "Recurso para cadastro de um novo contatoEmpresa", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	ResponseDTO<EntityDTO> cadastrarContatoEmpresa(
			@RequestBody(description = "Objeto ContatoEmpresa que será adicionado", required = true, content = @Content(schema = @Schema(implementation = ContatoEmpresaDTO.class))) @Valid ContatoEmpresaDTO cadastrarContatoEmpresa);

	/**
	 * Editar contato empresa.
	 *
	 * @param id the id
	 * @param editarContatoEmpresa the editar contato empresa
	 * @return the response DTO
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(EDITAR_CONTATO_EMPRESA)
	@Operation(description = "Recurso para alterar empresa por id", summary = "Recurso para alterar empresa por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	ResponseDTO<EntityDTO> editarContatoEmpresa(
			@Parameter(description = "Id do Exemplo que será editado", required = true) @PathParam("id") Long id,
			@RequestBody(description = "Objeto Exemplo para edição", required = true, content = @Content(schema = @Schema(implementation = ContatoEmpresaDTO.class))) @Valid ContatoEmpresaDTO editarContatoEmpresa);

	/**
	 * Excluir contato empresa.
	 *
	 * @param id the id
	 * @return the response DTO
	 */
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(EXCLUIR_CONTATO_EMPRESA)
	@Operation(description = "Recurso para excluir um contatoEmpresa por id", summary = "Recurso para excluir um contatoEmpresa por id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EntityDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public ResponseDTO<EntityDTO> excluirContatoEmpresa(@PathParam(value = "id") Long id);
	
	/**
     * Consultar contatos da empresa.
     *
     * @param idEmpresa the id empresa
     * @return the list
     */
    @GET
    @Path("{id}/contatos")
    @RolesAllowed({CONSULTAR_CONTATO_EMPRESA})
    @Operation(description = "Listar os contatos da empresa para selecao",
        summary = "Listar Contatos para selecao",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SelecaoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    List<SelecaoDTO> consultarContatosDaEmpresa(
        @Parameter(description = "Id da Empresa para listar os contatos",
            required = true) @PathParam("id") Long idEmpresa);

    /**
     * Gerar excel.
     *
     * @param pesquisarContatoEmpresa the pesquisar contato empresa
     * @return the response
     */
	@GET
	@Path("/excel")
	@RolesAllowed({ CONSULTAR_CONTATO_EMPRESA })
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(description = "Recurso para gerar Excel", summary = "Recurso para gerar Excel", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/vnd.ms-excel", schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	Response gerarExcel(
			@Parameter(description = "Filtro da exportação da consulta de contatoEmpresa") @Valid @BeanParam FiltroPesquisaContatoEmpresaDTO pesquisarContatoEmpresa);

	/**
	 * Gerar csv.
	 *
	 * @param pesquisarContatoEmpresa the pesquisar contato empresa
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
			@Parameter(description = "Filtro da exportação da consulta de contatoEmpresa") @Valid @BeanParam FiltroPesquisaContatoEmpresaDTO pesquisarContatoEmpresa);
	
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
	@Operation(description = "Recurso para buscar histórico de alterações de Contado de  Empresa", summary = "Recurso para buscar histórico de alterações Contato de Empresa", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistoricoAlteracaoDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarHistoricoRevisoes(@BeanParam @Valid FiltroPesquisaLogContatoEmpresaDTO revisaoDTO);

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
    @Operation(description = "Recurso para buscar histórico de alterações de Contato de Empresa",
        summary = "Recurso para buscar histórico de alterações de Contato de Empresa",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = HistoricoAlteracaoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    public HistoricoAlteracaoDetalheDTO consultarDetalheRevisao(@PathParam(value = "id") Long id);

  /**
   * Gerar log historico csv.
   *
   * @param filtro the filtro
   * @return the response
   */
  @GET
  @Path("/csv/log")
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(description = "Recurso para gerar csv",
      summary = "Recurso para gerar log de alterações de Contatos de Empresa para csv",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/vnd.ms-excel",
                  schema = @Schema(implementation = Response.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  Response gerarLogCsv(@Parameter(
      description = "Filtro da exportação da consulta de contatos de empresas") @Valid @BeanParam FiltroPesquisaLogContatoEmpresaDTO filtro);

  /**
   * Gerar log exel.
   *
   * @param pesquisarEmpresa the pesquisar tipo de para
   * @return the response
   */
  @GET
  @Path("/excel/log")
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(description = "Recurso para gerar excel",
      summary = "Recurso para gerar log de alterações de Contatos para excel",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/vnd.ms-excel",
                  schema = @Schema(implementation = Response.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  Response gerarLogExcel(@Parameter(
      description = "Filtro da exportação da consulta de contatos") @Valid @BeanParam FiltroPesquisaLogContatoEmpresaDTO filtro);

}
