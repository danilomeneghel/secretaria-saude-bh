package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_CONTATO_EMPRESA;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_OS_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoempresaservico.pesquisa.FiltroPesquisaContatoEmpresaServicoDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoContatoEmpresaServicoEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Servico;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

// TODO: Auto-generated Javadoc
/**
 * 
 * @author weverton.lucena@ctis.com.br
 *
 *         Classe responsável por gerenciar os dados de ContatoEmpresaServico
 */
@LocalBean
@Stateless
public class ContatoEmpresaServicoDAO extends GenericoDAO<Long, ContatoEmpresaServico> {

  private static final Logger LOG = LoggerFactory.getLogger(ContatoEmpresaServicoDAO.class);

  /**
   * Consultar por servico.
   *
   * @param servico the servico
   * @return the list
   * @throws DAOException the DAO exception
   */
  public List<ContatoEmpresaServico> consultarPorServicoSucesso(Servico servico)
      throws DAOException {
    try {

      String qlString = new StringBuilder().append(
          "SELECT c FROM ContatoEmpresaServico c JOIN FETCH c.contatoEmpresa WHERE c.servico = :servico and c.notificacaoSucesso = '1'")
          .toString();

      return getEntityManager().createQuery(qlString, ContatoEmpresaServico.class)
          .setParameter("servico", servico).getResultList();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
      throw new DAOException(e);
    }
  }

  /**
   * Consultar por servico falha.
   *
   * @param servico the servico
   * @return the list
   * @throws DAOException the DAO exception
   */
  public List<ContatoEmpresaServico> consultarPorServicoFalha(Servico servico) throws DAOException {
    try {

      String qlString = new StringBuilder()
          .append(
              "SELECT c FROM ContatoEmpresaServico c JOIN FETCH c.contatoEmpresa WHERE c.servico = :servico and c.notificacaoFalha = '1'")
          .toString();

      return getEntityManager().createQuery(qlString, ContatoEmpresaServico.class)
          .setParameter("servico", servico).getResultList();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
      throw new DAOException(e);
    }
  }

