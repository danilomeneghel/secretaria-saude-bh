package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.PATH_EMPRESA_ID;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_AS_EMPRESAS;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_OS_SISTEMAS;
import static br.gov.pbh.prodabel.hubsmsa.constants.MensagemErroUtil.ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
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
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.DadosSistemaAudDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.FiltroPesquisaLogSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.sistema.FiltroPesquisaSistemaDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoSistemaEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Empresa;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.ContatoEmpresaAud_;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.SistemaAud;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.SistemaAud_;
import br.gov.pbh.prodabel.hubsmsa.util.EnumUtil;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.TimeUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;
import br.gov.pbh.prodabel.hubsmsa.util.query.EntityQuery;
 
/**
 * Classe DAO responsável por manipular os dados de Sistema
 *
 * @author danilo.oliveiram@ctis.com.br
 */
@LocalBean
@Stateless
public class SistemaDAO extends GenericoDAO<Long, Sistema> {

	private static final Logger LOG = LoggerFactory.getLogger(SistemaDAO.class);

	protected static final String SISTEMA_REMOVIDO = "Sistema Removido";
	protected static final String SISTEMA_CRIADO = "Sistema Criado";
	protected static final String ATIVO = "Ativo";
	protected static final String INATIVO = "Inativo";
	protected static final String CAMPO_ATIVO = "ativo";
	protected static final String SIM = "Sim";
	protected static final String NAO = "Não";

	/**
	 * Retorna uma lista de {@link Sistema} de acordo com o filtro passado.
	 * 
	 * @param filtroPesquisaSistema Objeto do tipo {@link FiltroPesquisaSistemaDTO}
	 *                              que contém os campos referentes ao filtro de
	 *                              Sistema.
	 * @return {@link List<Sistema>} - Retorno da consulta.
	 * @throws RegistroNaoEncontradoException Caso nenhum registro seja encontrado
	 *                                        correspondente ao filtro passado.
	 * @throws DAOException                   Caso ocorra algum erro inesperado na
	 *                                        operação.
	 */
	public List<Sistema> consultarSistema(FiltroPesquisaSistemaDTO filtroPesquisaSistema)
			throws DAOException, RegistroNaoEncontradoException {

		try {

			String orderBy = ColunaOrdenacaoSistemaEnum.valueOf(filtroPesquisaSistema.getOrderBy().toUpperCase())
					.getName();

			Integer paginaAtual = PaginacaoUtil.calcularPaginacaoAtual(filtroPesquisaSistema.getNumeroPagina(),
					filtroPesquisaSistema.getItensPorPagina());

			List<Sistema> resultado = EntityQuery.create(getEntityManager(), Sistema.class)
					.innerJoinFetch(Sistema_.EMPRESA)
					.objectEqualsTo(PATH_EMPRESA_ID, filtroPesquisaSistema.getIdEmpresa())
					.like(Sistema_.NOME, filtroPesquisaSistema.getNome())
					.in(Sistema_.ATIVO, EnumUtil.toStatusEnumList(filtroPesquisaSistema.getStatus()))
					.addOrderBy(orderBy, filtroPesquisaSistema.getTipoOrdenacao()).setFirstResult(paginaAtual)
					.setMaxResults(filtroPesquisaSistema.getItensPorPagina()).list();

			return ValidadorUtil.validarNoResultList(resultado);

		} catch (PersistenceException e) {
			LOG.error(ERRO_AO_CONSULTAR_OS_SISTEMAS, e);
			throw new DAOException();
		}

	}

	/**
	 * Retorna um sistema baseado no id passado com sua relação com {@link Empresa}.
	 * 
	 * @param id id referente ao sistema que deseja-se recuperar.
	 * @return {@link Sistema} - Retorna Sistema correspondente ao id passado.
	 * @throws DAOException Caso ocorra algum erro inesperado na operação.
	 */
	public Sistema consultaSistemaPorId(Long id) throws DAOException {
		try {

			return EntityQuery.create(getEntityManager(), Sistema.class).innerJoinFetch(Sistema_.EMPRESA)
					.objectEqualsTo(Sistema_.ID, id).uniqueResult();

		} catch (PersistenceException e) {
			LOG.error(ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS, e);
			throw new DAOException();
		}
	}


