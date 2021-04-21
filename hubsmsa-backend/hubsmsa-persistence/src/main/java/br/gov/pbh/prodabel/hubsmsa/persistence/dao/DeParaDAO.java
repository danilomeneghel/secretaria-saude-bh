package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_AS_EMPRESAS;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_OS_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_EXCLUIR_DEPARA;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.DeParaAudDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.depara.pesquisa.FiltroPesquisaLogDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.DePara_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.ContatoEmpresaAud_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.DeParaAud;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.DeParaAud_;
import br.gov.pbh.prodabel.hubsmsa.util.DeParaDAOUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;
import br.gov.pbh.prodabel.hubsmsa.util.query.EntityQuery;

// TODO: Auto-generated Javadoc
/**
 * Classe DAO responsável por manipular os dados de De/Para
 *
 * @author alisson.souza@ctis.com.br
 * @author claudivan.moreira@ctis.com.br
 */
@LocalBean
@Stateless
public class DeParaDAO extends GenericoDAO<Long, DePara> {

  protected static final String DE_PARA_REMOVIDA = "De/Para Removido";
  protected static final String DE_PARA_CRIADA = "De/Para Criado";
  protected static final String ATIVO = "Ativo";
  protected static final String INATIVO = "Inativo";
  protected static final String CAMPO_STATUS = "status";
  protected static final String SIM = "Sim";
  protected static final String NAO = "Não";
  private static final String DE_PARA_LOAD_GRAPH = "de-para-load-graph";
  private static final String JAVAX_PERSISTENCE_LOADGRAPH = "javax.persistence.loadgraph";
  private static final Logger LOG = LoggerFactory.getLogger(DeParaDAO.class);

  /**
   * Consultar de para.
   *
   * @param filtroPesquisaDePara the filtro pesquisa de para
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<DePara> consultarDePara(FiltroPesquisaDeParaDTO filtroPesquisaDePara)
      throws DAOException, RegistroNaoEncontradoException {

    try {
	  
    	  String qlString = DeParaDAOUtil.montarParametrosMaisDeUmCodigoPrimario(filtroPesquisaDePara);
    	  
    	  final List<DePara> resultado = getEntityManager()
    	          .createQuery(qlString, DePara.class)
    	          .setFirstResult(PaginacaoUtil.calcularPaginacaoAtual(filtroPesquisaDePara.getPagina(),
    	                  filtroPesquisaDePara.getItensPorPagina()))
    	              .setMaxResults(filtroPesquisaDePara.getItensPorPagina())
    	          .getResultList();
    	  
          return ValidadorUtil.validarNoResultList(resultado);
          
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
      throw new DAOException();
    }
  }
    
    /**
     * Consultar de para sem paginacao.
     *
     * @param filtroPesquisaDePara the filtro pesquisa de para
     * @return the list
     * @throws DAOException the DAO exception
     * @throws RegistroNaoEncontradoException the registro nao encontrado exception
     */
    public List<DePara> consultarDeParaSemPaginacao(FiltroPesquisaDeParaDTO filtroPesquisaDePara)
    	      throws DAOException, RegistroNaoEncontradoException {

    	    try {
    		  
    	    	  String qlString = DeParaDAOUtil.montarParametrosMaisDeUmCodigoPrimario(filtroPesquisaDePara);
    	    	  
    	    	  final List<DePara> resultado = getEntityManager()
    	    	          .createQuery(qlString, DePara.class)
    	    	          .getResultList();
    	    	  
    	          return ValidadorUtil.validarNoResultList(resultado);
    	          
    	    } catch (final PersistenceException e) {
    	      LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
    	      throw new DAOException();
    	    }

  }

  /**
   * Retorna um De/Para baseado no id passado com sua relação com {@link Empresa}.
   * @param id id referente ao sistema que deseja-se recuperar.
   * @return {@link Sistema} - Retorna Sistema correspondente ao id passado.
   * @throws DAOException Caso ocorra algum erro inesperado na operação.
   */
  public DePara consultaDeParaPorId(final Long id) throws DAOException {
    try {
      final CriteriaBuilder cb = getCriteriaBuilder();
      final CriteriaQuery<DePara> query = getCriteriaBuilder().createQuery(DePara.class);
      final Root<DePara> root = query.from(DePara.class);

      final EntityGraph<?> entityGraph = getEntityManager().createEntityGraph(DE_PARA_LOAD_GRAPH);
      final Predicate predicadoId = cb.equal(root.get(DePara_.id), id);

      query.select(root).where(predicadoId);

      return getEntityManager()
          .createQuery(query)
          .setHint(JAVAX_PERSISTENCE_LOADGRAPH, entityGraph)
          .getSingleResult();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
      throw new DAOException();
    }

  }

