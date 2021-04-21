package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_OS_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ItensDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaLogDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DeParaPrimario;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.ContatoEmpresaAud_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.DeParaPrimarioAud;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.DeParaPrimarioAud_;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;
import br.gov.pbh.prodabel.hubsmsa.util.query.EntityQuery;

/**
 * 
 * @author weverton.lucena@ctis.com.br Classe responsável por acessar dados de DeParaPrimario
 *
 */
@LocalBean
@Stateless
public class DeParaPrimarioDAO extends GenericoDAO<Long, DeParaPrimario> {

  private static final Logger LOG = LoggerFactory.getLogger(DeParaPrimarioDAO.class);

  protected static final String DEPARA_PRIMARIO_REMOVIDA = "De/Para Primário Removido";
  protected static final String DEPARA_PRIMARIO_CRIADA = "De/Para Primário Criado";

  /**
   * Excluir de para primario do de para.
   *
   * @param idsDePara the ids de para
   * @throws DAOException the DAO exception
   */
  public void excluirDeParaPrimarioDoDePara(List<Long> idsDePara) throws DAOException {
    try {

      String qlString = new StringBuilder()
          .append("DELETE FROM DeParaPrimario dpp WHERE dpp.dePara.id in (:idsDePara)")
          .toString();

      getEntityManager().createQuery(qlString).setParameter("idsDePara", idsDePara).executeUpdate();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
      throw new DAOException(e);
    }
  }

  /**
   * Excluir de para primarios.
   *
   * @param idsDeParaPrimario the ids de para primario
   * @throws DAOException the DAO exception
   */
  public void excluirDeParaPrimarios(List<Long> idsDeParaPrimario) throws DAOException {
    try {

      String qlString = new StringBuilder()
          .append("DELETE FROM DeParaPrimario dpp WHERE dpp.id in (:idsDeParaPrimario)")
          .toString();

      getEntityManager().createQuery(qlString).setParameter("idsDeParaPrimario", idsDeParaPrimario).executeUpdate();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
      throw new DAOException(e);
    }

  }
  
  /**
   * Consulta codigos primarios.
   *
   * @param idDePara the id de para
   * @return the list
   * @throws DAOException the DAO exception
   */
  public List<DeParaPrimario> consultaCodigosPrimarios(Long idDePara) throws DAOException {
    try {

      String qlString = new StringBuilder()
          .append("FROM DeParaPrimario dpp WHERE dpp.dePara.id = :idDePara").toString();

      List<DeParaPrimario> resultado = getEntityManager().createQuery(qlString)
          .setParameter("idDePara", idDePara).getResultList();

      return resultado;

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
      throw new DAOException(e);
    }
  }

  /**
   * Consulta codigos primarios aud.
   *
   * @param idDePara the id de para
   * @param idRevisao the id revisao
   * @return the list
   * @throws DAOException the DAO exception
   */
  public List<DeParaPrimarioAud> consultaCodigosPrimariosAud(Long idDePara, Long idRevisao)
      throws DAOException {
    try {

      String qlString = new StringBuilder().append(
          "FROM DeParaPrimarioAud dps WHERE dps.dePara.id = :idDePara and dps.revisao.id = :idRevisao")
          .toString();

      List<DeParaPrimarioAud> resultado = getEntityManager().createQuery(qlString)
          .setParameter("idDePara", idDePara).setParameter("idRevisao", idRevisao).getResultList();

      return resultado;

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
      throw new DAOException(e);
    }

  }

  /**
   * Buscar historico revisoes.
   *
   * @param filtro the filtro
   * @param atributos the atributos
   * @return the list
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   * @throws DAOException the DAO exception
   */
  public List<EnversDTO> buscarHistoricoRevisoes(FiltroPesquisaLogDeParaDTO filtro,
      String[] atributos) throws RegistroNaoEncontradoException, DAOException {
    List<EnversDTO> listaDeRevisoes = new ArrayList<>();
    setAuditReader(AuditReaderFactory.get(getEntityManager()));

    DeParaPrimario entidade = new DeParaPrimario();
    List<ItensDeParaDTO> revisoesPorId = consultarListaEntidades(filtro);
    DeParaPrimario revisaoObjetoTemporario = null;
    
    try {
      iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario,
          entidade,
          atributos);

    } catch (Exception e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
      throw new DAOException();
    }

