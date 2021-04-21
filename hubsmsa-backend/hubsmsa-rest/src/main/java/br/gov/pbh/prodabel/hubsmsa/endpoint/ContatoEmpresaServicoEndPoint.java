package br.gov.pbh.prodabel.hubsmsa.endpoint;

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
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.cadastro.CadastrarContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.pesquisa.FiltroPesquisaContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.pesquisa.VisualizarContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.security.JWTToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// TODO: Auto-generated Javadoc
@Path("/contato-empresa-servico")
@JWTToken
@Tag(name = "ContatoEmpresaServico",
    description = "Recurso para consultas e cadastros de Contatos Servicos")
public interface ContatoEmpresaServicoEndPoint {


  /**
   * Consultar contato servico.
   *
   * @param filtro the filtro
   * @return the paginacao publica DTO
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(description = "Listar os contatos dos serviços cadastrados no sistema",
      summary = "Listar Contatos Serviços",
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  PaginacaoPublicaDTO<VisualizarContatoEmpresaServicoDTO> consultarContatoServico(
      @Parameter(
          description = "Objeto Exemplo que será retornado") @Valid @BeanParam FiltroPesquisaContatoEmpresaServicoDTO filtro);

  /**
   * Consultar sistema.
   *
   * @param id the id
   * @return the visualizar contato empresa servico DTO
   */
  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(description = "Recurso para recuperar um contato empresa serviço por id",
      summary = "Recurso para recuperar um contato empresa serviço por id",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = VisualizarContatoEmpresaServicoDTO.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  public VisualizarContatoEmpresaServicoDTO consultarContatoEmpresaServico(
      @PathParam(value = "id") Long id);

  /**
   * Cadastrar sistema.
   *
   * @param cadastrarSistema the cadastrar sistema
   * @return the response DTO
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(description = "Recurso para cadastro de um novo contato empresa servico",
      summary = "Recurso para cadastro de um novo contato empresa servico",
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  ResponseDTO<EntityDTO> cadastrar(@RequestBody(
      description = "Objeto contato empresa serviço que será adicionado", required = true,
      content = @Content(
          schema = @Schema(
              implementation = CadastrarContatoEmpresaServicoDTO.class))) @Valid CadastrarContatoEmpresaServicoDTO cadastrarDTO);

  /**
   * Editar sistema.
   *
   * @param id the id
   * @param editarContatoEmpresaServico the editar contato empresa servico
   * @return the response DTO
   */
  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(description = "Recurso para alterar Contato Empresa Serviço por id",
      summary = "Recurso para alterar Contato Empresa Serviço por id",
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  ResponseDTO<EntityDTO> editar(
      @Parameter(description = "Id do Contato Empresa Serviço que será editado",
          required = true) @PathParam("id") Long id,
      @RequestBody(description = "Objeto Exemplo para edição", required = true,
          content = @Content(schema = @Schema(
              implementation = CadastrarContatoEmpresaServicoDTO.class))) @Valid CadastrarContatoEmpresaServicoDTO editarContatoEmpresaServico);

  /**
   * Excluir.
   *
   * @param id the id
   * @return the response DTO
   */
  @DELETE
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(description = "Recurso para Contato Empresa Serviço por id",
      summary = "Recurso para excluir um Contato Empresa Serviço por id",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = EntityDTO.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  public ResponseDTO<EntityDTO> excluir(@PathParam(value = "id") Long id);

}
