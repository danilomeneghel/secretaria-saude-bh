package br.gov.pbh.prodabel.hubsmsa.util;

import java.util.Map;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
public class ClientUtil {

  private static final Logger LOGGER = Logger.getLogger(ClientUtil.class.getName());

  /**
   * Gets the client.
   *
   * @param url do recurso
   * @param query params
   * @return
   * @return uma string com o conteúdo retornado pelo recurso
   */
  public static <T> T get(String url, Map<String, String> params, Class<T> clazz) {

    try {

      WebTarget webTarget = ClientBuilder.newClient().target(url);

      if (params != null)
        for (Map.Entry<String, String> param : params.entrySet())
          webTarget.queryParam(param.getKey(), param.getValue());

      return webTarget.request(MediaType.APPLICATION_JSON).get(clazz);

    } catch (WebApplicationException e) {
      LOGGER.error("Erro ao acessar o recurso ", e);
      throw e;
    }

  }

  /**
   * Post.
   *
   * @param url the url
   * @param headers the headers
   * @param obj the obj
   * @return the response
   */
  public static Response post(String url, Map<String, String> headers, Object obj) {

    try {

      WebTarget webTarget = ClientBuilder.newClient().target(url);
      Invocation.Builder builder = webTarget.request();

      if (headers != null) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
          builder.header(entry.getKey(), entry.getValue());
        }
      }
      return builder.post(Entity.entity(obj, MediaType.APPLICATION_JSON));

    } catch (WebApplicationException e) {
      LOGGER.error("Erro ao acessar o recurso ", e);
      throw e;
    }

  }

  /**
   * Instantiates a new client util.
   */
  private ClientUtil() {}

}
