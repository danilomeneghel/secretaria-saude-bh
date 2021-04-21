package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.PARAM_NOME;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_OS_TIPOS_DE_PARA;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.DadosTipodeParaAudDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.FiltroPesquisaLogTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.FiltroPesquisaTipoDeParaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoTipoDeParaEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.TipoDePara;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.TipoDePara_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.ContatoEmpresaAud_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.TipoDeParaAud;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.TipoDeParaAud_;
import br.gov.pbh.prodabel.hubsmsa.util.EnumUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;
import br.gov.pbh.prodabel.hubsmsa.util.query.EntityQuery;

// TODO: Auto-generated Javadoc
/**
 * Classe DAO responsável por manipular os dados de Tipo de De/Para
 *
 * @author alisson.souza@ctis.com.br
 */

@LocalBean
@Stateless
public class TipoDeParaDAO extends GenericoDAO<Long, TipoDePara> {

  private static final Logger LOG = LoggerFactory.getLogger(TipoDeParaDAO.class);

  protected static final String TIPO_DE_PARA_REMOVIDA = "Tipo de De/Para Removido";
  protected static final String TIPO_DE_PARA_CRIADA = "Tipo de De/Para Criado";
  protected static final String ATIVO = "Ativo";
  protected static final String INATIVO = "Inativo";
  protected static final String CAMPO_STATUS = "status";
  protected static final String SIM = "Sim";
  protected static final String NAO = "Não";

  @EJB
  private UsuarioDAO usuarioDAO;

