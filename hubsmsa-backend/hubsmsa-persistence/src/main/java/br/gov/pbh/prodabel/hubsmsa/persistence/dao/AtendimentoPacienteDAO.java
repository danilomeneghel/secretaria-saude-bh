package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_ATENDIMENTO_PACIENTE;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.AtendimentoPaciente;
import br.gov.pbh.prodabel.hubsmsa.ws.dao.SisredeDAO;

// @LocalBean
// @Stateless
public class AtendimentoPacienteDAO extends SisredeDAO<Long, AtendimentoPaciente> {

  private static final Logger LOG = LoggerFactory.getLogger(AtendimentoPacienteDAO.class);


  /**
   * Consultar atendimentos do dia.
   *
   * @param dataAtendimento the data atendimento
   * @return the list
   * @throws DAOException the DAO exception
   */
  public List<AtendimentoPaciente> consultarAtendimentosDoDia(final LocalDateTime dataAtendimento)
      throws DAOException {
    try {

      String qlString = new StringBuilder().append(
          "SELECT a FROM AtendimentoPaciente a ")
          .toString();


      return getEntityManager().createQuery(qlString, AtendimentoPaciente.class)
          // .setParameter("dataAtendimento", dataAtendimento)
          .getResultList();

    } catch (PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_ATENDIMENTO_PACIENTE, e);
      throw new DAOException(e);
    }
  }

}