  /**
   * Retorna o total de registros de um determinado tipo no banco.
   *
   * @param filtroPesquisaDePara Objeto do tipo {@link FiltroPesquisaDeParaDTO}
   * que contém os campos referentes ao filtro de
   * Sistema.
   * @return O total de registro de um determinado tipo de acordo com os
   *         parametros passados
   * @throws DAOException Caso ocorsra algum erro inesperado na operação.
   */
	public Integer consultarTotalRegistros(final FiltroPesquisaDeParaDTO filtroPesquisaDePara) throws DAOException {

		try {

			String qlString = DeParaDAOUtil.montarParametrosMaisDeUmCodigoPrimario(filtroPesquisaDePara);

			final EntityGraph<?> pesquisarDeParaGraph = getEntityManager().createEntityGraph(DE_PARA_LOAD_GRAPH);

			final List<DePara> resultado = getEntityManager().createQuery(qlString, DePara.class)
					.setHint(JAVAX_PERSISTENCE_LOADGRAPH, pesquisarDeParaGraph).getResultList();

			return resultado.size();

		} catch (final PersistenceException e) {
			LOG.error(ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS, e);
			throw new DAOException();
		}
	}

  /**
   * Excluir de para sistemas do de para.
   *
   * @param idTipoDePara the id tipo de para
   * @throws DAOException the DAO exception
   */
  public void excluirDeParaSistemasDoDePara(final Long idTipoDePara) throws DAOException {
    try {
      String qlString = new StringBuilder()
          .append("DELETE FROM DePara d WHERE (d.deParaPrimario.id = :idSistema) or (d.deParaSecundario.id = :idSistema)")
          .toString();

      getEntityManager().createQuery(qlString).setParameter("idTipoDePara", idTipoDePara).executeUpdate();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_EXCLUIR_DEPARA_PELO_TIPO_DEPARA, e);
      throw new DAOException(e);
    }
  }

  /**
   * Consultar de paras do sistema.
   *
   * @param idSistema the id sistema
   * @return the list
   * @throws DAOException the DAO exception
   */
  public List<DePara> consultarDeParasDoSistema(final Long idSistema) throws DAOException {
    try {

      String qlString = new StringBuilder()
          .append("SELECT d FROM DePara d WHERE d.tipoDePara.id = :idSistema or d.sistemaSecundario.id = :idSistema")
          .toString();

      return getEntityManager()
          .createQuery(qlString, DePara.class)
          .setParameter("idSistema", idSistema)
          .getResultList();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
      throw new DAOException(e);
    }
  }

  /**
   * Consultar de para do tipo de para.
   *
   * @param idTipoDePara the id tipo de para
   * @return the list
   * @throws DAOException the DAO exception
   */
  public List<DePara> consultarDeParaDoTipoDePara(Long idTipoDePara) throws DAOException {
    try {
      String qlString = new StringBuilder()
          .append("SELECT d FROM DePara d WHERE d.tipoDePara.id = :idTipoDePara")
          .toString();

      return getEntityManager()
          .createQuery(qlString, DePara.class)
          .setParameter("idTipoDePara", idTipoDePara)
          .getResultList();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
      throw new DAOException();
    }

  }

  /**
   * Consultar de para.
   *
   * @param nomeTipo the nome tipo
   * @param idSistemaPrimario the id sistema primario
   * @param idSistemaSecundario the id sistema secundario
   * @param codigo the codigo
   * @return the de para ativo DTO
   * @throws DAOException the DAO exception
   */
  public DePara consultarDePara(String nomeTipo, Long idSistemaPrimario,
      Long idSistemaSecundario, String codigo) throws DAOException {
    try {
      String qlString = new StringBuilder().append(
          "SELECT d FROM DePara d " + "JOIN FETCH d.deParaPrimario dpp "
              + "JOIN FETCH d.deParaSecundario dps "
              + "JOIN FETCH d.tipoDePara tdp "
              + "WHERE lower(d.tipoDePara.nome) = :nomeTipo and d.sistemaPrimario.id = :idSistemaPrimario and d.sistemaSecundario.id = :idSistemaSecundario "
              + "and (lower(dpp.codigo) = :codigo or lower(dps.codigo) = :codigo )")
          .toString();

      Optional<DePara> deParaOpt = getEntityManager().createQuery(qlString, DePara.class)
          .setParameter("nomeTipo", nomeTipo.toLowerCase())
          .setParameter("idSistemaPrimario", idSistemaPrimario)
          .setParameter("idSistemaSecundario", idSistemaSecundario)
          .setParameter("codigo", codigo.toLowerCase())
          .getResultStream().findAny();

      if (deParaOpt.isPresent())
        return deParaOpt.get();
      else
        throw new DAOException();
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
      throw new DAOException();
    }

  }

  /**
   * Excluir de paras.
   *
   * @param idsDePara the ids de para
   * @throws DAOException the DAO exception
   */
  public void excluirDeParas(List<Long> idsDePara) throws DAOException {
    try {

      String qlString = new StringBuilder()
          .append("DELETE FROM DePara d WHERE d.id IN (:idsDePara)")
          .toString();

      getEntityManager().createQuery(qlString).setParameter("idsDePara", idsDePara).executeUpdate();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_EXCLUIR_DEPARA, e);
      throw new DAOException(e);
    }
  }

  /**
   * Consultar lista entidades.
   *
   * @param filtro the filtro
   * @return the list
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<DeParaAudDTO> consultarListaEntidades(FiltroPesquisaLogDeParaDTO filtro)
      throws RegistroNaoEncontradoException {

    StringBuilder jpql =
        new StringBuilder("SELECT new br.gov.pbh.prodabel.hubsmsa.dto.depara.DeParaAudDTO( "
        + " deParaAud.id," 
            + " revisao,"
        + " deParaAud.revType," 
        + " (SELECT tipoDeParaAud.nome FROM TipoDeParaAud tipoDeParaAud"
        + "    WHERE tipoDeParaAud.revisao = (SELECT MAX(tipoDeParaAud2.revisao) "
        + "    FROM TipoDeParaAud tipoDeParaAud2 WHERE tipoDeParaAud2.id = deParaAud.tipoDePara)),"
        + " deParaAud.nome,"
        + " deParaAud.status,"
        + " (SELECT sistemaAudPri.nome FROM SistemaAud sistemaAudPri"
        + "    WHERE sistemaAudPri.revisao = (SELECT MAX(sistemaAudPri2.revisao) "
        + "    FROM SistemaAud sistemaAudPri2 WHERE sistemaAudPri2.id = deParaAud.sistemaPrimario)),"
        + " (SELECT sistemaAudSec.nome FROM SistemaAud sistemaAudSec"
        + "    WHERE sistemaAudSec.revisao = (SELECT MAX(sistemaAudSec2.revisao) "
        + "    FROM SistemaAud sistemaAudSec2 WHERE sistemaAudSec2.id = deParaAud.sistemaSecundario)),"
        + " (SELECT sistemaAudPri.empresa.nomeFantasia FROM SistemaAud sistemaAudPri, Empresa emp"
        + "    WHERE sistemaAudPri.empresa = emp.id AND  sistemaAudPri.revisao = (SELECT MAX(sistemaAudPri2.revisao) "
        + "    FROM SistemaAud sistemaAudPri2 WHERE sistemaAudPri2.id = deParaAud.sistemaPrimario)),"
        + " (SELECT sistemaAudSec.empresa.nomeFantasia FROM SistemaAud sistemaAudSec, Empresa emp"
        + "    WHERE sistemaAudSec.empresa = emp.id AND  sistemaAudSec.revisao = (SELECT MAX(sistemaAudSec2.revisao) "
            + "    FROM SistemaAud sistemaAudSec2 WHERE sistemaAudSec2.id = deParaAud.sistemaSecundario)))")
            .append(" FROM DeParaAud deParaAud," + " Revision revisao"
                + " WHERE deParaAud.revisao = revisao.id");

    if (filtro.getId() != null) {
      jpql.append(" AND deParaAud.id = " + filtro.getId());
    }

    if (filtro.getIdTipoDePara() != null) {
      jpql.append(" AND deParaAud.tipoDePara.id = " + filtro.getIdTipoDePara());
    }

    if (filtro.getLoginUsuario() != null) {
      jpql.append(" AND revisao.matricula = '").append(filtro.getLoginUsuario()).append("'");
    }

    if (filtro.getNomeDePara() != null) {
      jpql.append(" AND UPPER(unaccent(deParaAud.nome)) LIKE UPPER(unaccent('%")
          .append(filtro.getNomeDePara()).append("%'))");
    }

    if (filtro.getIdSistemaPrimario() != null) {
      jpql.append(" AND deParaAud.sistemaPrimario.id = " + filtro.getIdSistemaPrimario());
    }

    if (filtro.getIdSistemaSecundario() != null) {
      jpql.append(" AND deParaAud.sistemaSecundario.id = " + filtro.getIdSistemaSecundario());
    }

    if (filtro.getDataInicial() != null) {
      jpql.append(" AND revisao.dtRevisao >='").append(filtro.getDataInicial()).append("'");
    }

    if (filtro.getDataFinal() != null) {
      jpql.append(" AND revisao.dtRevisao<'").append(filtro.getDataFinal()).append("'");
    }

    jpql.append(" order by revisao.id desc");

    List<DeParaAudDTO> resultado =
        getEntityManager().createQuery(jpql.toString(), DeParaAudDTO.class).getResultList();

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
  public void iterarSobreRevisoes(List<DeParaAudDTO> revisoesPorId, List<EnversDTO> listaDeRevisoes,
      DePara revisaoObjetoTemporario, DePara entidade, String... atributosValidos)
      throws IllegalAccessException, CloneNotSupportedException, InvocationTargetException,
      NoSuchMethodException {

    HashMap<Long, Object> mapObjetosAnteriores = new HashMap<>();

    for (int i = 0; i < revisoesPorId.size(); i++) {
      DeParaAudDTO itemRevisao = revisoesPorId.get(i);
      DePara objetoRevisao = new DePara();
      objetoRevisao.setId(itemRevisao.getId());
      Revision rev = itemRevisao.getRevisao();
      RevisionType revType = itemRevisao.getRevType();
      objetoRevisao.setTipoDeParaNome(itemRevisao.getNomeTipoDePara());
      objetoRevisao.setNome(itemRevisao.getNomeDePara());
      objetoRevisao.setStatus(itemRevisao.getStatus());
      objetoRevisao.setSistemaPrimarioNome(itemRevisao.getNomeSistemaPrimario());
      objetoRevisao.setSistemaSecundarioNome(itemRevisao.getNomeSistemaSecundario());
      objetoRevisao.setEmpresaPrimarioNome(itemRevisao.getNomeEmpresaPrimario());
      objetoRevisao.setEmpresaSecundarioNome(itemRevisao.getNomeEmpresaSecundario());

      revisaoObjetoTemporario = (DePara) mapObjetosAnteriores.get(objetoRevisao.getId());

      if (revType != RevisionType.ADD && revisaoObjetoTemporario == null) {
        revisaoObjetoTemporario = consultarRevisaoAnterior(objetoRevisao.getId(), rev.getId());
      }

      Map<String, Object> listaCamposAlterados = new HashMap<>();

      if (revType == RevisionType.DEL) {
        for (String attr : atributosValidos) {
          listaCamposAlterados.put(attr, DE_PARA_REMOVIDA);
        }
      } else {
        listaCamposAlterados =
            consultarValorAlterado(revisaoObjetoTemporario, objetoRevisao, atributosValidos);
      }

      for (Entry<String, Object> itemAlterado : listaCamposAlterados.entrySet()) {
        EnversDTO revisaoDTO = mapearRevisaoDTO(itemAlterado, revType, rev, entidade,
            objetoRevisao.getId(), revisaoObjetoTemporario);
        revisaoDTO.setTipoDePara(itemRevisao.getNomeTipoDePara());
        formatarValores(revisaoDTO);

        listaDeRevisoes.add(revisaoDTO);
      }

      mapObjetosAnteriores.put(objetoRevisao.getId(), objetoRevisao);
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

    DePara entidade = new DePara();
    List<DeParaAudDTO> revisoesPorId = consultarListaEntidades(filtro);
    DePara revisaoObjetoTemporario = null;

    try {
      iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario, entidade,
          atributos);

    } catch (Exception e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_DE_PARA, e);
      throw new DAOException();
    }

    return listaDeRevisoes;
  }

  /**
   * Buscar detalhe revisao.
   *
   * @param id the id
   * @param atributos the atributos
   * @return the list
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<EnversDTO> buscarDetalheRevisao(Long id, String[] atributos,
      FiltroPesquisaLogDeParaDTO filtroPesquisa) throws RegistroNaoEncontradoException {

    List<EnversDTO> listaDeRevisoes = new ArrayList<>();

    try {
      DePara entidade = new DePara();

      DePara dePara = (DePara) consultarEntidadePorIdRevisao(DePara.class, id);

      if (dePara != null) {

        filtroPesquisa.setId(dePara.getId());

        List<DeParaAudDTO> revisoesPorId = consultarListaEntidades(filtroPesquisa);

        DePara revisaoObjetoTemporario = null;

        iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario, entidade,
            atributos);
      }
    } catch (Exception e) {
      LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
      throw new RegistroNaoEncontradoException();
    }

    return listaDeRevisoes.stream().filter(e -> e.getIdRevision().equals(id))
        .collect(Collectors.toList());

  }


  /**
   * Mapear removido.
   *
   * @param idContatoEmpresa the id contato empresa
   * @param idRevisaoAtual the id revisao
   * @return the list
   */
  private DePara consultarRevisaoAnterior(Long idDePara, Long idRevisaoAtual) {

    final String orderBy = ContatoEmpresaAud_.REVISAO;

    DeParaAud deParaAud = EntityQuery.create(getEntityManager(), DeParaAud.class)
        .objectEqualsTo(DeParaAud_.ID, idDePara)
        .objectNotEqualsTo(DeParaAud_.REVISAO, idRevisaoAtual).addOrderBy(orderBy, "DESC")
        .setMaxResults(1).uniqueResult();

    DePara dePara = new DePara();
    dePara.setId(deParaAud.getId());
    dePara.setTipoDeParaNome(deParaAud.getTipoDePara().getNome());
    dePara.setNome(deParaAud.getNome());
    dePara.setStatus(deParaAud.getStatus());
    dePara.setEmpresaPrimarioNome(deParaAud.getSistemaPrimario().getEmpresa().getNomeFantasia());
    dePara
        .setEmpresaSecundarioNome(deParaAud.getSistemaSecundario().getEmpresa().getNomeFantasia());
    dePara.setSistemaPrimarioNome(deParaAud.getSistemaPrimario().getNome());
    dePara.setSistemaSecundarioNome(deParaAud.getSistemaSecundario().getNome());

    return dePara;

  }

  /**
   * Formatar valores.
   *
   * @param revisaoDTO the revisao DTO
   */
  private void formatarValores(EnversDTO revisaoDTO) {

    if (revisaoDTO.getRevisionType() == RevisionType.ADD)
      revisaoDTO.setOldValue(DE_PARA_CRIADA);
    if (revisaoDTO.getRevisionType() == RevisionType.DEL)
      revisaoDTO.setValue(DE_PARA_REMOVIDA);

    if (revisaoDTO.getField().equals(CAMPO_STATUS)) {
      revisaoDTO.setValue(formatarStatus(revisaoDTO.getValue()));
      revisaoDTO.setOldValue(formatarStatus(revisaoDTO.getOldValue()));
    }
  }

  /**
   * Formatar status.
   *
   * @param status the status
   * @return the string
   */
  public String formatarStatus(String status) {

    switch (status) {
      case SIM:
        status = ATIVO;
        break;
      case NAO:
        status = INATIVO;
        break;
    }
    return status;
  }

  /**
   * Montar historico.
   *
   * @param deParaAudList the de para aud list
   * @return the list
   */
  public List<HistoricoDeParaDTO> montarHistorico(List<DeParaAudDTO> deParaAudList) {
    List<HistoricoDeParaDTO> tipoDeParasAudDto = new ArrayList<>();

    for (DeParaAudDTO itemRevisao : deParaAudList) {

      HistoricoDeParaDTO historico = new HistoricoDeParaDTO();

      historico.setIdDePara(itemRevisao.getId());
      historico.setTipoDePara(itemRevisao.getNomeTipoDePara());
      historico.setNomeDePara(itemRevisao.getNomeDePara());
      historico.setCodigoSistemaPrimario(itemRevisao.getNomeSistemaPrimario());
      historico.setCodigoSistemaSecundario(itemRevisao.getNomeSistemaSecundario());

      historico.setEmpresaPrimaria(itemRevisao.getNomeEmpresaPrimario());
      historico.setEmpresaSecundaria(itemRevisao.getNomeEmpresaSecundario());
      historico.setAtivo(itemRevisao.getStatus().getName());
      historico.setLogin(itemRevisao.getRevisao().getMatricula());
      historico.setIdRevisao(itemRevisao.getRevisao().getId());
      historico.setDataEvento(
          TimeUtil.convertDataToStr(itemRevisao.getRevisao().getDtRevisao(), "dd/MM/yyyy HH:mm"));
      historico.setTipoRevisao(formataTipoRevisao(itemRevisao.getRevType()));
      tipoDeParasAudDto.add(historico);

    }
    return tipoDeParasAudDto;
  }

}
