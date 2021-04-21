package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.PARAM_EMAIL;
import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.PARAM_ID;
import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.PARAM_NOME;
import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.PATH_EMPRESA_ID;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_AS_EMPRESAS;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_CONTATO_EMPRESA;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.DadosContatoEmpresaAudDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.FiltroPesquisaContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.FiltroPesquisaLogContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoContatoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoContatoEmpresaEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresa_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.ContatoEmpresaAud;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.ContatoEmpresaAud_;
import br.gov.pbh.prodabel.hubsmsa.util.EnumUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TextFormatUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;
import br.gov.pbh.prodabel.hubsmsa.util.query.EntityQuery;

// TODO: Auto-generated Javadoc
/**
 * Classe DAO responsável por manipular os dados de ContatoEmpresa
 *
 * @author andre.moreira@ctis.com.br
 */
@LocalBean
@Stateless
public class ContatoEmpresaDAO extends GenericoDAO<Long, ContatoEmpresa> {

  private static final String PARAM_ID_EMPRESA = "idEmpresa";
  private static final Logger LOG = LoggerFactory.getLogger(ContatoEmpresaDAO.class);

  protected static final String CONTATO_EMPRESA_REMOVIDA = "Contato Empresa Removido";
  protected static final String CONTATO_EMPRESA_CRIADA = "Contato Empresa Criado";
  protected static final String CAMPO_STATUS = "status";
  protected static final String SIM = "Sim";
  protected static final String NAO = "Não";
  protected static final String ATIVO = "Ativo";
  protected static final String INATIVO = "Inativo";

  @EJB
  private UsuarioDAO usuarioDAO;