    /**
     * Consulta sistema por empresa.
     *
     * @param empresa the empresa
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Sistema> consultaSistemaPorEmpresa(Empresa empresa) throws DAOException {
      try {

        return EntityQuery.create(getEntityManager(), Sistema.class)
            .innerJoinFetch(Sistema_.EMPRESA).objectEqualsTo(Sistema_.EMPRESA, empresa)
            .list();

      } catch (PersistenceException e) {
        LOG.error(ERRO_AO_CONSULTAR_OS_SISTEMAS, e);
        throw new DAOException();
      }
    }


    /**
     * Retorna Lista de todos os sistemas
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Sistema> consultaSistemas() throws DAOException {
      try {

        return EntityQuery.create(getEntityManager(), Sistema.class)
            .innerJoinFetch(Sistema_.EMPRESA).list();

      } catch (PersistenceException e) {
        LOG.error(ERRO_AO_CONSULTAR_OS_SISTEMAS, e);
        throw new DAOException();
      }
    }

	/**
	 * Retorna uma lista de {@link Sistema} de acordo com o filtro passado sem
	 * paginacao.
	 * 
	 * @param filtroPesquisaSistema Objeto do tipo {@link FiltroPesquisaSistemaDTO}
	 *                              que contém os campos referentes ao filtro de
	 *                              Sistema.
	 * @return {@link List<Sistema>} - Retorno da consulta.
	 * @throws RegistroNaoEncontradoException Caso nenhum registro seja encontrado
	 *                                        correspondente ao filtro passado.
	 * @throws DAOException                   Caso ocorra algum erro inesperado na
	 *                                        operação.
	 */
	public List<Sistema> consultarSistemaSemPaginacao(FiltroPesquisaSistemaDTO filtroPesquisaSistema)
			throws DAOException, RegistroNaoEncontradoException {

		try {
			String orderBy = ColunaOrdenacaoSistemaEnum.valueOf(filtroPesquisaSistema.getOrderBy().toUpperCase())
					.getName();

			List<Sistema> resultado = EntityQuery.create(getEntityManager(), Sistema.class)
					.like(Sistema_.NOME, filtroPesquisaSistema.getNome())
					.in(Sistema_.ATIVO, EnumUtil.toStatusEnumList(filtroPesquisaSistema.getStatus()))
					.addOrderBy(orderBy, filtroPesquisaSistema.getTipoOrdenacao()).list();

			return ValidadorUtil.validarNoResultList(resultado);

		} catch (PersistenceException e) {
			LOG.error(ERRO_AO_CONSULTAR_OS_SISTEMAS, e);
			throw new DAOException();
		}
	}

	/**
	 * Retorna o total de registros de um determinado tipo no banco.
	 * 
	 * @param filtroPesquisaSistema Objeto do tipo {@link FiltroPesquisaSistemaDTO}
	 *                              que contém os campos referentes ao filtro de
	 *                              Sistema.
	 * @return O total de registro de um determinado tipo de acordo com os
	 *         parametros passados
	 * @throws DAOException Caso ocorra algum erro inesperado na operação.
	 */
	public Integer consultarTotalRegistros(FiltroPesquisaSistemaDTO filtroPesquisaSistema) throws DAOException {
		try {

			return EntityQuery.createCount(getEntityManager(), Sistema.class).distinct().innerJoin(Sistema_.EMPRESA)
					.objectEqualsTo(PATH_EMPRESA_ID, filtroPesquisaSistema.getIdEmpresa())
					.like(Sistema_.NOME, filtroPesquisaSistema.getNome())
					.in(Sistema_.ATIVO, EnumUtil.toStatusEnumList(filtroPesquisaSistema.getStatus())).count()
					.intValue();

		} catch (PersistenceException e) {
			LOG.error(ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS, e);
			throw new DAOException();
		}
	}

