package br.gov.pbh.prodabel.hubsmsa.client.siom.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import br.gov.pbh.prodabel.hubsmsa.client.siom.SiomClient;
import br.gov.pbh.prodabel.hubsmsa.client.siom.dto.EstruturaAtualSiomDTO;
import br.gov.pbh.prodabel.hubsmsa.client.siom.dto.EstruturaHierarquicaInferiorSiomDTO;
import br.gov.pbh.prodabel.hubsmsa.client.siom.dto.UnidadeOrganizacionalSiomDTO;
import br.gov.pbh.prodabel.hubsmsa.client.siom.exception.SiomClientException;
import br.gov.pbh.prodabel.hubsmsa.util.ResourcesUtil;

/**
 * Classe responsável pelo negócio do webservice estrutura atual (SIOM).
 * 
 * @author alysson@ctis.com.br
 *
 */
@Stateless
public class SiomClientImpl implements SiomClient {

	private static final Logger LOGGER = Logger.getLogger(SiomClientImpl.class.getName());

	private static final long serialVersionUID = 5239914510228355843L;

	private static final String SIOM_BASE_URL = "SIOM_BASE_URL";
	private static final String RESPONSAVEL_PARAM = "responsavel";
	private static final String SIGLA_PARAM = "sigla";
	private static final String RESPONSAVEL_INTEGRADOR_MUNICIPAL = "IntegradorMunicipal";
	private static final String ID_UNIDADE_ORGANIZACIONAL_PARAM = "identificador";
	private static final String ESTRUTURA_ATUAL_PATH = "estruturaatual";
	private static final String UNIDADE_ORGANIZACIONAL_PATH = "unidadeorganizacional";
	private static final String ESTRUTURA_HIERARQUICA_INFERIOR_PATH = "estruturahierarquicainferior";
	private static final String SERVICOS_URL_RESOURCE = "servicos_url";

	@Override
	public List<EstruturaAtualSiomDTO> consultarEstruturaAtual() throws SiomClientException {

		try {

			return ClientBuilder.newClient().target(ResourcesUtil.getProperty(SERVICOS_URL_RESOURCE, SIOM_BASE_URL))
					.path(ESTRUTURA_ATUAL_PATH).queryParam(RESPONSAVEL_PARAM, RESPONSAVEL_INTEGRADOR_MUNICIPAL)
					.request(MediaType.APPLICATION_JSON).get(new GenericType<List<EstruturaAtualSiomDTO>>() {
					});

		} catch (WebApplicationException e) {
			LOGGER.error("Erro ao acessar o recurso Estrutura Atual do WS SIOM", e);
			throw new SiomClientException(e.getMessage());
		}
	}

	@Override
	public List<UnidadeOrganizacionalSiomDTO> consultarUnidadeOrganizacional(Long idUnidadeOrganizacional,
			String siglaUnidadeOrganizacional) throws SiomClientException {

		try {

			return ClientBuilder.newClient().target(ResourcesUtil.getProperty(SERVICOS_URL_RESOURCE, SIOM_BASE_URL))
					.path(UNIDADE_ORGANIZACIONAL_PATH)
					.queryParam(ID_UNIDADE_ORGANIZACIONAL_PARAM, idUnidadeOrganizacional)
					.queryParam(SIGLA_PARAM, siglaUnidadeOrganizacional)
					.queryParam(RESPONSAVEL_PARAM, RESPONSAVEL_INTEGRADOR_MUNICIPAL).request(MediaType.APPLICATION_JSON)
					.get(new GenericType<List<UnidadeOrganizacionalSiomDTO>>() {
					});

		} catch (WebApplicationException e) {
			LOGGER.error("Erro ao acessar o recurso Unidade Organizacional do WS SIOM", e);
			throw new SiomClientException(e.getMessage());
		}
	}

	@Override
	public List<EstruturaHierarquicaInferiorSiomDTO> consultarEstruturaHierarquicaInferior(Long idUnidadeOrganizacional,
			String siglaUnidadeOrganizacional) throws SiomClientException {

		try {
			return ClientBuilder.newClient().target(ResourcesUtil.getProperty(SERVICOS_URL_RESOURCE, SIOM_BASE_URL))
					.path(ESTRUTURA_HIERARQUICA_INFERIOR_PATH)
					.queryParam(ID_UNIDADE_ORGANIZACIONAL_PARAM, idUnidadeOrganizacional)
					.queryParam(SIGLA_PARAM, siglaUnidadeOrganizacional)
					.queryParam(RESPONSAVEL_PARAM, RESPONSAVEL_INTEGRADOR_MUNICIPAL).request(MediaType.APPLICATION_JSON)
					.get(new GenericType<List<EstruturaHierarquicaInferiorSiomDTO>>() {
					});

		} catch (WebApplicationException e) {
			LOGGER.error("Erro ao acessar o recurso Estrutura Hierarquica Inferior do WS SIOM", e);
			throw new SiomClientException(e.getMessage());
		}
	}

}
