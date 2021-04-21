package br.gov.pbh.prodabel.hubsmsa.endpoint;

import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import br.gov.pbh.prodabel.hubsmsa.dto.PaginacaoPublicaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.VisualizarUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso.FiltroPesquisaLogAcessoUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.logacesso.VisualizarLogAcessoUsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.security.JWTToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// TODO: Auto-generated Javadoc
@JWTToken
@Path("/usuario")
@Tag(name = "Usuario", description = "Recurso para consultas e cadastros de Usuario")
@Produces(MediaType.APPLICATION_JSON)
public interface UsuarioEndPoint {

  /**
   * Checkin.
   *
   * @return the response DTO
   */
  @POST
  @Path("/checkin")
  @Operation(description = "Recurso para registrar entrada do usuario no sistema",
      summary = "Registra entrada do usuario no sistema",
      responses = {
          @ApiResponse(responseCode = "200",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = VisualizarUsuarioDTO.class))),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  ResponseDTO<String> checkin();
  
  /**
   * Consultar acesso usuarios.
   *
   * @param filtroPesquisaDTO the filtro pesquisa DTO
   * @return the paginacao publica DTO
   */
  @GET
  @Path("/acessos-usuario")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)	
  @Operation(description = "Recurso para buscar o registro de acessos de usuários", 
		summary = "Recurso para buscar o registro de acessos de usuários",
		responses = {
		@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FiltroPesquisaLogAcessoUsuarioDTO.class))),
		@ApiResponse(responseCode = "400", description = "Bad Request"),
		@ApiResponse(responseCode = "404", description = "Not found"),
		@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public PaginacaoPublicaDTO<VisualizarLogAcessoUsuarioDTO> consultarAcessoUsuarios(@BeanParam @Valid FiltroPesquisaLogAcessoUsuarioDTO filtroPesquisaDTO);

    /**
     * Consultar acesso usuarios.
     *
     * @param filtroPesquisaDTO the filtro pesquisa DTO
     * @return the paginacao publica DTO
     */
    @GET
    @Path("/acessos-usuario/csv/log")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Recurso para gerar csv",
        summary = "Recurso para gerar log de acesso de usuários para csv",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/vnd.ms-excel",
                    schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    Response gerarLogCsv(
        @Parameter(
            description = "Filtro da exportação do log de acesso de usuários") @Valid @BeanParam FiltroPesquisaLogAcessoUsuarioDTO filtro);

    /**
     * Consultar acesso usuarios.
     *
     * @param filtroPesquisaDTO the filtro pesquisa DTO
     * @return the paginacao publica DTO
     */
    @GET
    @Path("/acessos-usuario/excel/log")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Recurso para gerar excel",
        summary = "Recurso para gerar log de acesso de usuários para excel",
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(mediaType = "application/vnd.ms-excel",
                    schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
    Response gerarLogExcel(@Parameter(
        description = "Filtro da exportação do log de acesso de usuários") @Valid @BeanParam FiltroPesquisaLogAcessoUsuarioDTO filtro);

  /**
   * Selecao usuario.
   *
   * @return the list
   */
  @GET
  @Path("/selecao")
  @Operation(description = "Listar os Usuários cadastradas no sistema para selecao", summary = "Listar Usuários para selecao", responses = {
		@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SelecaoDTO.class))),
	    @ApiResponse(responseCode = "400", description = "Bad Request"),
		@ApiResponse(responseCode = "404", description = "Not found"),
		@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	List<SelecaoDTO> selecaoUsuario();
}
