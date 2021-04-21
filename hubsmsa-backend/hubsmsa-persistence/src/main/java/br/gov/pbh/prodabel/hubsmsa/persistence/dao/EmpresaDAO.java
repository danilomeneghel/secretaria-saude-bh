package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.PARAM_ID;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_AS_EMPRESAS;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_VERIFICAR_CNES;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_VERIFICAR_CNPJ;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_VERIFICAR_NOME_EMPRESARIAL;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_VERIFICAR_NOME_FANTASIA;
import java.lang.reflect.InvocationTargetException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.DadosEmpresaAudDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.FiltroPesquisaEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.empresa.FiltroPesquisaLogEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoEmpresaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoEmpresaEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.ContatoEmpresaAud_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.EmpresaAud;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.EmpresaAud_;
import br.gov.pbh.prodabel.hubsmsa.util.EnumUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TextFormatUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;
import br.gov.pbh.prodabel.hubsmsa.util.query.EntityQuery;

// TODO: Auto-generated Javadoc
@LocalBean
@Stateless
public class EmpresaDAO extends GenericoDAO<Long, Empresa> {

  private static final Logger LOG = LoggerFactory.getLogger(EmpresaDAO.class);

  protected static final String EMPRESA_REMOVIDA = "Empresa Removida";
  protected static final String EMPRESA_CRIADA = "Empresa Criada";
  protected static final String ATIVO = "Ativo";
  protected static final String INATIVO = "Inativo";
  protected static final String CAMPO_CNPJ = "cnpj";
  protected static final String CAMPO_ATIVO = "ativo";
  protected static final String SIM = "Sim";
  protected static final String NAO = "Não";

  @EJB
  private UsuarioDAO usuarioDAO;

  /**
   * Metodo responsavel por realizar a consulta ao banco de dados segundo um filtro de pesquisa.
   *
   * @param filtroPesquisa - Filtro de pequisa.
   * @return List<Empresa> - Lista de empresas que são equivalentes ao filtro de pesquisa.
   * @throws DAOException
   * @throws RegistroNaoEncontradoException
   */
  public List<Empresa> consultarEmpresa(final FiltroPesquisaEmpresaDTO filtroPesquisa)
      throws DAOException, RegistroNaoEncontradoException {

    try {
      final String orderBy =
          ColunaOrdenacaoEmpresaEnum.valueOf(filtroPesquisa.getOrderBy().toUpperCase()).getName();

      final Integer paginaAtual = PaginacaoUtil.calcularPaginacaoAtual(
          filtroPesquisa.getNumeroPagina(), filtroPesquisa.getItensPorPagina());

      final List<Empresa> resultado = EntityQuery.create(getEntityManager(), Empresa.class)
          .like(Empresa_.NOME_EMPRESARIAL, filtroPesquisa.getNomeEmpresarial())
          .like(Empresa_.NOME_FANTASIA, filtroPesquisa.getNomeFantasia())
          .stringEqualsTo(Empresa_.CNPJ, filtroPesquisa.getCnpj())
          .objectEqualsTo(Empresa_.CODIGO_CNES, filtroPesquisa.getCnes())
          .in(Empresa_.ATIVO, EnumUtil.toStatusEnumList(filtroPesquisa.getStatus()))
          .setFirstResult(paginaAtual).setMaxResults(filtroPesquisa.getItensPorPagina())
          .addOrderBy(orderBy, filtroPesquisa.getTipoOrdenacao()).list();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
      throw new DAOException();
    }

  }

