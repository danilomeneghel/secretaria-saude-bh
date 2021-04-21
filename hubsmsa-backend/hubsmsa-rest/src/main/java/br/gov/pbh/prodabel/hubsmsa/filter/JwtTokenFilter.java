package br.gov.pbh.prodabel.hubsmsa.filter;

import java.io.IOException;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.mensagem.MensagemErro;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.UserRevisionService;
import br.gov.pbh.prodabel.hubsmsa.security.JWTToken;

@Provider
@JWTToken
@Priority(Priorities.AUTHORIZATION)
public class JwtTokenFilter implements ContainerRequestFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);

  @Context
  private HttpServletRequest request;

  @Inject
  private UserRevisionService userRevisionService;

  @Override
  public void filter(final ContainerRequestContext requestContext) throws IOException {
    try {
      final KeycloakPrincipal<?> principal = (KeycloakPrincipal<?>) this.request.getUserPrincipal();
      final KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
      this.setUsuarioAutenticado(context.getToken());
    } catch (final NegocioException e) {
      LOGGER.warn("Falha ao processar solicitação: {}", e.getMessage());
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
          .entity(new MensagemErro(e.getMessage())).build());
    } catch (final Exception e) {
      LOGGER.warn("Falha ao processar solicitação: {}", e.getMessage());
      requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(
              new MensagemErro("Erro desconhecido, por favor, contate o administrador do sistema."))
          .build());
    }
  }

  private void setUsuarioAutenticado(final AccessToken token) {
    final String matricula = token.getPreferredUsername();
    final String nomeUsuario = token.getName();
    final String emailUsuario = token.getEmail();
    this.userRevisionService.salvar(nomeUsuario, matricula, emailUsuario);
  }

}
