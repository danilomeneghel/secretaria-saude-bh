package br.gov.pbh.prodabel.hubsmsa.endpoint;

import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CADASTRAR_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CONSULTAR_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CONSULTAR_EMPRESA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.CONSULTAR_TIPO_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.EDITAR_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.security.ConstantesPermissoes.EXCLUIR_DE_PARA;
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
import br.gov.pbh.prodabel.hubsmsa.dto.depara.VisualizarDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.cadastro.CadastrarDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaLogDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.PesquisaDeParaDTO;
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
@Path("/de-para")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "De/Para", description = "Recurso para consultas e cadastros de De/Para")
public interface DeParaEndPoint {

  /**
   * Consultar de para.
   *
   * @param cadastrarDePara the cadastrar de para
   * @return the paginacao publica DTO
   */

  /**
   * Consultar de para.
   *
   * @param cadastrarDePara the cadastrar de para
   * @return the paginacao publica DTO
   */
  @POST
  @Path("/pesquisar")
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed({CONSULTAR_DE_PARA})
  @Operation(description = "Listar os De/Para cadastrados no sistema", summary = "Listar De/Para",
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  PaginacaoPublicaDTO<PesquisaDeParaDTO> consultarDePara(FiltroPesquisaDeParaDTO cadastrarDePara);

  /**
   * Visualizar de para.
   *
   * @param id the id
   * @return the visualizar de para DTO
   */

  /**
   * Visualizar de para.
   *
   * @param id the id
   * @return the visualizar de para DTO
   */
  @GET
  @Path("visualizar/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed({CONSULTAR_DE_PARA})
  @Operation(description = "Recurso para recuperar um De/Para por id",
      summary = "Recurso para recuperar um De/Para por id",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = VisualizarDeParaDTO.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  public VisualizarDeParaDTO visualizarDePara(@PathParam(value = "id") Long id);

  /**
   * Selecionar de para.
   *
   * @param id the id
   * @return the cadastrar de para DTO
   */

  /**
   * Selecionar de para.
   *
   * @param id the id
   * @return the cadastrar de para DTO
   */
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed({CONSULTAR_DE_PARA})
  @Operation(description = "Recurso para recuperar um De/Para por id",
      summary = "Recurso para recuperar um De/Para por id",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = CadastrarDeParaDTO.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  public CadastrarDeParaDTO selecionarDePara(@PathParam(value = "id") Long id);

  /**
   * Cadastrar de para.
   *
   * @param cadastrarDePara the cadastrar de para
   * @return the response DTO
   */

  /**
   * Cadastrar de para.
   *
   * @param cadastrarDePara the cadastrar de para
   * @return the response DTO
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed(CADASTRAR_DE_PARA)
  @Operation(description = "Recurso para cadastro de um novo De/Para",
      summary = "Recurso para cadastro de um novo De/Para",
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  ResponseDTO<EntityDTO> cadastrarDePara(@RequestBody(
      description = "Objeto De/Para que será adicionado", required = true,
      content = @Content(schema = @Schema(
          implementation = CadastrarDeParaDTO.class))) @Valid CadastrarDeParaDTO cadastrarDePara);

  /**
   * Editar de para.
   *
   * @param id the id
   * @param editarDePara the editar de para
   * @return the response DTO
   */

  /**
   * Editar de para.
   *
   * @param id the id
   * @param editarDePara the editar de para
   * @return the response DTO
   */
  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed(EDITAR_DE_PARA)
  @Operation(description = "Recurso para alterar De/Para por id",
      summary = "Recurso para alterar De/Para por id",
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  ResponseDTO<EntityDTO> editarDePara(
      @Parameter(description = "Id do De/Para que será editado",
          required = true) @PathParam("id") Long id,
      @RequestBody(description = "Objeto De/Para para edição", required = true,
          content = @Content(schema = @Schema(
              implementation = CadastrarDeParaDTO.class))) @Valid CadastrarDeParaDTO editarDePara);

  /**
   * Excluir de para.
   *
   * @param id the id
   * @return the response DTO
   */

  /**
   * Excluir de para.
   *
   * @param id the id
   * @return the response DTO
   */
  @DELETE
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed(EXCLUIR_DE_PARA)
  @Operation(description = "Recurso para excluir um De/Para por id",
      summary = "Recurso para excluir um De/Para por id",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = EntityDTO.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  public ResponseDTO<EntityDTO> excluirDePara(@PathParam(value = "id") Long id);

  /**
   * Gerar csv.
   *
   * @param cadastrarDePara the cadastrar de para
   * @return the response
   */

  /**
   * Gerar csv.
   *
   * @param cadastrarDePara the cadastrar de para
   * @return the response
   */
  @POST
  @Path("/csv")
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(description = "Listar os De/Para cadastrados no sistema", summary = "Listar De/Para",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/vnd.ms-excel",
                  schema = @Schema(implementation = Response.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  Response gerarCsv(FiltroPesquisaDeParaDTO cadastrarDePara);

  /**
   * Gerar excel.
   *
   * @param cadastrarDePara the cadastrar de para
   * @return the response
   */

  /**
   * Gerar excel.
   *
   * @param cadastrarDePara the cadastrar de para
   * @return the response
   */
  @POST
  @Path("/excel")
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(description = "Listar os De/Para cadastrados no sistema", summary = "Listar De/Para",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/vnd.ms-excel",
                  schema = @Schema(implementation = Response.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  Response gerarExcel(FiltroPesquisaDeParaDTO cadastrarDePara);

  /**
   * Consultar log tipo de para.
   *
   * @param revisaoDTO the revisao DTO
   * @return the paginacao publica DTO
   */

  /**
   * Consultar log tipo de para.
   *
   * @param revisaoDTO the revisao DTO
   * @return the paginacao publica DTO
   */
  @GET
  @Path("/log-de-para")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed({CONSULTAR_TIPO_DE_PARA})
  @Operation(description = "Recurso para buscar histórico de alterações de DePara",
      summary = "Recurso para buscar histórico de alterações de DePara",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = HistoricoAlteracaoDTO.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  public PaginacaoPublicaDTO<HistoricoAlteracaoDTO> consultarLogTipoDePara(
      @BeanParam @Valid FiltroPesquisaLogDeParaDTO revisaoDTO);

  /**
   * Consultar detalhe revisao.
   *
   * @param id the id
   * @return the historico alteracao detalhe DTO
   */

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
  @Operation(description = "Recurso para buscar histórico de alterações de De/Para",
      summary = "Recurso para buscar histórico de alterações de Contato de De/Para",
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
      summary = "Recurso para gerar log de alterações de DePara para csv",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/vnd.ms-excel",
                  schema = @Schema(implementation = Response.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  Response gerarLogCsv(@Parameter(
      description = "Filtro da exportação da consulta de DePara") @Valid @BeanParam FiltroPesquisaLogDeParaDTO filtro);


  /**
   * Gerar log excel.
   *
   * @param filtro the filtro
   * @return the response
   */
  @GET
  @Path("/excel/log")
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(description = "Recurso para gerar excel",
      summary = "Recurso para gerar log de alterações de DePara para excel",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/vnd.ms-excel",
                  schema = @Schema(implementation = Response.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  Response gerarLogExcel(@Parameter(
      description = "Filtro da exportação da consulta de DePara") @Valid @BeanParam FiltroPesquisaLogDeParaDTO filtro);

}