	/**
	 * Verifica se um nome de sistema já foi cadastrado para alguma empresa.
	 * 
	 * @param nomeSistema Nome do sistema a ser validado.
	 * @return {@link Boolean} Retorna um boolean para validações.
	 * @throws DAOException Caso ocorra algum erro inesperado na operação.
	 */
	public Boolean verificarExistenciaSistemaParaMesmaEmpresa(String nomeSistema, Long idEmpresa) throws DAOException {
		StringBuilder jpql = new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
				.append(" FROM Sistema sistema INNER JOIN Empresa empresa ON sistema.empresa.id = empresa.id")
				.append(" AND ").append("UPPER(sistema.nome)").append(" = ").append(":nome").append(" AND ")
				.append("sistema.empresa.id").append(" = ").append(":empresa");

		TypedQuery<Boolean> query = getEntityManager().createQuery(jpql.toString(), Boolean.class);
		query.setParameter(Sistema_.NOME, nomeSistema.toUpperCase());
		query.setParameter(Sistema_.EMPRESA, idEmpresa);

		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			LOG.error("Erro ao verificar se existe Sistema com mesmo nome: ", e);
			throw new DAOException();
		}
	}

    /**
     * Verificar existencia sistema para mesma empresa.
     *
     * @param id the id
     * @param nomeSistema the nome sistema
     * @param idEmpresa the id empresa
     * @return the boolean
     * @throws DAOException the DAO exception
     */
	public Boolean verificarExistenciaSistemaParaMesmaEmpresa(Long id, String nomeSistema, Long idEmpresa)
			throws DAOException {
		StringBuilder jpql = new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
				.append(" FROM Sistema sistema INNER JOIN Empresa empresa ON sistema.empresa.id = empresa.id")
				.append(" AND ").append("UPPER(sistema.nome)").append(" = ").append(":nome").append(" AND ")
				.append("sistema.empresa.id").append(" = ").append(":empresa").append(" AND ").append("sistema.id")
				.append(" != ").append(":id");

		TypedQuery<Boolean> query = getEntityManager().createQuery(jpql.toString(), Boolean.class);
		query.setParameter(Sistema_.NOME, nomeSistema.toUpperCase());
		query.setParameter(Sistema_.ID, id);
		query.setParameter(Sistema_.EMPRESA, idEmpresa);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			LOG.error("Erro ao verificar se existe Sistema com mesmo nome: ", e);
			throw new DAOException();
		}
	}

	/**
	 * Verifica se uma empresa tem algum sistema cadastrado.
	 * 
	 * @param id id da empresa a ser validada.
	 * @return {@link Boolean} Retorna um boolean para validações.
	 * @throws DAOException Caso ocorra algum erro inesperado na operação.
	 */
	public boolean verificarExistenciaSistemaParaUmaEmpresa(Long id) throws DAOException {
		StringBuilder jpql = new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
				.append(" FROM Sistema sistema where sistema.empresa.id").append(" = ").append(":idEmpresa");

		TypedQuery<Boolean> query = getEntityManager().createQuery(jpql.toString(), Boolean.class);
		query.setParameter("idEmpresa", id);

		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			LOG.error("Erro ao verificar se existe Exemplo: ", e);
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
			builder.append(" FROM Sistema tdp ORDER BY tdp.nome ASC ");

			return getEntityManager().createQuery(builder.toString(), SelecaoDTO.class).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

  /**
   * Consultar sistema por nome.
   *
   * @param sistema the sistema
   * @return the sistema
   * @throws DAOException the DAO exception
   */
  public Sistema consultarSistemaPorNome(String sistema) throws DAOException {
    try {

      StringBuilder builder = new StringBuilder();
      builder.append(" SELECT s FROM Sistema s JOIN FETCH s.empresa where lower(s.nome) = :nome ");

      return getEntityManager().createQuery(builder.toString(), Sistema.class)
          .setParameter("nome", sistema.toLowerCase()).getSingleResult();

    } catch (Exception e) {
      LOG.error("Erro ao verificar se existe Exemplo: ", e);
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
	public List<EnversDTO> buscarHistoricoRevisoes(FiltroPesquisaLogSistemaDTO filtro, String[] atributos)
			throws RegistroNaoEncontradoException, DAOException {
		List<EnversDTO> listaDeRevisoes = new ArrayList<>();
		setAuditReader(AuditReaderFactory.get(getEntityManager()));

		Sistema entidade = new Sistema();
        List<DadosSistemaAudDTO> revisoesPorId = consultarListaEntidades(filtro);

		try {
      iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, entidade, atributos);
		} catch (Exception e) {
			LOG.error(ERRO_AO_CONSULTAR_OS_SISTEMAS, e);
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
    public List<DadosSistemaAudDTO> consultarListaEntidades(FiltroPesquisaLogSistemaDTO filtro)
        throws RegistroNaoEncontradoException {
      StringBuilder jpql = new StringBuilder();
      jpql.append("SELECT");
      jpql.append(" NEW br.gov.pbh.prodabel.hubsmsa.dto.sistema.DadosSistemaAudDTO(");
      jpql.append(" sistemaAud.id,");
      jpql.append(" sistemaAud.revisao,");
      jpql.append(" sistemaAud.revType,");
      jpql.append(" sistemaAud.descricao,");
      jpql.append(" sistemaAud.formaCadastro,");
      jpql.append(" sistemaAud.nome,");
      jpql.append(" sistemaAud.status,");
      jpql.append(" (SELECT empresaAud.nomeFantasia FROM EmpresaAud empresaAud");
      jpql.append(
          " WHERE empresaAud.revisao = (SELECT MAX(empresaAud2.revisao) FROM EmpresaAud empresaAud2 WHERE empresaAud2.id = sistemaAud.empresa)),");
      jpql.append(" rev.dtRevisao,");
      jpql.append(" rev.matricula )");
      jpql.append(" FROM SistemaAud sistemaAud, Revision rev");
      jpql.append(" WHERE sistemaAud.revisao = rev.id");

      if (filtro.getId() != null) {

        jpql.append(" AND sistemaAud.id = " + filtro.getId());
      }

		if (filtro.getNome() != null) {
			jpql.append(" AND UPPER(unaccent(sistemaAud.nome)) LIKE UPPER(unaccent('%").append(filtro.getNome())
					.append("%'))");
		}

		if (filtro.getIdEmpresa() != null) {
			jpql.append(" AND sistemaAud.empresa.id = ").append(filtro.getIdEmpresa());
		}

		if (filtro.getDataInicial() != null) {
          jpql.append(" AND rev.dtRevisao >= '").append(filtro.getDataInicial()).append("'");
		}

		if (filtro.getDataFinal() != null) {
          jpql.append(" AND rev.dtRevisao < '").append(filtro.getDataFinal()).append("'");
		}

        jpql.append(" ORDER BY sistemaAud.revisao DESC");

        List<DadosSistemaAudDTO> resultado = getEntityManager()
            .createQuery(jpql.toString(), DadosSistemaAudDTO.class).getResultList();

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
    public void iterarSobreRevisoes(List<DadosSistemaAudDTO> revisoesPorIdList,
        List<EnversDTO> listaDeRevisoes,
      Sistema entidade, String... atributosValidos)
			throws IllegalAccessException, CloneNotSupportedException, InvocationTargetException,
			NoSuchMethodException {

      HashMap<Long, Object> mapObjetosAnteriores = new HashMap<>();
      Sistema revisaoObjetoTemporario = null;
      for (DadosSistemaAudDTO revisoesPorId : revisoesPorIdList) {

        Sistema objetoRevisao = new Sistema();
        objetoRevisao.setId(revisoesPorId.getId());
        Revision rev = revisoesPorId.getRevisao();
        RevisionType revType = revisoesPorId.getRevType();
        objetoRevisao.setDescricao(revisoesPorId.getDescricao());
        objetoRevisao.setFormaCadastro(revisoesPorId.getFormaCadastro());
        objetoRevisao.setNome(revisoesPorId.getNome());
        objetoRevisao.setAtivo(revisoesPorId.getStatus());
        objetoRevisao.setNomeEmpresa(revisoesPorId.getNomeEmpresa());
			
        revisaoObjetoTemporario = (Sistema) mapObjetosAnteriores.get(objetoRevisao.getId());

        if (revType != RevisionType.ADD && revisaoObjetoTemporario == null) {
          revisaoObjetoTemporario = consultarRevisaoAnterior(objetoRevisao.getId(), rev.getId());
        }

        Map<String, Object> listaCamposAlterados = new HashMap<>();

        if (revType == RevisionType.DEL) {
          for (String attr : atributosValidos) {
            listaCamposAlterados.put(attr, SISTEMA_REMOVIDO);
          }
        } else {
          listaCamposAlterados =
              consultarValorAlterado(revisaoObjetoTemporario, objetoRevisao, atributosValidos);
        }

        for (Entry<String, Object> itemAlterado : listaCamposAlterados.entrySet()) {
          EnversDTO revisaoDTO = mapearRevisaoDTO(itemAlterado, revType, rev, entidade,
              objetoRevisao.getId(), revisaoObjetoTemporario);
          revisaoDTO.setNomeEmpresa(revisoesPorId.getNomeEmpresa());
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
    private Sistema consultarRevisaoAnterior(Long idSistema, Long idRevisaoAtual) {

      final String orderBy = ContatoEmpresaAud_.REVISAO;

      SistemaAud sistemaAud = EntityQuery.create(getEntityManager(), SistemaAud.class)
          .innerJoinFetch(SistemaAud_.EMPRESA).objectEqualsTo(SistemaAud_.ID, idSistema)
          .objectNotEqualsTo(SistemaAud_.REVISAO, idRevisaoAtual).addOrderBy(orderBy, "DESC")
          .setMaxResults(1).uniqueResult();

      Sistema sistema = new Sistema();
      sistema.setAtivo(sistemaAud.getStatus());
      sistema.setDescricao(sistemaAud.getDescricao());
      sistema.setFormaCadastro(sistemaAud.getFormaCadastro());
      sistema.setId(sistemaAud.getId());
      sistema.setNome(sistemaAud.getNome());
      sistema.setNomeEmpresa(sistemaAud.getEmpresa().getNomeFantasia());

      return sistema;

    }

    /**
     * Formatar valores.
     *
     * @param revisaoDTO the revisao DTO
     */
	private void formatarValores(EnversDTO revisaoDTO) {

		if (revisaoDTO.getRevisionType() == RevisionType.ADD)
			revisaoDTO.setOldValue(SISTEMA_CRIADO);
		if (revisaoDTO.getRevisionType() == RevisionType.DEL)
			revisaoDTO.setValue(SISTEMA_REMOVIDO);

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
     * Buscar detalhe revisao.
     *
     * @param id the id
     * @param atributos the atributos
     * @return the list
     * @throws RegistroNaoEncontradoException the registro nao encontrado exception
     */
	public List<EnversDTO> buscarDetalheRevisao(Long id, String[] atributos) throws RegistroNaoEncontradoException {
		List<EnversDTO> listaDeRevisoes = new ArrayList<>();

		try {
            Sistema sistema = (Sistema) consultarEntidadePorIdRevisao(Sistema.class, id);
            FiltroPesquisaLogSistemaDTO filtroPesquisa = new FiltroPesquisaLogSistemaDTO();
            filtroPesquisa.setId(sistema.getId());
            List<DadosSistemaAudDTO> revisoesPorId = consultarListaEntidades(filtroPesquisa);

            iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, sistema, atributos);
		} catch (Exception e) {
			LOG.error(ERRO_AO_CONSULTAR_AS_EMPRESAS, e);
			throw new RegistroNaoEncontradoException();
		}

		return listaDeRevisoes.stream().filter(e -> e.getIdRevision().equals(id)).collect(Collectors.toList());
	}

    /**
     * Montar historico.
     *
     * @param tipoDeParasAudList the tipo de paras aud list
     * @return the list
     */
    public List<HistoricoSistemaDTO> montarHistorico(List<DadosSistemaAudDTO> tipoDeParasAudList) {
      List<HistoricoSistemaDTO> tipoDeParasAudDto = new ArrayList<>();

      for (DadosSistemaAudDTO itemRevisao : tipoDeParasAudList) {

        HistoricoSistemaDTO historico = new HistoricoSistemaDTO();

        historico.setDescricao(itemRevisao.getDescricao());
        historico.setId(itemRevisao.getId());
        historico.setNome(itemRevisao.getNome());
        historico.setStatus(itemRevisao.getStatus().getName());
        historico.setLogin(itemRevisao.getMatricula());
        historico.setRevisao(String.valueOf(itemRevisao.getRevisao().getId()));
        historico.setDataEvento(
            TimeUtil.convertDataToStr(itemRevisao.getDtRevisao(), "dd/MM/yyyy HH:mm"));
        historico.setTipoRevisao(formataTipoRevisao(itemRevisao.getRevType()));
        tipoDeParasAudDto.add(historico);
        historico.setNomeEmpresa(itemRevisao.getNomeEmpresa());
        historico.setDescricao(itemRevisao.getDescricao());

      }
      return tipoDeParasAudDto;
    }

}
