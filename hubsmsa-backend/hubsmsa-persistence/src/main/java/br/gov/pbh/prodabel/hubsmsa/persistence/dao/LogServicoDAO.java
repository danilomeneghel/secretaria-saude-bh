package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_LOG_SERVICO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.logservico.FiltroPesquisaLogServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoLogServicoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusExecucao;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;
import br.gov.pbh.prodabel.hubsmsa.util.EnumUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

// TODO: Auto-generated Javadoc
@LocalBean
@Stateless
public class LogServicoDAO extends GenericoDAO<Long, LogServico> {

  private static final Logger LOG = LoggerFactory.getLogger(LogServicoDAO.class);

  /**
   * Buscar por data E status.
   *
   * @param data the data
   * @param status the status
   * @return the list
   * @throws RegistroNaoEncontradoException
   * @throws DAOException
   */
  public List<LogServico> buscarPorDataEStatus(LocalDateTime data, StatusEnum status)
      throws RegistroNaoEncontradoException, DAOException {
    try {
      final StringBuilder builder = new StringBuilder();
      builder
          .append("SELECT l FROM LogServico l ").append("WHERE l.status = :status ")
          .append("and l.dataIncioEvento between :dataInicio and :dataFim");

      final List<LogServico> resultado =
          getEntityManager().createQuery(builder.toString(), LogServico.class)
          .setParameter("status", status)
              .setParameter("dataInicio", data).setParameter("dataFim", data)
          .getResultList();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error("Erro ao consultar LogServico", e);
      throw new DAOException();
    }
  }

  /**
   * Buscar resquisicoes falhas.
   *
   * @param data the data
   * @return the list
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   * @throws DAOException the DAO exception
   */
  public List<LogServico> buscarResquisicoesFalhas()
      throws RegistroNaoEncontradoException, DAOException {
    try {
      final StringBuilder builder = new StringBuilder();
      builder
          .append(
              "SELECT l3 FROM LogServico l3 left join l3.logsServicoFilhos l4 WHERE l3.id NOT IN ")
          .append("(SELECT l2.logServicoPai.id FROM LogServico l JOIN LogServico l2 ")
          .append(" on l.id = l2.logServicoPai.id ")
          .append(
              "WHERE l2.status = 'S' ) AND l3.status = 'F' AND l3.logServicoPai.id is null ")
          .append("AND l3.dataInicioEvento > :dataInicio");

      LocalDateTime localDate = LocalDateTime.of(2020, 12, 16, 0, 0);
      Date date1 = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
      final List<LogServico> resultado = getEntityManager()
          .createQuery(builder.toString(), LogServico.class).setParameter("dataInicio", date1)
          .getResultList();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error("Erro ao consultar LogServico", e);
      throw new DAOException();
    }
  }

  /**
   * Consultar log servico.
   *
   * @param filtro the filtro
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<LogServico> consultarLogServico(final FiltroPesquisaLogServicoDTO filtro)
      throws DAOException, RegistroNaoEncontradoException {

    try {

      String orderBy =
          ColunaOrdenacaoLogServicoEnum.valueOf(filtro.getOrderBy().toUpperCase()).getName();

      StringBuilder sb = montaSqlLogServico();

      final List<StatusExecucao> statusList = EnumUtil.toStatusExecucaoEnumList(filtro.getStatus());

      if (null != filtro.getDataInicial()) {
        sb.append(" AND logServico.dataInicioEvento >='").append(filtro.getDataInicial())
            .append("'");
      }

      if (filtro.getDataFinal() != null) {
        sb.append(" AND logServico.dataFimEvento <='").append(filtro.getDataFinal()).append("'");
      }

      if (null != filtro.getIdServico()) {
        sb.append(" AND logServico.servico.id = ").append(filtro.getIdServico());
      }

      if (null != filtro.getStatus() && !statusList.isEmpty()) {
        sb.append(" AND logServico.status IN (:status)");
      }


      if (null != orderBy) {
        sb.append(" ORDER BY ").append(orderBy).append(" ").append(filtro.getTipoOrdenacao());
      }

      TypedQuery<LogServico> createQuery =
          getEntityManager().createQuery(sb.toString(), LogServico.class);
      if (!statusList.isEmpty()) {
        createQuery.setParameter("status", statusList);
      }

      final List<LogServico> resultado = createQuery
          .setFirstResult(PaginacaoUtil.calcularPaginacaoAtual(filtro.getNumeroPagina(),
              filtro.getItensPorPagina()))
          .setMaxResults(filtro.getItensPorPagina()).getResultList();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_LOG_SERVICO, e);
      throw new DAOException();
    }
  }

  /**
   * Monta sql log servico.
   *
   * @return the string builder
   */
  private StringBuilder montaSqlLogServico() {
    final String INNER_JOIN_FETCH = " INNER JOIN FETCH";
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT logServico FROM LogServico logServico");
    sb.append(INNER_JOIN_FETCH).append(" logServico.servico AS servico");
    sb.append(INNER_JOIN_FETCH).append(" servico.sistemaPrimario AS sistemaPrimario");
    sb.append(INNER_JOIN_FETCH).append(" servico.sistemaSecundario AS sistemaSecundario");
    sb.append(INNER_JOIN_FETCH).append(" sistemaPrimario.empresa AS empresaSistemaPrimario");
    sb.append(INNER_JOIN_FETCH).append(" sistemaSecundario.empresa AS empresaSistemaSecundario");
    sb.append(" WHERE 1 = 1");
    return sb;
  }

}