    return listaDeRevisoes;
  }



  /**
   * Consultar lista entidades.
   *
   * @param filtro the filtro
   * @return the list
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  @SuppressWarnings("unchecked")
  private List<ItensDeParaDTO> consultarListaEntidades(FiltroPesquisaLogDeParaDTO filtro)
      throws RegistroNaoEncontradoException {

    StringBuilder jpql = new StringBuilder(
        "SELECT new br.gov.pbh.prodabel.hubsmsa.dto.ItensDeParaDTO(deParaPriAud.id,"
            + " deParaPriAud.revisao,"
        + " deParaPriAud.revType," + " deParaPriAud.codigo," + " deParaPriAud.descricao,"
        + " (SELECT deParaAud.tipoDePara.nome FROM DeParaAud deParaAud"
        + "    WHERE deParaAud.revisao = (SELECT MAX(deParaAud2.revisao) "
            + "    FROM DeParaAud deParaAud2 WHERE deParaAud2.id = deParaPriAud.dePara)) as tipoDePara,"
            + " deParaPriAud.dePara.id as idDePara)")
            .append(" FROM DeParaPrimarioAud deParaPriAud," + " Revision revisao"
                + " WHERE deParaPriAud.revisao = revisao.id");


    if (filtro.getId() != null) {
      jpql.append(" AND deParaPriAud.id = " + filtro.getId());

    }

    if (filtro.getCodigosDePara() != null) {
      jpql.append(" AND deParaPriAud.dePara.id IN (:idsDePara)");
    }

    jpql.append(" order by deParaPriAud.revisao.id");

    TypedQuery<ItensDeParaDTO> createQuery =
        getEntityManager().createQuery(jpql.toString(), ItensDeParaDTO.class);

    if (filtro.getCodigosDePara() != null) {
      createQuery.setParameter("idsDePara", filtro.getCodigosDePara());
    }

    List<ItensDeParaDTO> resultado = createQuery.getResultList();

    return ValidadorUtil.validarNoResultList(resultado);

  }

  /**
   * Iterar sobre revisoes.
   *
   * @param revisoesPorId the revisoes por id
   * @param listaDeRevisoes the lista de revisoes
   * @param revisaoObjetoTemporario the revisao objeto temporario
   * @param entidade the entidade
   * @param atributosValidos the atributos validos
   * @throws IllegalAccessException the illegal access exception
   * @throws CloneNotSupportedException the clone not supported exception
   * @throws InvocationTargetException the invocation target exception
   * @throws NoSuchMethodException the no such method exception
   */
  public void iterarSobreRevisoes(List<ItensDeParaDTO> revisoesPorId,
      List<EnversDTO> listaDeRevisoes,
      DeParaPrimario revisaoObjetoTemporario, DeParaPrimario entidade,
      String... atributosValidos) throws IllegalAccessException, CloneNotSupportedException,
      InvocationTargetException, NoSuchMethodException {

    HashMap<Long, Object> mapObjetosAnteriores = new HashMap<>();

    for (ItensDeParaDTO item : revisoesPorId) {
      DeParaPrimario objetoRevisao = new DeParaPrimario();
      objetoRevisao.setId(item.getId());
      Revision rev = item.getRevisao();
      RevisionType revType = item.getRevisaoType();
      objetoRevisao.setCodigo(item.getCodigo());
      objetoRevisao.setDescricao(item.getDescricao());

      revisaoObjetoTemporario = (DeParaPrimario) mapObjetosAnteriores.get(objetoRevisao.getId());

      if (revType != RevisionType.ADD && revisaoObjetoTemporario == null) {
        revisaoObjetoTemporario = consultarRevisaoAnterior(objetoRevisao.getId(), rev.getId());
      }

      Map<String, Object> listaCamposAlterados = new HashMap<>();

      if (revType == RevisionType.DEL) {
        for (String attr : atributosValidos) {
          listaCamposAlterados.put(attr, DEPARA_PRIMARIO_REMOVIDA);
        }
      } else {
        listaCamposAlterados =
            consultarValorAlterado(revisaoObjetoTemporario, objetoRevisao, atributosValidos);
      }

      for (Entry<String, Object> itemAlterado : listaCamposAlterados.entrySet()) {
        EnversDTO revisaoDTO = mapearRevisaoDTO(itemAlterado, revType, rev, entidade,
            objetoRevisao.getId(), revisaoObjetoTemporario);
        revisaoDTO.setTipoDePara(item.getTipoDePara());
        formatarValores(revisaoDTO);

        listaDeRevisoes.add(revisaoDTO);
      }

      mapObjetosAnteriores.put(objetoRevisao.getId(), objetoRevisao);
    }

  }


  /**
   * Consultar revisao anterior.
   *
   * @param idDeParaPrimario the id de para primario
   * @param idRevisaoAtual the id revisao atual
   * @return the de para primario
   */
  private DeParaPrimario consultarRevisaoAnterior(Long idDeParaPrimario, Long idRevisaoAtual) {

    final String orderBy = ContatoEmpresaAud_.REVISAO;

    DeParaPrimarioAud deParaPrimarioAud =
        EntityQuery.create(getEntityManager(), DeParaPrimarioAud.class)
            .objectEqualsTo(DeParaPrimarioAud_.ID, idDeParaPrimario)
            .objectNotEqualsTo(DeParaPrimarioAud_.REVISAO, idRevisaoAtual)
            .addOrderBy(orderBy, "DESC").setMaxResults(1).uniqueResult();

    DeParaPrimario deParaPrimario = new DeParaPrimario();
    deParaPrimario.setCodigo(deParaPrimarioAud.getCodigo());
    deParaPrimario.setDescricao(deParaPrimarioAud.getDescricao());

    return deParaPrimario;

  }

  /**
   * Buscar detalhe revisao.
   *
   * @param id the id
   * @param atributosValidos the atributos validos
   * @param filtroPesquisa the filtro pesquisa
   * @return the list
   * @throws RegistroNaoEncontradoException
   */
  public List<EnversDTO> buscarDetalheRevisao(Long id, String[] atributosValidos,
      FiltroPesquisaLogDeParaDTO filtroPesquisa) throws RegistroNaoEncontradoException {
    List<EnversDTO> listaDeRevisoes = new ArrayList<>();

    try {
      DeParaPrimario entidade = new DeParaPrimario();
      List<ItensDeParaDTO> revisoesPorId = new ArrayList<>();

      DeParaPrimario deParaPrimario =
          (DeParaPrimario) consultarEntidadePorIdRevisao(DeParaPrimario.class, id);
      if (deParaPrimario != null) {
        filtroPesquisa.setId(deParaPrimario.getId());

        revisoesPorId = consultarListaEntidades(filtroPesquisa);

        DeParaPrimario revisaoObjetoTemporario = null;

        iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario,
            entidade,
            atributosValidos);
      }
    } catch (Exception e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
      throw new RegistroNaoEncontradoException();
    }

    return listaDeRevisoes.stream().filter(e -> e.getIdRevision().equals(id))
        .collect(Collectors.toList());
  }

  /**
   * Formatar valores.
   *
   * @param revisaoDTO the revisao DTO
   */
  private void formatarValores(EnversDTO revisaoDTO) {

    if (revisaoDTO.getRevisionType() == RevisionType.ADD)
      revisaoDTO.setOldValue(DEPARA_PRIMARIO_CRIADA);
    if (revisaoDTO.getRevisionType() == RevisionType.DEL)
      revisaoDTO.setValue(DEPARA_PRIMARIO_REMOVIDA);

  }


}
