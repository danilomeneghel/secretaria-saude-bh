package br.gov.pbh.prodabel.hubsmsa.endpoint;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.AgendamentoRequestDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.agendamento.AgendamentoResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.security.JWTToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * @author weverton.lucena@ctis.com.br classe responsável por expor servico de agendamento
 */
@JWTToken
@Path("/agendamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "agendamentos", description = "Solicitar o agendamento do atendimento no SIGRAH")
public interface AgendamentoEndPoint {

  /**
   * Solicitar o agendamento do atendimento ao paciente.
   *
   * @param AgendamentoRequestDTO dados de agendamento
   * @return the response DTO
   */
  @POST
  @Path("/solicitarAgendamentoAtendimentoPaciente")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(description = "Recurso para solicitar o agendamento do atendimento no SIGRAH",
      summary = "Solicitar agendamento",
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  AgendamentoResponseDTO solicitarAgendamentoAtendimentoPaciente(@RequestBody(
      description = "Objeto AgendamentoRequestDTO com dados do agendamento", required = true,
      content = @Content(schema = @Schema(
          implementation = AgendamentoRequestDTO.class))) @Valid AgendamentoRequestDTO agendamentoDTO);

  /**
   * Recuperar atendimentos agendados.
   *
   * @return the string
   */
  @GET
  @Path("/recuperarAtendimentosAgendados")
  @Operation(description = "Recurso para buscar agendamentos realizados no Sigrah",
      summary = "Recuperar agendamentos",
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  String recuperarAtendimentosAgendados();

  /**
   * Recuperar atendimentos agendados.
   *
   * @return the string
   */
  @GET
  @Path("/registrarAtendimentoPaciente")
  @Operation(description = "Recurso para buscar registrar atendimentos no sisrede",
      summary = "Registrar atendimentos",
      responses = {
          @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")})
  String registrarAtendimentoPaciente();

}