  /**
   * Retorna uma lista de {@link TipoDePara} de acordo com o filtro passado.
   * 
   * @param filtroPesquisaTipoDePara Objeto do tipo {@link FiltroPesquisaTipoDeParaDTO} que contém
   *        os campos referentes ao filtro de TipoDePara.
   * @return {@link List<TipoDePara>} - Retorno da consulta.
   * @throws RegistroNaoEncontradoException Caso nenhum registro seja encontrado correspondente ao
   *         filtro passado.
   * @throws DAOException Caso ocorra algum erro inesperado na operação.
   */
  public List<TipoDePara> consultarSistema(FiltroPesquisaTipoDeParaDTO filtroPesquisaTipoDePara)
      throws DAOException, RegistroNaoEncontradoException {

    try {
      String orderBy = ColunaOrdenacaoTipoDeParaEnum
          .valueOf(filtroPesquisaTipoDePara.getOrderBy().toUpperCase()).getName();

      Integer paginaAtual = PaginacaoUtil.calcularPaginacaoAtual(
          filtroPesquisaTipoDePara.getNumeroPagina(), filtroPesquisaTipoDePara.getItensPorPagina());

      List<StatusEnum> statusList = EnumUtil.toStatusEnumList(filtroPesquisaTipoDePara.getStatus());

      List<TipoDePara> resultado = EntityQuery.create(getEntityManager(), TipoDePara.class)
          .like(TipoDePara_.NOME, filtroPesquisaTipoDePara.getNome())
          .in(TipoDePara_.STATUS, statusList)
          .addOrderBy(orderBy, filtroPesquisaTipoDePara.getTipoOrdenacao())
          .setFirstResult(paginaAtual).setMaxResults(filtroPesquisaTipoDePara.getItensPorPagina())
          .list();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_TIPOS_DE_PARA, e);
      throw new DAOException();
    }
  }

  /**
   * Retorna uma lista de {@link TipoDePara} de acordo com o filtro passado sem paginacao.
   * 
   * @param filtroPesquisaTipoDePara Objeto do tipo {@link FiltroPesquisaTipoDeParaDTO} que contém
   *        os campos referentes ao filtro de TipoDePara.
   * @return {@link List<TipoDePara>} - Retorno da consulta.
   * @throws RegistroNaoEncontradoException Caso nenhum registro seja encontrado correspondente ao
   *         filtro passado.
   * @throws DAOException Caso ocorra algum erro inesperado na operação.
   */
  public List<TipoDePara> consultarSistemaSemPaginacao(
      FiltroPesquisaTipoDeParaDTO filtroPesquisaTipoDePara)
      throws DAOException, RegistroNaoEncontradoException {

    try {
      String orderBy = ColunaOrdenacaoTipoDeParaEnum
          .valueOf(filtroPesquisaTipoDePara.getOrderBy().toUpperCase()).getName();

      List<StatusEnum> statusList = EnumUtil.toStatusEnumList(filtroPesquisaTipoDePara.getStatus());

      List<TipoDePara> resultado = EntityQuery.create(getEntityManager(), TipoDePara.class)
          .like(TipoDePara_.NOME, filtroPesquisaTipoDePara.getNome())
          .in(TipoDePara_.STATUS, statusList)
          .addOrderBy(orderBy, filtroPesquisaTipoDePara.getTipoOrdenacao()).list();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_TIPOS_DE_PARA, e);
      throw new DAOException();
    }
  }

  /**
   * Retorna o total de registros de um determinado tipo no banco.
   * 
   * @param filtroPesquisaTipoDePara Objeto do tipo {@link FiltroPesquisaTipoDeParaDTO} que contém
   *        os campos referentes ao filtro de TipoDePara.
   * @return O total de registro de um determinado tipo de acordo com os parametros passados
   * @throws DAOException Caso ocorra algum erro inesperado na operação.
   */
  public Integer consultarTotalRegistros(FiltroPesquisaTipoDeParaDTO filtroPesquisaTipoDePara)
      throws DAOException {

    try {
      List<StatusEnum> statusList = EnumUtil.toStatusEnumList(filtroPesquisaTipoDePara.getStatus());

      return EntityQuery.createCount(getEntityManager(), TipoDePara.class).distinct()
          .like(TipoDePara_.NOME, filtroPesquisaTipoDePara.getNome())
          .in(TipoDePara_.STATUS, statusList).count().intValue();

    } catch (PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS, e);
      throw new DAOException();
    }
  }

  /**
   * 
   * Verifica se já existe um TipoDePara cadastrado com o nome informado.[
   * 
   * @param nome - Nome do TipoDePara
   * @throws PersistenceException DAOException
   * 
   */
  public Boolean verificarExitenciaTipoDePara(String nome) throws DAOException {
    try {
      StringBuilder jpql =
          new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
              .append(" FROM TipoDePara tipo_de_para where UPPER(tipo_de_para.nome) = :nome");

      return getEntityManager().createQuery(jpql.toString(), Boolean.class)
          .setParameter(PARAM_NOME, nome.toUpperCase()).getSingleResult();

    } catch (PersistenceException e) {
      LOG.error("Erro ao verificar se existe TipoDePara: ", e);
      throw new DAOException();
    }
  }

  /**
   * Consultar selecao.
   *
   * @return the list
   */
  public List<SelecaoDTO> consultarSelecao() {
    try {

      StringBuilder builder = new StringBuilder();
      builder.append(" SELECT NEW br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO(tdp.id, tdp.nome) ");
      builder.append(" FROM TipoDePara tdp ORDER BY tdp.nome ASC ");

      return getEntityManager().createQuery(builder.toString(), SelecaoDTO.class).getResultList();

    } catch (Exception e) {
      return Collections.emptyList();
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
  public List<EnversDTO> buscarHistoricoRevisoes(FiltroPesquisaLogTipoDeParaDTO filtro,
      String[] atributos) throws RegistroNaoEncontradoException, DAOException {
    List<EnversDTO> listaDeRevisoes = new ArrayList<>();
    setAuditReader(AuditReaderFactory.get(getEntityManager()));

    TipoDePara entidade = new TipoDePara();
    List<DadosTipodeParaAudDTO> revisoesPorId = consultarListaEntidades(filtro);
    TipoDePara revisaoObjetoTemporario = null;

    try {
      iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario, entidade,
          atributos);

    } catch (Exception e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_TIPOS_DE_PARA, e);
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
  public List<DadosTipodeParaAudDTO> consultarListaEntidades(FiltroPesquisaLogTipoDeParaDTO filtro)
      throws RegistroNaoEncontradoException {

    StringBuilder jpql = new StringBuilder();
    jpql.append("SELECT");
    jpql.append(" NEW br.gov.pbh.prodabel.hubsmsa.dto.tipodepara.DadosTipodeParaAudDTO(");
    jpql.append(" tipoDeParaAud.id,");
    jpql.append(" revisao,");
    jpql.append(" tipoDeParaAud.revType,");
    jpql.append(" tipoDeParaAud.descricao,");
    jpql.append(" tipoDeParaAud.formaCadastro,");
    jpql.append(" tipoDeParaAud.nome,");
    jpql.append(" tipoDeParaAud.status,");
    jpql.append(" revisao.dtRevisao,");
    jpql.append(" revisao.matricula)");
    jpql.append(" FROM TipoDeParaAud tipoDeParaAud, Revision revisao");
    jpql.append(" WHERE tipoDeParaAud.revisao = revisao.id");

    if (filtro.getIdTipoDePara() != null) {
      jpql.append(" AND tipoDeParaAud.id = " + filtro.getIdTipoDePara());
    }

    if (filtro.getNome() != null) {
      jpql.append(" AND UPPER(unaccent(tipoDeParaAud.nome)) LIKE UPPER(unaccent('%")
          .append(filtro.getNome()).append("%'))");
    }

    if (filtro.getDataInicial() != null) {
      jpql.append(" AND revisao.dtRevisao >='").append(filtro.getDataInicial()).append("'");
    }

    if (filtro.getDataFinal() != null) {
      jpql.append(" AND revisao.dtRevisao<'").append(filtro.getDataFinal()).append("'");
    }

    jpql.append(" ORDER BY tipoDeParaAud.revisao DESC");

    List<DadosTipodeParaAudDTO> resultado = getEntityManager()
        .createQuery(jpql.toString(), DadosTipodeParaAudDTO.class).getResultList();

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
  public void iterarSobreRevisoes(List<DadosTipodeParaAudDTO> revisoesPorIdList,
      List<EnversDTO> listaDeRevisoes,
      TipoDePara revisaoObjetoTemporario, TipoDePara entidade, String... atributosValidos)
      throws IllegalAccessException, CloneNotSupportedException, InvocationTargetException,
      NoSuchMethodException {

    HashMap<Long, Object> mapObjetosAnteriores = new HashMap<>();

    for (DadosTipodeParaAudDTO revisoesPorId : revisoesPorIdList) {

      TipoDePara objetoRevisao = new TipoDePara();
      objetoRevisao.setId(revisoesPorId.getId());
      Revision rev = revisoesPorId.getRevisao();
      RevisionType revType = revisoesPorId.getRevType();
      objetoRevisao.setDescricao(revisoesPorId.getDescricao());
      objetoRevisao.setFormaCadastro(revisoesPorId.getFormaCadastro());
      objetoRevisao.setNome(revisoesPorId.getNome());
      objetoRevisao.setStatus(revisoesPorId.getStatus());

      revisaoObjetoTemporario = (TipoDePara) mapObjetosAnteriores.get(objetoRevisao.getId());

      if (revType != RevisionType.ADD && revisaoObjetoTemporario == null) {
        revisaoObjetoTemporario = consultarRevisaoAnterior(objetoRevisao.getId(), rev.getId());
      }

      Map<String, Object> listaCamposAlterados = new HashMap<>();

      if (revType == RevisionType.DEL) {
        for (String attr : atributosValidos) {
          listaCamposAlterados.put(attr, TIPO_DE_PARA_REMOVIDA);
        }
      } else {
        listaCamposAlterados =
            consultarValorAlterado(revisaoObjetoTemporario, objetoRevisao, atributosValidos);
      }

      for (Entry<String, Object> itemAlterado : listaCamposAlterados.entrySet()) {
        EnversDTO revisaoDTO = mapearRevisaoDTO(itemAlterado, revType, rev, entidade,
            objetoRevisao.getId(), revisaoObjetoTemporario);
        formatarValores(revisaoDTO);

        listaDeRevisoes.add(revisaoDTO);
      }

      mapObjetosAnteriores.put(objetoRevisao.getId(), objetoRevisao);
    }

  }

  /**
   * Buscar detalhe revisao.
   *
   * @param id the id
   * @param atributos the atributos
   * @return the list
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<EnversDTO> buscarDetalheRevisao(Long id, String[] atributos)
      throws RegistroNaoEncontradoException {
    List<EnversDTO> listaDeRevisoes = new ArrayList<>();

    try {

      TipoDePara tipoDePara = (TipoDePara) consultarEntidadePorIdRevisao(TipoDePara.class, id);

      FiltroPesquisaLogTipoDeParaDTO filtroPesquisa = new FiltroPesquisaLogTipoDeParaDTO();
      filtroPesquisa.setIdTipoDePara(tipoDePara.getId());

      List<DadosTipodeParaAudDTO> revisoesPorId = consultarListaEntidades(filtroPesquisa);
      TipoDePara revisaoObjetoTemporario = null;

      iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario, tipoDePara,
          atributos);

    } catch (Exception e) {
      LOG.error(ERRO_AO_CONSULTAR_OS_TIPOS_DE_PARA, e);
      throw new RegistroNaoEncontradoException();
    }

    return listaDeRevisoes.stream().filter(e -> e.getIdRevision().equals(id))
        .collect(Collectors.toList());
  }

  /**
   * Mapper.
   *
   * @param Método monta o DTO Historico. Criado na classe DAO devido aos objetos Revision e
   *        RevisionType do Hibernate que são visto apenas na camada persistence.
   * @return the list
   */
  public List<HistoricoTipoDeParaDTO> montarHistorico(
      List<DadosTipodeParaAudDTO> tipoDeParasAudList) {
    List<HistoricoTipoDeParaDTO> tipoDeParasAudDto = new ArrayList<>();

    for (DadosTipodeParaAudDTO itemRevisao : tipoDeParasAudList) {

      HistoricoTipoDeParaDTO historico = new HistoricoTipoDeParaDTO();

      historico.setDescricao(itemRevisao.getDescricao());
      historico.setIdTipoDePara(itemRevisao.getId());
      historico.setNome(itemRevisao.getNome());
      historico.setStatus(itemRevisao.getStatus().getName());
      historico.setLogin(itemRevisao.getRevisao().getMatricula());
      historico.setRevisao(String.valueOf(itemRevisao.getRevisao().getId()));
      historico.setDataEvento(
          TimeUtil.convertDataToStr(itemRevisao.getRevisao().getDtRevisao(), "dd/MM/yyyy HH:mm"));
      historico.setTipoRevisao(formataTipoRevisao(itemRevisao.getRevType()));
      tipoDeParasAudDto.add(historico);

    }
    return tipoDeParasAudDto;
  }

  /**
   * Mapear removido.
   *
   * @param idContatoEmpresa the id contato empresa
   * @param idRevisaoAtual the id revisao
   * @return the list
   */
  private TipoDePara consultarRevisaoAnterior(Long idTipoDePara, Long idRevisaoAtual) {

    final String orderBy = ContatoEmpresaAud_.REVISAO;

    TipoDeParaAud tipoDeParaAud = EntityQuery.create(getEntityManager(), TipoDeParaAud.class)
        .objectEqualsTo(TipoDeParaAud_.ID, idTipoDePara)
        .objectNotEqualsTo(TipoDeParaAud_.REVISAO, idRevisaoAtual).addOrderBy(orderBy, "DESC")
        .setMaxResults(1).uniqueResult();

    TipoDePara tipoDePara = new TipoDePara();
    tipoDePara.setId(tipoDeParaAud.getId());
    tipoDePara.setDescricao(tipoDeParaAud.getDescricao());
    tipoDePara.setFormaCadastro(tipoDeParaAud.getFormaCadastro());
    tipoDePara.setNome(tipoDeParaAud.getNome());
    tipoDePara.setStatus(tipoDeParaAud.getStatus());

    return tipoDePara;

  }

  /**
   * Formatar valores.
   *
   * @param revisaoDTO the revisao DTO
   */
  private void formatarValores(EnversDTO revisaoDTO) {

    if (revisaoDTO.getRevisionType() == RevisionType.ADD)
      revisaoDTO.setOldValue(TIPO_DE_PARA_CRIADA);
    if (revisaoDTO.getRevisionType() == RevisionType.DEL)
      revisaoDTO.setValue(TIPO_DE_PARA_REMOVIDA);

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


}
