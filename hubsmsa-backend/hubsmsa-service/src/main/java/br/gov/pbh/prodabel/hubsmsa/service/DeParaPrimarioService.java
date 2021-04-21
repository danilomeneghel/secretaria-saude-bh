package br.gov.pbh.prodabel.hubsmsa.service;

import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.DeParaDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.DeParaPrimarioDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario;

@LocalBean
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DeParaPrimarioService extends GenericService<Long, DeParaPrimario> {
  
  private static final Logger LOG = LoggerFactory.getLogger(DeParaDAO.class);
  @EJB
  private DeParaPrimarioDAO deParaPrimarioDAO;

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void gravar(final Set<DeParaPrimario> deParaPrimarios) {
    deParaPrimarios.forEach(dePara -> {
      this.gravar(dePara);
    });
  } 
  
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void excluirDeParaPrimarioDoDePara(final List<Long> idDePara) throws DAOException {
    try {
      this.deParaPrimarioDAO.excluirDeParaPrimarioDoDePara(idDePara);
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
      throw new DAOException(e);
    }
  }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void excluirDeParaPrimarios(final List<Long> idsDeParaPrimario) throws DAOException {
    try {
      if (CollectionUtils.isNotEmpty(idsDeParaPrimario)) {
        this.deParaPrimarioDAO.excluirDeParaPrimarios(idsDeParaPrimario);
      }
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
      throw new DAOException(e);
    }
  }
  
}