  /**
   * Consultar contato empresa servico.
   *
   * @param filtro the filtro
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<ContatoEmpresaServico> consultarContatoEmpresaServico(
      final FiltroPesquisaContatoEmpresaServicoDTO filtro)
      throws DAOException, RegistroNaoEncontradoException {

    try {

      String orderBy = ColunaOrdenacaoContatoEmpresaServicoEnum
          .valueOf(filtro.getOrderBy().toUpperCase()).getName();

      
      StringBuilder sb = montaSqlContatoEmpresaServico();

      if (null != filtro.getIdEmpresa()) {
        sb.append(" AND empresa.id = ").append(filtro.getIdEmpresa());
      }
      
      if (null != filtro.getIdContato()) {
        sb.append(" AND contatoEmpresa.id = ").append(filtro.getIdContato());
      }

      if (null != filtro.getIdServico()) {
        sb.append(" AND servico.id = ").append(filtro.getIdServico());
      }

      if (null != filtro.getIdSistema()) {
        sb.append(" AND (sistemaPrimario.id = ").append(filtro.getIdSistema())
            .append(" OR  sistemaSecundario.id = ").append(filtro.getIdSistema()).append(")");
      }
      
      if (null != orderBy) {
        sb.append(" ORDER BY ").append(orderBy).append(" ").append(filtro.getTipoOrdenacao());
      }

      final List<ContatoEmpresaServico> resultado = getEntityManager()
          .createQuery(sb.toString(), ContatoEmpresaServico.class)
          .setFirstResult(PaginacaoUtil.calcularPaginacaoAtual(filtro.getNumeroPagina(),
              filtro.getItensPorPagina()))
              .setMaxResults(filtro.getItensPorPagina())
          .getResultList();
  
      return ValidadorUtil.validarNoResultList(resultado);
      

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_CONTATO_EMPRESA, e);
      throw new DAOException();
    }
  }

  /**
   * Consulta contato empresa servico por id.
   *
   * @param id the id
   * @return the contato empresa servico
   * @throws DAOException the DAO exception
   */
  public ContatoEmpresaServico consultaContatoEmpresaServicoPorId(Long id) throws DAOException {
    try {

      StringBuilder sb = montaSqlContatoEmpresaServico();

      sb.append(" AND ces.id = ").append(id);

      return getEntityManager().createQuery(sb.toString(), ContatoEmpresaServico.class)
          .getSingleResult();

    } catch (PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS, e);
      throw new DAOException();
    }
  }

  /**
   * Monta sql contato empresa servico.
   *
   * @return the string builder
   */
  private StringBuilder montaSqlContatoEmpresaServico() {
    final String INNER_JOIN_FETCH = " INNER JOIN FETCH";
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT ces FROM ContatoEmpresaServico ces");
    sb.append(INNER_JOIN_FETCH).append(" ces.contatoEmpresa AS contatoEmpresa");
    sb.append(INNER_JOIN_FETCH).append(" ces.servico AS servico");
    sb.append(INNER_JOIN_FETCH).append(" servico.sistemaPrimario AS sistemaPrimario");
    sb.append(INNER_JOIN_FETCH).append(" servico.sistemaSecundario AS sistemaSecundario");
    sb.append(INNER_JOIN_FETCH).append(" contatoEmpresa.empresa AS empresa");
    sb.append(INNER_JOIN_FETCH).append(" sistemaPrimario.empresa AS empresaSistemaPrimario");
    sb.append(INNER_JOIN_FETCH).append(" sistemaSecundario.empresa AS empresaSistemaSecundario");
    sb.append(" WHERE 1 = 1");
    return sb;
  }

  /**
   * Verificar existencia contato empresa para mesmo servico.
   *
   * @param idContatoEmpresa the id contato empresa
   * @param idServico the id servico
   * @return the contato empresa servico
   * @throws DAOException the DAO exception
   */
  public ContatoEmpresaServico verificarExistenciaContatoEmpresaParaMesmoServico(
      Long idContatoEmpresa, Long idServico) throws DAOException {
    StringBuilder jpql = new StringBuilder("SELECT ces FROM ContatoEmpresaServico ces ")
        .append(" INNER JOIN FETCH ces.contatoEmpresa AS contatoEmpresa")
        .append(" INNER JOIN FETCH ces.servico AS servico").append(" WHERE ")
        .append("ces.contatoEmpresa.id").append(" = ").append(":contatoEmpresa").append(" AND ")
        .append("ces.servico.id").append(" = ").append(":servico");        

    TypedQuery<ContatoEmpresaServico> query =
        getEntityManager().createQuery(jpql.toString(), ContatoEmpresaServico.class);
    query.setParameter(ContatoEmpresaServico_.CONTATO_EMPRESA, idContatoEmpresa);
    query.setParameter(ContatoEmpresaServico_.SERVICO, idServico);

    try {
      return query.getSingleResult();
    } catch (PersistenceException e) {
      return null;
    }
  }
  
  /**
   * Verificar existencia contato empresa para mesmo servico.
   *
   * @param id the id
   * @param idContatoEmpresa the id contato empresa
   * @param idServico the id servico
   * @return the contato empresa servico
   */
  public ContatoEmpresaServico verificarExistenciaContatoEmpresaParaMesmoServico(Long id,
      Long idContatoEmpresa, Long idServico) {
    StringBuilder jpql = new StringBuilder("SELECT ces FROM ContatoEmpresaServico ces ")
        .append(" INNER JOIN FETCH ces.contatoEmpresa AS contatoEmpresa")
        .append(" INNER JOIN FETCH ces.servico AS servico").append(" WHERE ")
        .append("ces.contatoEmpresa.id").append(" = ").append(":contatoEmpresa").append(" AND ")
        .append("ces.servico.id").append(" = ").append(":servico")
        .append(" AND ces.id  != ").append(":id");

    TypedQuery<ContatoEmpresaServico> query =
        getEntityManager().createQuery(jpql.toString(), ContatoEmpresaServico.class);
    query.setParameter(ContatoEmpresaServico_.CONTATO_EMPRESA, idContatoEmpresa);
    query.setParameter(ContatoEmpresaServico_.SERVICO, idServico);
    query.setParameter(ContatoEmpresaServico_.ID, id);

    try {
      return query.getSingleResult();
    } catch (PersistenceException e) {
      return null;
    }
  }
  


}
