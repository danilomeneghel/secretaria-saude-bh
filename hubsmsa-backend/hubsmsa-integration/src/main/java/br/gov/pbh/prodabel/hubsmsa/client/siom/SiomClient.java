package br.gov.pbh.prodabel.hubsmsa.client.siom;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;
import br.gov.pbh.prodabel.hubsmsa.client.siom.dto.EstruturaAtualSiomDTO;
import br.gov.pbh.prodabel.hubsmsa.client.siom.dto.EstruturaHierarquicaInferiorSiomDTO;
import br.gov.pbh.prodabel.hubsmsa.client.siom.dto.UnidadeOrganizacionalSiomDTO;
import br.gov.pbh.prodabel.hubsmsa.client.siom.exception.SiomClientException;

/**
 * Interface de negócio do webservice estrutura atual (SIOM)
 * 
 * @author alysson.cordeiro@ctis.com.br
 *
 */
@Local
public interface SiomClient extends Serializable {

	/**
	 * Consultar os dados da Estrutura Atual
	 * 
	 * @return {@link List<EstruturaAtualSiomDTO>} -  Estrutura Atual
	 * 
	 * @throws SiomClientException - Caso ocorra erro na chamada do serviço
	 */
    List<EstruturaAtualSiomDTO> consultarEstruturaAtual() throws SiomClientException;
    
    /**
	 * Consultar os dados das unidades Organizacional.
     *
     * @param idUnidadeOrganizacional - Identificador da Unidade Organizacional
     * @param siglaUnidadeOrganizacional - Sigla da Unidade Organizacional
     * 
     * @return {@link List<UnidadeOrganizacionalSiomDTO>} - Unidades Organizacionais
     * 
     * @throws SiomClientException - Caso ocorra erro na chamada do serviço
     */
	List<UnidadeOrganizacionalSiomDTO> consultarUnidadeOrganizacional(Long idUnidadeOrganizacional,
			String siglaUnidadeOrganizacional) throws SiomClientException;
	
	/**
	 * Consultar os dados da Estrutura Hierarquica Inferior.
	 * 
	 * @param idUnidadeOrganizacional - Identificador da Unidade Organizacional
	 * @param siglaUnidadeOrganizacional - Sigla da Unidade Organizacional
	 * 
	 * @return {@link List<EstruturaHierarquicaInferiorSiomDTO>}
	 * 
	 * @throws SiomClientException - Caso ocorra erro na chamada do serviço
	 */
	List<EstruturaHierarquicaInferiorSiomDTO> consultarEstruturaHierarquicaInferior(Long idUnidadeOrganizacional,
			String siglaUnidadeOrganizacional) throws SiomClientException;
}
