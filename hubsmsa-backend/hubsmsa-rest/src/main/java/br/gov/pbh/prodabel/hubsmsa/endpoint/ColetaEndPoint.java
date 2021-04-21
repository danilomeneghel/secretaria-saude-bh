package br.gov.pbh.prodabel.hubsmsa.endpoint;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import br.gov.pbh.prodabel.hubsmsa.security.JWTToken;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacaoColetaDTO;
import br.gov.pbh.prodabel.hubsmsa.ws.coletaexame.dto.SolicitacoesColetaExameResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@JWTToken
@Path("/coletas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "coletas", description = "Buscar solicitacoes de coleta de exames no sisrede")
public interface ColetaEndPoint {
  
  /**
   * Buscar solicitacoes coleta exame.
   *
   * @param solicitacaoColetaDTO the solicitacao coleta DTO
   * @return the solicitacoes coleta exame response
   */
  @GET
  @Path("/buscarSolicitacoesColetaExameDoPaciente")
  @Operation(description = "Recurso para buscar solicitacoes de coletas no sisrede",
      summary = "Buscar coletas de exames",
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  SolicitacoesColetaExameResponse buscarSolicitacoesColetaExame(
      @Parameter @BeanParam @Valid SolicitacaoColetaDTO solicitacaoColetaDTO);

}
