package br.gov.pbh.prodabel.hubsmsa.service;

import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import org.apache.commons.collections.CollectionUtils;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.DeParaSecundarioDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaSecundario;

@LocalBean
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DeParaSecundarioService extends GenericService<Long, DeParaSecundario> {
  @EJB
  private DeParaSecundarioDAO deParaSecundarioDAO;

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void gravar(final Set<DeParaSecundario> deParaSecundarios) {
    deParaSecundarios.forEach(dePara -> {
      this.gravar(dePara);
    });
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void excluirDeParaSecundarioDoDePara(List<Long> idsDePara) throws DAOException {
    try {
      this.deParaSecundarioDAO.excluirDeParaSecundarioDoDePara(idsDePara);
    } catch (final PersistenceException e) {
      throw new DAOException(e);
    }
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void excluirDeParaSecundarios(List<Long> idsDeParaSecundario) throws DAOException {
    try {
      System.out.println("idsDeParaSecundario: " + idsDeParaSecundario);
      if (CollectionUtils.isNotEmpty(idsDeParaSecundario)) {
        this.deParaSecundarioDAO.excluirDeParaSecundarios(idsDeParaSecundario);
      }
    } catch (final PersistenceException e) {
      throw new DAOException(e);
    }
  }
}
