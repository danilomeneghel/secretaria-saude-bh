package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_OS_SERVICOS;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

// TODO: Auto-generated Javadoc
/**
 * Classe ServicoDAO responsável por manipular os dados de Servico
 *
 * @author weverton.lucena@ctis.com.br
 */
@LocalBean
@Stateless
public class ServicoDAO extends GenericoDAO<Long, Servico> {

  private static final Logger LOG = LoggerFactory.getLogger(ServicoDAO.class);

  /**
   * Consultar de para do tipo de para.
   *
   * @param idSistemaPrimario o id sistema primario
   * @param idSistemaSecundario o id sistema secundario
   * @param nomeServico o nome servico correspondente aos parametros da pesquisa
   * @return a lista de Servico
   * @throws DAOException the DAO exception
   */
  public List<Servico> consultarServico(Long idSistemaPrimario, Long idSistemaSecundario,
      String nomeServico) throws DAOException {
    try {
      String qlString = new StringBuilder().append(
          "SELECT s FROM Servico s WHERE s.sistemaPrimario.id = :idSistemaPrimario and s.sistemaSecundario.id = :idSistemaSecundario and lower(s.nome) = :nomeServico")
          .toString();

      return getEntityManager().createQuery(qlString, Servico.class)
          .setParameter("idSistemaPrimario", idSistemaPrimario)
          .setParameter("idSistemaSecundario", idSistemaSecundario)
          .setParameter("nomeServico", nomeServico.toLowerCase()).getResultList();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_SERVICOS, e);
      throw new DAOException();
    }

  }


  /**
   * Consultar servico primario ou secundario.
   *
   * @param idSistema the id sistema
   * @return the list
   * @throws DAOException the DAO exception
   */
  public List<Servico> consultarServicoPrimarioOuSecundario(Long idSistema) throws DAOException {
    try {
      String qlString = new StringBuilder().append(
          "SELECT s FROM Servico s WHERE s.sistemaPrimario.id = :idSistema or s.sistemaSecundario.id = :idSistema ORDER BY s.nome")
          .toString();

      return getEntityManager().createQuery(qlString, Servico.class)
          .setParameter("idSistema", idSistema).getResultList();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_SERVICOS, e);
      throw new DAOException();
    }

  }

  /**
   * Consultar selecao servico.
   *
   * @param idSistema the id sistema
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException
   */
  public List<SelecaoDTO> consultarSelecaoServico(Long idSistema)
      throws DAOException, RegistroNaoEncontradoException {
    try {
      final StringBuilder qlString = new StringBuilder();
      qlString
          .append("SELECT DISTINCT new br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO(e.id, e.nome) ");
      qlString.append("FROM Servico e ");
      qlString.append(
          "WHERE (e.sistemaPrimario.id = :idSistema or e.sistemaSecundario.id = :idSistema) ");
      qlString.append("ORDER BY e.nome");

      final List<SelecaoDTO> resultado =
          getEntityManager().createQuery(qlString.toString(), SelecaoDTO.class)
              .setParameter("idSistema", idSistema).getResultList();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_SERVICOS, e);
      throw new DAOException();
    }
  }

  /**
   * Consulta servicos.
   *
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException
   */
  public List<Servico> consultaServicos() throws DAOException {
    try {

      StringBuilder sb = montarSqlServico();

      return getEntityManager().createQuery(sb.toString(), Servico.class).getResultList();

    } catch (PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_SERVICOS, e);
      throw new DAOException();
    }
  }

  /**
   * Consulta servicos.
   *
   * @param idServico the id servico
   * @return the list
   * @throws DAOException the DAO exception
   */
  public Servico consultaServico(Long idServico) throws DAOException {
    try {

      StringBuilder sb = montarSqlServico();
      sb.append(" WHERE s.id = ").append(idServico);

      return getEntityManager().createQuery(sb.toString(), Servico.class).getSingleResult();

    } catch (PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_SERVICOS, e);
      throw new DAOException();
    }
  }


  /**
   * Montar sql servico.
   *
   * @return the string builder
   */
  private StringBuilder montarSqlServico() {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT s FROM Servico s");
    sb.append(" INNER JOIN FETCH s.sistemaPrimario AS sistemaPrimario ");
    sb.append(" INNER JOIN FETCH s.sistemaSecundario AS sistemaSecundario ");
    sb.append(" INNER JOIN FETCH sistemaPrimario.empresa AS empresaPrimario");
    sb.append(" INNER JOIN FETCH sistemaSecundario.empresa AS empresaSecundario");
    return sb;
  }


}