  /**
   * Retorna uma lista de {@link ContatoEmpresa} de acordo com o filtro passado.
   *
   * @param filtroPesquisaContatoEmpresa Objeto do tipo {@link FiltroPesquisaContatoEmpresaDTO} que
   *        contém os campos referentes ao filtro de ContatoEmpresa.
   * @return {@link List<ContatoEmpresa>} - Retorno da consulta.
   * @throws RegistroNaoEncontradoException Caso nenhum registro seja encontrado correspondente ao
   *         filtro passado.
   * @throws DAOException Caso ocorra algum erro inesperado na operação.
   */
  public List<ContatoEmpresa> consultarContatoEmpresa(
      final FiltroPesquisaContatoEmpresaDTO filtroPesquisaContatoEmpresa)
      throws DAOException, RegistroNaoEncontradoException {

    try {

      final String orderBy = ColunaOrdenacaoContatoEmpresaEnum
          .valueOf(filtroPesquisaContatoEmpresa.getOrderBy().toUpperCase()).getName();

      final Integer paginaAtual =
          PaginacaoUtil.calcularPaginacaoAtual(filtroPesquisaContatoEmpresa.getNumeroPagina(),
              filtroPesquisaContatoEmpresa.getItensPorPagina());

      final List<StatusEnum> statusList =
          EnumUtil.toStatusEnumList(filtroPesquisaContatoEmpresa.getStatus());

      final List<ContatoEmpresa> resultado = EntityQuery
          .create(getEntityManager(), ContatoEmpresa.class).innerJoinFetch(ContatoEmpresa_.EMPRESA)
          .objectEqualsTo(PATH_EMPRESA_ID, filtroPesquisaContatoEmpresa.getIdEmpresa())
          .like(ContatoEmpresa_.NOME, filtroPesquisaContatoEmpresa.getNome())
          .like(ContatoEmpresa_.EMAIL, filtroPesquisaContatoEmpresa.getEmail())
          .like(ContatoEmpresa_.TELEFONE, filtroPesquisaContatoEmpresa.getTelefone())
          .in(ContatoEmpresa_.STATUS, statusList)
          .addOrderBy(orderBy, filtroPesquisaContatoEmpresa.getTipoOrdenacao())
          .setFirstResult(paginaAtual)
          .setMaxResults(filtroPesquisaContatoEmpresa.getItensPorPagina()).list();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_CONTATO_EMPRESA, e);
      throw new DAOException();
    }
  }

  /**
   * Retorna um contatoEmpresa baseado no id passado com sua relação com {@link Empresa}.
   *
   * @param id id referente ao contatoEmpresa que deseja-se recuperar.
   * @return {@link ContatoEmpresa} - Retorna ContatoEmpresa correspondente ao id passado.
   * @throws DAOException Caso ocorra algum erro inesperado na operação.
   */
  public ContatoEmpresa consultaContatoEmpresaPorId(final Long id) throws DAOException {
    try {

      return EntityQuery.create(getEntityManager(), ContatoEmpresa.class)
          .innerJoinFetch(ContatoEmpresa_.EMPRESA).objectEqualsTo(ContatoEmpresa_.ID, id)
          .uniqueResult();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_CONTATO_EMPRESA, e);
      throw new DAOException();
    }

  }

  /**
   * Retorna o total de registros de um determinado tipo no banco.
   *
   * @param filtroPesquisaContatoEmpresa Objeto do tipo {@link FiltroPesquisaContatoEmpresaDTO} que
   *        contém os campos referentes ao filtro de ContatoEmpresa.
   * @return O total de registro de um determinado tipo de acordo com os parametros passados
   * @throws DAOException Caso ocorra algum erro inesperado na operação.
   */
  public Integer consultarTotalRegistros(
      final FiltroPesquisaContatoEmpresaDTO filtroPesquisaContatoEmpresa) throws DAOException {

    try {

      final List<StatusEnum> statusList =
          EnumUtil.toStatusEnumList(filtroPesquisaContatoEmpresa.getStatus());

      return EntityQuery.createCount(getEntityManager(), ContatoEmpresa.class).distinct()
          .innerJoin(ContatoEmpresa_.EMPRESA)
          .objectEqualsTo(PATH_EMPRESA_ID, filtroPesquisaContatoEmpresa.getIdEmpresa())
          .like(ContatoEmpresa_.NOME, filtroPesquisaContatoEmpresa.getNome())
          .like(ContatoEmpresa_.EMAIL, filtroPesquisaContatoEmpresa.getEmail())
          .like(ContatoEmpresa_.TELEFONE, filtroPesquisaContatoEmpresa.getTelefone())
          .in(ContatoEmpresa_.STATUS, statusList).count().intValue();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_CONTATO_EMPRESA, e);
      throw new DAOException();
    }
  }

  /**
   *
   * @param email
   * @return
   * @throws DAOException
   */
  public boolean verificarEmailExiste(final String email) throws DAOException {

    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END").append(
            " FROM ContatoEmpresa contatoEmpresa INNER JOIN Empresa empresa ON contatoEmpresa.empresa.id = empresa.id")
            .append(" AND UPPER(contatoEmpresa.email) = :email");

    try {

      return getEntityManager().createQuery(jpql.toString(), Boolean.class)
          .setParameter(PARAM_EMAIL, email.toUpperCase()).getSingleResult();

    } catch (final PersistenceException e) {
      LOG.error("Erro ao verificar se existe ContatoEmpresa com mesmo nome: ", e);
      throw new DAOException();
    }
  }

  /**
   *
   * @param nome
   * @return
   * @throws DAOException
   */
  public boolean verificarNomeExiste(final String nome, final Long idEmpresa) throws DAOException {

    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END").append(
            " FROM ContatoEmpresa contatoEmpresa INNER JOIN Empresa empresa ON contatoEmpresa.empresa.id = empresa.id")
            .append(" AND UPPER(contatoEmpresa.nome) = :nome")
            .append(" AND contatoEmpresa.empresa.id = :idEmpresa");

    try {

      return getEntityManager().createQuery(jpql.toString(), Boolean.class)
          .setParameter(PARAM_NOME, nome.toUpperCase()).setParameter(PARAM_ID_EMPRESA, idEmpresa)
          .getSingleResult();

    } catch (final PersistenceException e) {
      LOG.error("Erro ao verificar se existe ContatoEmpresa com mesmo nome: ", e);
      throw new DAOException();
    }
  }

  /**
   *
   * @param email
   * @param id
   * @return
   * @throws DAOException
   */
  public boolean verificarEmailExiste(final String email, final Long id) throws DAOException {

    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END").append(
            " FROM ContatoEmpresa contatoEmpresa INNER JOIN Empresa empresa ON contatoEmpresa.empresa.id = empresa.id")
            .append(" AND UPPER(contatoEmpresa.email) = :email AND contatoEmpresa.id != :id");

    try {

      return getEntityManager().createQuery(jpql.toString(), Boolean.class)
          .setParameter(PARAM_EMAIL, email.toUpperCase()).setParameter(PARAM_ID, id)
          .getSingleResult();

    } catch (final PersistenceException e) {
      LOG.error("Erro ao verificar se existe ContatoEmpresa com mesmo nome: ", e);
      throw new DAOException();
    }
  }

  /**
   *
   * @param nome
   * @param id
   * @return
   * @throws DAOException
   */
  public boolean verificarNomeExiste(final String nome, final Long idEmpresa, final Long id)
      throws DAOException {

    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END").append(
            " FROM ContatoEmpresa contatoEmpresa INNER JOIN Empresa empresa ON contatoEmpresa.empresa.id = empresa.id")
            .append(" AND  UPPER(contatoEmpresa.nome) = :nome")
            .append(" AND contatoEmpresa.id != :id ")
            .append(" AND contatoEmpresa.empresa.id = :idEmpresa");

    try {

      return getEntityManager().createQuery(jpql.toString(), Boolean.class)
          .setParameter(PARAM_NOME, nome.toUpperCase()).setParameter(PARAM_ID, id)
          .setParameter(PARAM_ID_EMPRESA, idEmpresa).getSingleResult();

    } catch (final PersistenceException e) {
      LOG.error("Erro ao verificar se existe ContatoEmpresa com mesmo nome: ", e);
      throw new DAOException();
    }
  }

  /**
   *
   * @param idEmpresa
   * @throws DAOException
   */
  public void excluirContatosDessaEmpresa(final Long idEmpresa) throws DAOException {
    try {
      final StringBuilder querySb =
          new StringBuilder("DELETE FROM ContatoEmpresa WHERE  empresa.id = :id");

      getEntityManager().createQuery(querySb.toString()).setParameter(PARAM_ID, idEmpresa)
          .executeUpdate();

    } catch (final PersistenceException e) {
      LOG.error("Erro ao verificar se existe ContatoEmpresa com mesmo nome: ", e);
    }
  }

  /**
   * Consultar contatos da empresa.
   *
   * @param idEmpresa the id empresa
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<SelecaoDTO> consultarContatosDaEmpresa(Long idEmpresa)
      throws DAOException, RegistroNaoEncontradoException {
    try {
      final StringBuilder builder = new StringBuilder();
      builder.append("SELECT new br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO(c.id, c.nome) ");
      builder.append("FROM ContatoEmpresa c WHERE c.empresa.id = : idEmpresa ");

      final List<SelecaoDTO> resultado =
          getEntityManager().createQuery(builder.toString(), SelecaoDTO.class)
              .setParameter("idEmpresa", idEmpresa).getResultList();

      return resultado;

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_CONTATO_EMPRESA, e);
      throw new DAOException();
    }
  }

  /**
   * Retorna uma lista de todos os registros de {@link ContatoEmpresa}, sem paginação, de acordo com
   * o filtro passado.
   *
   * @param filtroPesquisaContatoEmpresa Objeto do tipo {@link FiltroPesquisaContatoEmpresaDTO} que
   *        contém os campos referentes ao filtro de ContatoEmpresa.
   * @return {@link List<ContatoEmpresa>} - Retorno da consulta.
   * @throws RegistroNaoEncontradoException Caso nenhum registro seja encontrado correspondente ao
   *         filtro passado.
   * @throws DAOException Caso ocorra algum erro inesperado na operação.
   */
  public List<ContatoEmpresa> consultarContatoEmpresaSemPaginacao(
      final FiltroPesquisaContatoEmpresaDTO filtroPesquisaContatoEmpresa)
      throws DAOException, RegistroNaoEncontradoException {

    try {

      final String orderBy = ColunaOrdenacaoContatoEmpresaEnum
          .valueOf(filtroPesquisaContatoEmpresa.getOrderBy().toUpperCase()).getName();

      final List<StatusEnum> statusList =
          EnumUtil.toStatusEnumList(filtroPesquisaContatoEmpresa.getStatus());

      final List<ContatoEmpresa> resultado = EntityQuery
          .create(getEntityManager(), ContatoEmpresa.class).innerJoinFetch(ContatoEmpresa_.EMPRESA)
          .objectEqualsTo(PATH_EMPRESA_ID, filtroPesquisaContatoEmpresa.getIdEmpresa())
          .like(ContatoEmpresa_.NOME, filtroPesquisaContatoEmpresa.getNome())
          .like(ContatoEmpresa_.EMAIL, filtroPesquisaContatoEmpresa.getEmail())
          .like(ContatoEmpresa_.TELEFONE, filtroPesquisaContatoEmpresa.getTelefone())
          .in(ContatoEmpresa_.STATUS, statusList)
          .addOrderBy(orderBy, filtroPesquisaContatoEmpresa.getTipoOrdenacao()).list();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_CONTATO_EMPRESA, e);
      throw new DAOException();
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
  public List<EnversDTO> buscarHistoricoRevisoes(FiltroPesquisaLogContatoEmpresaDTO filtro,
      String[] atributos) throws RegistroNaoEncontradoException, DAOException {
    List<EnversDTO> listaDeRevisoes = new ArrayList<>();
    setAuditReader(AuditReaderFactory.get(getEntityManager()));

    ContatoEmpresa entidade = new ContatoEmpresa();

    List<DadosContatoEmpresaAudDTO> revisoesPorId = consultarListaEntidades(filtro);

    ContatoEmpresa revisaoObjetoTemporario = null;

    try {
      iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario, entidade,
          atributos);
    } catch (Exception e) {
      LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
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
  public List<DadosContatoEmpresaAudDTO> consultarListaEntidades(
      FiltroPesquisaLogContatoEmpresaDTO filtro)
      throws RegistroNaoEncontradoException {

    StringBuilder jpql = new StringBuilder(
        "SELECT " + "new br.gov.pbh.prodabel.hubsmsa.dto.contatoEmpresa.DadosContatoEmpresaAudDTO ("
            + " contEmprAud.id," + " contEmprAud.revisao,"
        + " contEmprAud.revType," + " contEmprAud.email," + " contEmprAud.nome,"
        + " contEmprAud.telefone," + " contEmprAud.setor," + " contEmprAud.status,"
        + " (SELECT empresaAud.nomeFantasia FROM EmpresaAud empresaAud"
        + "    WHERE empresaAud.revisao = (SELECT MAX(empresaAud2.revisao) "
        + "    FROM EmpresaAud empresaAud2 WHERE empresaAud2.id = contEmprAud.empresa)),"
            + " rev.dtRevisao," + " rev.matricula )")
                .append(" FROM ContatoEmpresaAud contEmprAud," + " Revision rev"
                + " WHERE contEmprAud.revisao = rev.id");

    if (filtro.getId() != null) {
      jpql.append(" AND contEmprAud.id = " + filtro.getId());
    }

    if (filtro.getNome() != null) {
      jpql.append(" AND UPPER(unaccent(contEmprAud.nome)) LIKE UPPER(unaccent('%")
          .append(filtro.getNome()).append("%'))");
    }

    if (filtro.getIdEmpresa() != null) {
      jpql.append(" AND contEmprAud.empresa.id = ").append(filtro.getIdEmpresa());
    }

    if (filtro.getDataInicial() != null) {
      jpql.append(" AND rev.dtRevisao >='").append(filtro.getDataInicial()).append("'");
    }

    if (filtro.getDataFinal() != null) {
      jpql.append(" AND rev.dtRevisao<'").append(filtro.getDataFinal()).append("'");
    }

    jpql.append(" order by contEmprAud.revisao desc");

    List<DadosContatoEmpresaAudDTO> resultado = getEntityManager()
        .createQuery(jpql.toString(), DadosContatoEmpresaAudDTO.class).getResultList();

    return ValidadorUtil.validarNoResultList(resultado);
  }

  /**
   * Iterar sobre revisoes.
   *
   * @param revisoesPorId the revisoes por id
   * @param listaDeRevisoes the lista de revisoes
   * @param revisaoObjetoTemporario the revisao objeto temporario
   * @param entidade the entidade
   * @param id the id
   * @param atributosValidos the atributos validos
   * @throws IllegalAccessException the illegal access exception
   * @throws CloneNotSupportedException the clone not supported exception
   * @throws InvocationTargetException the invocation target exception
   * @throws NoSuchMethodException the no such method exception
   */
  public void iterarSobreRevisoes(List<DadosContatoEmpresaAudDTO> revisoesPorId,
      List<EnversDTO> listaDeRevisoes,
      ContatoEmpresa revisaoObjetoTemporario, ContatoEmpresa entidade, String... atributosValidos)
      throws IllegalAccessException, CloneNotSupportedException, InvocationTargetException,
      NoSuchMethodException {

    HashMap<Long, Object> mapObjetosAnteriores = new HashMap<>();

    for (int i = 0; i < revisoesPorId.size(); i++) {
      DadosContatoEmpresaAudDTO itemRevisao = revisoesPorId.get(i);
      ContatoEmpresa objetoRevisao = new ContatoEmpresa();
      objetoRevisao.setId(itemRevisao.getId());
      Revision rev = itemRevisao.getRevisao();
      RevisionType revType = itemRevisao.getRevType();
      objetoRevisao.setEmail(itemRevisao.getEmail());
      objetoRevisao.setNome(itemRevisao.getNome());
      objetoRevisao.setTelefone(itemRevisao.getTelefone());
      objetoRevisao.setSetor(itemRevisao.getSetor());
      objetoRevisao.setStatus(itemRevisao.getStatus());
      objetoRevisao.setNomeEmpresa(itemRevisao.getNomeEmpresa());

      revisaoObjetoTemporario = (ContatoEmpresa) mapObjetosAnteriores.get(objetoRevisao.getId());
      
      if (revType != RevisionType.ADD && revisaoObjetoTemporario == null) {
        revisaoObjetoTemporario = consultarRevisaoAnterior(objetoRevisao.getId(), rev.getId());
      }

      Map<String, Object> listaCamposAlterados = new HashMap<>();

      if (revType == RevisionType.DEL) {
        for (String attr : atributosValidos) {
          listaCamposAlterados.put(attr, CONTATO_EMPRESA_REMOVIDA);
        }
      } else {
        listaCamposAlterados =
            consultarValorAlterado(revisaoObjetoTemporario, objetoRevisao, atributosValidos);
      }

      for (Entry<String, Object> itemAlterado : listaCamposAlterados.entrySet()) {
        EnversDTO revisaoDTO = mapearRevisaoDTO(itemAlterado, revType, rev, entidade,
            objetoRevisao.getId(), revisaoObjetoTemporario);
        revisaoDTO.setNomeEmpresa(itemRevisao.getNomeEmpresa());
        formatarValores(revisaoDTO);

        listaDeRevisoes.add(revisaoDTO);
      }

      mapObjetosAnteriores.put(objetoRevisao.getId(), objetoRevisao);
    }

  }

  /**
   * Consultar revisao anterior.
   *
   * @param idContatoEmpresa the id contato empresa
   * @param idRevisaoAtual the id revisao
   * @return the list
   */
  private ContatoEmpresa consultarRevisaoAnterior(Long idContatoEmpresa, Long idRevisaoAtual) {


    final String orderBy = ContatoEmpresaAud_.REVISAO;

      ContatoEmpresaAud contatoEmpresaAud =
          EntityQuery.create(getEntityManager(), ContatoEmpresaAud.class)
              .innerJoinFetch(ContatoEmpresaAud_.EMPRESA)
      .objectEqualsTo(ContatoEmpresaAud_.ID, idContatoEmpresa)
        .objectNotEqualsTo(ContatoEmpresaAud_.REVISAO, idRevisaoAtual).addOrderBy(orderBy, "DESC")
      .setMaxResults(1).uniqueResult();

      ContatoEmpresa contatoEmpresa = new ContatoEmpresa();
      contatoEmpresa.setEmail(contatoEmpresaAud.getEmail());
      contatoEmpresa.setId(contatoEmpresaAud.getId());
      contatoEmpresa.setNome(contatoEmpresaAud.getNome());
      contatoEmpresa.setNomeEmpresa(contatoEmpresaAud.getEmpresa().getNomeFantasia());
      contatoEmpresa.setSetor(contatoEmpresaAud.getSetor());
      contatoEmpresa.setStatus(contatoEmpresaAud.getStatus());
      contatoEmpresa.setTelefone(contatoEmpresaAud.getTelefone());

      return contatoEmpresa;

  }

  /**
   * Formatar valores.
   *
   * @param revisaoDTO the revisao DTO
   */
  private void formatarValores(EnversDTO revisaoDTO) {

    if (revisaoDTO.getField().equals(ContatoEmpresa_.TELEFONE)) {
      revisaoDTO.setValue(TextFormatUtil.formatarTelefone(revisaoDTO.getValue()));
      revisaoDTO.setOldValue(TextFormatUtil.formatarTelefone(revisaoDTO.getOldValue()));
    }

    if (revisaoDTO.getRevisionType() == RevisionType.ADD)
      revisaoDTO.setOldValue(CONTATO_EMPRESA_CRIADA);
    if (revisaoDTO.getRevisionType() == RevisionType.DEL)
      revisaoDTO.setValue(CONTATO_EMPRESA_REMOVIDA);

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
   * Buscar detalhe revisao.
   *
   * @param id the id
   * @param atributos the atributos
   * @return the list
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<EnversDTO> buscarDetalheRevisao(Long id, String[] atributos,
      FiltroPesquisaLogContatoEmpresaDTO filtroPesquisa) throws RegistroNaoEncontradoException {

    List<EnversDTO> listaDeRevisoes = new ArrayList<>();

    try {
      ContatoEmpresa entidade = new ContatoEmpresa();

      ContatoEmpresa contatoEmpresa =
          (ContatoEmpresa) consultarEntidadePorIdRevisao(ContatoEmpresa.class, id);

      filtroPesquisa.setId(contatoEmpresa.getId());

      List<DadosContatoEmpresaAudDTO> revisoesPorId = consultarListaEntidades(filtroPesquisa);

      ContatoEmpresa revisaoObjetoTemporario = null;

      iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario, entidade,
          atributos);
    } catch (Exception e) {
      LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
      throw new RegistroNaoEncontradoException();
    }

    return listaDeRevisoes.stream().filter(e -> e.getIdRevision().equals(id))
        .collect(Collectors.toList());

  }

  /**
   * Montar historico.
   *
   * @param tipoDeParasAudList the tipo de paras aud list
   * @return the list
   */
  public List<HistoricoContatoEmpresaDTO> montarHistorico(
      List<DadosContatoEmpresaAudDTO> tipoDeParasAudList) {
    List<HistoricoContatoEmpresaDTO> tipoDeParasAudDto = new ArrayList<>();

    for (DadosContatoEmpresaAudDTO itemRevisao : tipoDeParasAudList) {

      HistoricoContatoEmpresaDTO historico = new HistoricoContatoEmpresaDTO();

      historico.setIdContatoEmpresa(itemRevisao.getId());
      historico.setNome(itemRevisao.getNome());
      historico.setEmail(itemRevisao.getEmail());
      historico.setTelefone(itemRevisao.getTelefone());
      historico.setSetor(itemRevisao.getSetor());
      historico.setNomeEmpresa(itemRevisao.getNomeEmpresa());
      historico.setStatus(itemRevisao.getStatus().getName());
      historico.setLogin(itemRevisao.getMatricula());
      historico.setRevisao(String.valueOf(itemRevisao.getRevisao().getId()));
      historico.setDataEvento(
          TimeUtil.convertDataToStr(itemRevisao.getDtRevisao(), "dd/MM/yyyy HH:mm"));
      historico.setTipoRevisao(formataTipoRevisao(itemRevisao.getRevType()));
      tipoDeParasAudDto.add(historico);

    }
    return tipoDeParasAudDto;
  }

}