  /**
   * Consultar empresa droplist.
   *
   * @param filtroPesquisa the filtro pesquisa
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<Empresa> consultarEmpresaDroplist(final FiltroPesquisaEmpresaDTO filtroPesquisa)
      throws DAOException, RegistroNaoEncontradoException {

    try {
      final String orderBy =
          ColunaOrdenacaoEmpresaEnum.valueOf(filtroPesquisa.getOrderBy().toUpperCase()).getName();

      final List<Empresa> resultado = EntityQuery.create(getEntityManager(), Empresa.class)
          .like(Empresa_.NOME_EMPRESARIAL, filtroPesquisa.getNomeEmpresarial())
          .like(Empresa_.NOME_FANTASIA, filtroPesquisa.getNomeFantasia())
          .stringEqualsTo(Empresa_.CNPJ, filtroPesquisa.getCnpj())
          .objectEqualsTo(Empresa_.CODIGO_CNES, filtroPesquisa.getCnes())
          .in(Empresa_.ATIVO, EnumUtil.toStatusEnumList(filtroPesquisa.getStatus()))
          .addOrderBy(orderBy, filtroPesquisa.getTipoOrdenacao()).list();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
      throw new DAOException();
    }

  }

  /**
   * Metodo responsavel por fazer a contagem de quantas Empresa tem que tem atributos iguais ao
   * filtro de pesquisa.
   *
   * @param filtroPesquisa - Filtro de Pesquisa
   * @return Integer - Quantidade de Empresas.
   * @throws DAOException - Caso tenha algum erro de persistencia na hora de realizar a consulta
   */
  public Integer consultarTotalRegistros(final FiltroPesquisaEmpresaDTO filtroPesquisa)
      throws DAOException {

    try {

      return EntityQuery.createCount(getEntityManager(), Empresa.class)
          .like(Empresa_.NOME_EMPRESARIAL, filtroPesquisa.getNomeEmpresarial())
          .like(Empresa_.NOME_FANTASIA, filtroPesquisa.getNomeFantasia())
          .stringEqualsTo(Empresa_.CNPJ, filtroPesquisa.getCnpj())
          .objectEqualsTo(Empresa_.CODIGO_CNES, filtroPesquisa.getCnes())
          .in(Empresa_.ATIVO, EnumUtil.toStatusEnumList(filtroPesquisa.getStatus())).count()
          .intValue();

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS, e);
      throw new DAOException();
    }

  }

  /**
   * Metodo responsavel por verificar se existe algum CNES que seja igual ao informado.
   *
   * @param cnesEmpresa - CNES da Empresa.
   * @return True , caso exista algum CNES e False caso contrario
   * @throws DAOException - Caso tenha algum erro de persistencia na hora de realizar a consulta
   */
  public boolean verificarCnesExistente(final Long cnesEmpresa) throws DAOException {
    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
            .append(" FROM Empresa empresa where empresa.codigoCnes").append(" = ")
            .append(":cnesEmpresa");

    final TypedQuery<Boolean> query =
        getEntityManager().createQuery(jpql.toString(), Boolean.class);
    query.setParameter("cnesEmpresa", cnesEmpresa);

    try {
      return query.getSingleResult();
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_VERIFICAR_CNES, e);
      throw new DAOException();
    }
  }

  /**
   * Metodo responsavel por verificar se existe algum CNPJ que seja igual ao informado.
   *
   * @param cnpjEmpresa - CNPJ da Empresa.
   * @return True , caso exista algum CNPJ e False caso contrario
   * @throws DAOException - Caso tenha algum erro de persistencia na hora de realizar a consulta
   */
  public boolean verificarCnpjExistente(final String cnpjEmpresa) throws DAOException {
    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
            .append(" FROM Empresa empresa where UPPER(empresa.cnpj)").append(" = ")
            .append(":cnpjEmpresa");

    final TypedQuery<Boolean> query =
        getEntityManager().createQuery(jpql.toString(), Boolean.class);
    query.setParameter("cnpjEmpresa", cnpjEmpresa);

    try {
      return query.getSingleResult();
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_VERIFICAR_CNPJ, e);
      throw new DAOException();
    }
  }

  /**
   * Metodo responsavel por verificar se existe algum Nome Fantasia que seja igual ao informado.
   *
   * @param nomeFantasiaEmpresa - Nome Fantasia da Empresa.
   * @return True , caso exista algum Nome Fantasia e False caso contrario
   * @throws DAOException - Caso tenha algum erro de persistencia na hora de realizar a consulta
   */
  public boolean verificarNomeFantasiaExistente(String nomeFantasiaEmpresa) throws DAOException {
    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
            .append(" FROM Empresa empresa where UPPER(empresa.nomeFantasia)").append(" = ")
            .append(":nomeFantasiaEmpresa");

    final TypedQuery<Boolean> query =
        getEntityManager().createQuery(jpql.toString(), Boolean.class);
    nomeFantasiaEmpresa = nomeFantasiaEmpresa.toUpperCase();
    query.setParameter("nomeFantasiaEmpresa", nomeFantasiaEmpresa);

    try {
      return query.getSingleResult();
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_VERIFICAR_NOME_FANTASIA, e);
      throw new DAOException();
    }
  }

  /**
   * Metodo responsavel por verificar se existe algum Nome Empresarial que seja igual ao informado.
   *
   * @param nomeEmpresarial - Nome Empresarial da Empresa.
   * @return True , caso exista algum Nome Empresarial e False caso contrario
   * @throws DAOException - Caso tenha algum erro de persistencia na hora de realizar a consulta
   */
  public boolean verificarNomeEmpresarialExistente(String nomeEmpresarial) throws DAOException {
    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
            .append(" FROM Empresa empresa where UPPER(empresa.nomeEmpresarial)").append(" = ")
            .append(":nomeEmpresarial");
    nomeEmpresarial = nomeEmpresarial.toUpperCase();
    final TypedQuery<Boolean> query =
        getEntityManager().createQuery(jpql.toString(), Boolean.class);
    query.setParameter(Empresa_.NOME_EMPRESARIAL, nomeEmpresarial);

    try {
      return query.getSingleResult();
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_VERIFICAR_NOME_EMPRESARIAL, e);
      throw new DAOException();
    }
  }

  /**
   * Metodo responsavel por verificar se existe algum CNES que seja igual ao informado e diferente
   * do Identificador.
   *
   * @param cnes- CNES da Empresa.
   * @param id - Identificador da empresa.
   * @return True , caso exista algum CNES e False caso contrario
   * @throws DAOException - Caso tenha algum erro de persistencia na hora de realizar a consulta
   */
  public boolean verificarCnesExistente(final Long cnes, final Long id) throws DAOException {
    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
            .append(" FROM Empresa empresa where empresa.codigoCnes").append(" = ")
            .append(":cnesEmpresa").append(" AND").append(" empresa.id").append(" !=")
            .append(":id");

    final TypedQuery<Boolean> query =
        getEntityManager().createQuery(jpql.toString(), Boolean.class);
    query.setParameter("cnesEmpresa", cnes);
    query.setParameter(PARAM_ID, id);

    try {
      return query.getSingleResult();
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_VERIFICAR_CNES, e);
      throw new DAOException();
    }
  }

  /**
   * Metodo responsavel por verificar se existe algum CNPJ que seja igual ao informado e diferente
   * do Identificador.
   *
   * @param cnpj- CNPJ da Empresa.
   * @param id - Identificador da empresa.
   * @return True , caso exista algum CNPJ e False caso contrario
   * @throws DAOException - Caso tenha algum erro de persistencia na hora de realizar a consulta
   */
  public boolean verificarCnpjExistente(final String cnpj, final Long id) throws DAOException {
    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
            .append(" FROM Empresa empresa where UPPER(empresa.cnpj)").append(" = ")
            .append(":cnpjEmpresa").append(" AND").append(" empresa.id").append(" !=")
            .append(":id");

    final TypedQuery<Boolean> query =
        getEntityManager().createQuery(jpql.toString(), Boolean.class);
    query.setParameter("cnpjEmpresa", cnpj);
    query.setParameter(PARAM_ID, id);

    try {
      return query.getSingleResult();
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_VERIFICAR_CNPJ, e);
      throw new DAOException();
    }
  }

  /**
   * Metodo responsavel por verificar se existe algum Nome Fantasia que seja igual ao informado e
   * diferente do Identificador.
   *
   * @param nomeFantasia - Nome Fantasia da Empresa.
   * @param id - Identificador da empresa.
   * @return True , caso exista algum Nome Fantasia e False caso contrario
   * @throws DAOException - Caso tenha algum erro de persistencia na hora de realizar a consulta
   */
  public boolean verificarNomeFantasiaExistente(String nomeFantasia, final Long id)
      throws DAOException {
    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
            .append(" FROM Empresa empresa where UPPER(empresa.nomeFantasia)").append(" = ")
            .append(":nomeFantasiaEmpresa").append(" AND").append(" empresa.id").append(" !=")
            .append(":id");

    final TypedQuery<Boolean> query =
        getEntityManager().createQuery(jpql.toString(), Boolean.class);
    nomeFantasia = nomeFantasia.toUpperCase();
    query.setParameter("nomeFantasiaEmpresa", nomeFantasia);
    query.setParameter(PARAM_ID, id);

    try {
      return query.getSingleResult();
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_VERIFICAR_NOME_FANTASIA, e);
      throw new DAOException();
    }
  }

  /**
   * Metodo responsavel por verificar se existe algum Nome Empresarial que seja igual ao informado e
   * diferente do Identificador.
   *
   * @param nomeEmpresarial- Nome Empresarial da Empresa.
   * @param id - Identificador da empresa.
   * @return True , caso exista algum Nome Empresarial e False caso contrario
   * @throws DAOException - Caso tenha algum erro de persistencia na hora de realizar a consulta
   */
  public boolean verificarNomeEmpresarialExistente(String nomeEmpresarial, final Long id)
      throws DAOException {
    final StringBuilder jpql =
        new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
            .append(" FROM Empresa empresa where UPPER(empresa.nomeEmpresarial)").append(" = ")
            .append(":nomeEmpresarial").append(" AND").append(" empresa.id").append(" !=")
            .append(":id");
    nomeEmpresarial = nomeEmpresarial.toUpperCase();
    final TypedQuery<Boolean> query =
        getEntityManager().createQuery(jpql.toString(), Boolean.class);
    query.setParameter(Empresa_.NOME_EMPRESARIAL, nomeEmpresarial);
    query.setParameter(PARAM_ID, id);

    try {
      return query.getSingleResult();
    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_VERIFICAR_NOME_EMPRESARIAL, e);
      throw new DAOException();
    }
  }

  /**
   * Metodo responsavel por realizar a consulta ao banco de dados segundo um filtro de pesquisa.
   *
   * @author rodolfo.carvalho@ctis.com.br
   *
   * @param filtroPesquisa - Filtro de pequisa.
   * @return List<Empresa> - Lista de empresas que são equivalentes ao filtro de pesquisa.
   * @throws DAOException
   * @throws RegistroNaoEncontradoException
   */
  public List<Empresa> consultarEmpresaRelatorio(final FiltroPesquisaEmpresaDTO filtroPesquisa)
      throws DAOException, RegistroNaoEncontradoException {
    try {

      final String orderBy =
          ColunaOrdenacaoEmpresaEnum.valueOf(filtroPesquisa.getOrderBy().toUpperCase()).getName();

      final List<Empresa> resultado = EntityQuery.create(getEntityManager(), Empresa.class)
          .like(Empresa_.NOME_EMPRESARIAL, filtroPesquisa.getNomeEmpresarial())
          .like(Empresa_.NOME_FANTASIA, filtroPesquisa.getNomeEmpresarial())
          .stringEqualsTo(Empresa_.CNPJ, filtroPesquisa.getCnpj())
          .objectEqualsTo(Empresa_.CODIGO_CNES, filtroPesquisa.getCnes())
          .in(Empresa_.ATIVO, EnumUtil.toStatusEnumList(filtroPesquisa.getStatus()))
          .addOrderBy(orderBy, filtroPesquisa.getTipoOrdenacao()).list();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
      throw new DAOException();
    }
  }

  /**
   * Consultar selecao empresa.
   *
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<SelecaoDTO> consultarSelecaoEmpresa()
      throws DAOException, RegistroNaoEncontradoException {
    try {
      final StringBuilder builder = new StringBuilder();
      builder
          .append("SELECT new br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO(e.id, e.nomeFantasia) ");
      builder.append("FROM Empresa e ");
      builder.append("ORDER BY e.nomeFantasia");

      final List<SelecaoDTO> resultado =
          getEntityManager().createQuery(builder.toString(), SelecaoDTO.class).getResultList();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
      throw new DAOException();
    }
  }

  /**
   * Consultar sistemas da empresa.
   *
   * @param idEmpresa the id empresa
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<SelecaoDTO> consultarSistemasDaEmpresa(Long idEmpresa)
      throws DAOException, RegistroNaoEncontradoException {
    try {
      final StringBuilder builder = new StringBuilder();
      builder.append("SELECT new br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO(s.id, s.nome) ");
      builder.append("FROM Sistema s WHERE s.empresa.id = : idEmpresa ");

      final List<SelecaoDTO> resultado =
          getEntityManager().createQuery(builder.toString(), SelecaoDTO.class)
              .setParameter("idEmpresa", idEmpresa).getResultList();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
      throw new DAOException();
    }
  }

  /**
   * Consultar empresa sem paginacao.
   *
   * @param filtroPesquisa the filtro pesquisa
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<Empresa> consultarEmpresaSemPaginacao(final FiltroPesquisaEmpresaDTO filtroPesquisa)
      throws DAOException, RegistroNaoEncontradoException {

    try {
      final String orderBy =
          ColunaOrdenacaoEmpresaEnum.valueOf(filtroPesquisa.getOrderBy().toUpperCase()).getName();

      final List<Empresa> resultado = EntityQuery.create(getEntityManager(), Empresa.class)
          .like(Empresa_.NOME_EMPRESARIAL, filtroPesquisa.getNomeEmpresarial())
          .like(Empresa_.NOME_FANTASIA, filtroPesquisa.getNomeFantasia())
          .stringEqualsTo(Empresa_.CNPJ, filtroPesquisa.getCnpj())
          .objectEqualsTo(Empresa_.CODIGO_CNES, filtroPesquisa.getCnes())
          .in(Empresa_.ATIVO, EnumUtil.toStatusEnumList(filtroPesquisa.getStatus()))
          .addOrderBy(orderBy, filtroPesquisa.getTipoOrdenacao()).list();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
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
  public List<EnversDTO> buscarHistoricoRevisoes(FiltroPesquisaLogEmpresaDTO filtro,
      String[] atributos) throws RegistroNaoEncontradoException, DAOException {
    List<EnversDTO> listaDeRevisoes = new ArrayList<>();
    setAuditReader(AuditReaderFactory.get(getEntityManager()));

    Empresa entidade = new Empresa();
    List<Object> revisoesPorId = consultarListaEntidades(filtro);
    Empresa revisaoObjetoTemporario = null;

    try {
      iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario, entidade,
          filtro.getIdEmpresa(), atributos);
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
  public List<Object> consultarListaEntidades(FiltroPesquisaLogEmpresaDTO filtro)
      throws RegistroNaoEncontradoException {
    AuditQuery auditQuery =
        getAuditReader().createQuery().forRevisionsOfEntity(Empresa.class, false, true);

    if (filtro.getIdEmpresa() != null) {
      auditQuery.add(AuditEntity.id().eq(filtro.getIdEmpresa()));
    }

    if (filtro.getDataInicial() != null) {
      Date dataInicial =
          Date.from(filtro.getDataInicial().atZone(ZoneId.systemDefault()).toInstant());
      auditQuery.add(AuditEntity.revisionProperty("dtRevisao").ge(dataInicial));
    }

    if (filtro.getDataFinal() != null) {
      Date dataFinal = Date.from(filtro.getDataFinal().atZone(ZoneId.systemDefault()).toInstant());
      auditQuery.add(AuditEntity.revisionProperty("dtRevisao").le(dataFinal));
    }

    return ValidadorUtil.validarNoResultList(auditQuery.getResultList());
  }

  @Override
  public void iterarSobreRevisoes(List<Object> revisoesPorId, List<EnversDTO> listaDeRevisoes,
      Empresa revisaoObjetoTemporario, Empresa entidade, Long id, String... atributosValidos)
      throws IllegalAccessException, CloneNotSupportedException, InvocationTargetException,
      NoSuchMethodException {

    HashMap<Long, Object> mapObjetosAnteriores = new HashMap<>();

    for (int i = 0; i < revisoesPorId.size(); i++) {
      Object[] itemRevisao = (Object[]) revisoesPorId.get(i);
      Empresa objetoRevisao = (Empresa) itemRevisao[0];
      Revision rev = (Revision) itemRevisao[1];
      RevisionType revType = (RevisionType) itemRevisao[2];

      revisaoObjetoTemporario = (Empresa) mapObjetosAnteriores.get(objetoRevisao.getId());

      if (revType != RevisionType.ADD && revisaoObjetoTemporario == null) {
        revisaoObjetoTemporario = consultarRevisaoAnterior(objetoRevisao.getId(), rev.getId());
      }

      Map<String, Object> listaCamposAlterados = new HashMap<>();

      if (revType == RevisionType.DEL) {
        for (String attr : atributosValidos) {
          listaCamposAlterados.put(attr, EMPRESA_REMOVIDA);
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

      Empresa empresa = (Empresa) consultarEntidadePorIdRevisao(Empresa.class, id);
      List<Object> list = consultarAlteracoesPorIdRevisao(empresa, id);
      List<Object> revisoesPorId = ValidadorUtil.validarNoResultList(list);
      Empresa revisaoObjetoTemporario = null;

      iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario, new Empresa(),
          id, atributos);

    } catch (Exception e) {
      LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
      throw new RegistroNaoEncontradoException();
    }

    return listaDeRevisoes.stream().filter(e -> e.getIdRevision().equals(id))
        .collect(Collectors.toList());
  }

  /**
   * Consultar Log EmpresaAud.
   *
   * @param filtro the filtro pesquisa
   * @return the list
   * @throws DAOException the DAO exception
   * @throws RegistroNaoEncontradoException the registro nao encontrado exception
   */
  public List<DadosEmpresaAudDTO> consultarLog(
      final FiltroPesquisaLogEmpresaDTO filtro)
      throws DAOException, RegistroNaoEncontradoException {

    try {

      StringBuilder jpql = new StringBuilder();
      jpql.append("SELECT");
      jpql.append(" NEW br.gov.pbh.prodabel.hubsmsa.dto.empresa.DadosEmpresaAudDTO(");
      jpql.append(" empresaAud.id,");
      jpql.append(" revisao,");
      jpql.append(" empresaAud.revType,");
      jpql.append(" empresaAud.nomeEmpresarial,");
      jpql.append(" empresaAud.nomeFantasia,");
      jpql.append(" empresaAud.cnpj,");
      jpql.append(" empresaAud.codigoCnes,");
      jpql.append(" empresaAud.site,");
      jpql.append(" empresaAud.ativo)");
      jpql.append(" FROM EmpresaAud empresaAud, Revision revisao");
      jpql.append(" WHERE empresaAud.revisao = revisao.id");

      if (filtro.getIdEmpresa() != null) {
        jpql.append(" AND empresaAud.id = ").append(filtro.getIdEmpresa());
      }

      if (filtro.getDataInicial() != null) {
        jpql.append(" AND revisao.dtRevisao >='").append(filtro.getDataInicial()).append("'");
      }

      if (filtro.getDataFinal() != null) {
        jpql.append(" AND revisao.dtRevisao<'").append(filtro.getDataFinal()).append("'");
      }

      jpql.append(" ORDER BY empresaAud.revisao DESC");

      List<DadosEmpresaAudDTO> resultado =
          getEntityManager().createQuery(jpql.toString(), DadosEmpresaAudDTO.class).getResultList();

      return ValidadorUtil.validarNoResultList(resultado);

    } catch (final PersistenceException e) {
      LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
      throw new DAOException();
    }
  }

  /**
   * Consultar revisao anterior.
   *
   * @param idEmpresa the id contato empresa
   * @param idRevisaoAtual the id revisao
   * @return the list
   */
  private Empresa consultarRevisaoAnterior(Long idEmpresa, Long idRevisaoAtual) {

    final String orderBy = ContatoEmpresaAud_.REVISAO;

    EmpresaAud empresaAud = EntityQuery.create(getEntityManager(), EmpresaAud.class)
        .objectEqualsTo(EmpresaAud_.ID, idEmpresa)
        .objectNotEqualsTo(EmpresaAud_.REVISAO, idRevisaoAtual).addOrderBy(orderBy, "DESC")
        .setMaxResults(1).uniqueResult();

    Empresa empresa = new Empresa();
    empresa.setAtivo(empresaAud.getAtivo());
    empresa.setCnpj(empresaAud.getCnpj());
    empresa.setCodigoCnes(empresaAud.getCodigoCnes());
    empresa.setId(empresaAud.getId());
    empresa.setNomeEmpresarial(empresaAud.getNomeEmpresarial());
    empresa.setNomeFantasia(empresaAud.getNomeFantasia());
    empresa.setSite(empresaAud.getSite());

    return empresa;

  }

  /**
   * Formatar valores.
   *
   * @param revisaoDTO the revisao DTO
   */
  private void formatarValores(EnversDTO revisaoDTO) {

    if (revisaoDTO.getField().equals(CAMPO_CNPJ)) {
      revisaoDTO.setValue(TextFormatUtil.formatarCnpj(revisaoDTO.getValue()));
      revisaoDTO.setOldValue(TextFormatUtil.formatarCnpj(revisaoDTO.getOldValue()));
    }

    if (revisaoDTO.getRevisionType() == RevisionType.ADD)
      revisaoDTO.setOldValue(EMPRESA_CRIADA);
    if (revisaoDTO.getRevisionType() == RevisionType.DEL)
      revisaoDTO.setValue(EMPRESA_REMOVIDA);

    if (revisaoDTO.getField().equals(CAMPO_ATIVO)) {
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
   * @param empresasAudList the empresas aud list
   * @return the list
   */
  public List<HistoricoEmpresaDTO> montarHistorico(List<DadosEmpresaAudDTO> empresasAudList) {

    List<HistoricoEmpresaDTO> empresasAudDto = new ArrayList<>();
    for (DadosEmpresaAudDTO itemRevisao : empresasAudList) {

      HistoricoEmpresaDTO historico = new HistoricoEmpresaDTO();

      historico.setId(itemRevisao.getId());
      historico.setCnes(itemRevisao.getCodigoCnes());
      historico.setCnpj(itemRevisao.getCnpj());
      historico.setNomeEmpresarial(itemRevisao.getNomeEmpresarial());
      historico.setNomeFantasia(itemRevisao.getNomeFantasia());
      historico.setSite(itemRevisao.getSite());
      historico.setStatus(itemRevisao.getAtivo().getName());
      historico.setLogin(itemRevisao.getRevisao().getMatricula());
      historico.setRevisao(String.valueOf(itemRevisao.getRevisao().getId()));
      historico.setDataEvento(
          TimeUtil.convertDataToStr(itemRevisao.getRevisao().getDtRevisao(), "dd/MM/yyyy HH:mm"));
      historico.setTipoRevisao(formataTipoRevisao(itemRevisao.getRevType()));
      empresasAudDto.add(historico);

    }
    return empresasAudDto;
  }

}
