package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import static br.gov.pbh.prodabel.hubsmsa.constants.ConstanteUtil.PORCENTAGEM;
import java.lang.reflect.Field;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.FiltroRevisaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.alteracao.FiltroPesquisaAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.exemplo.FiltroPesquisaExemploDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoAlteracaoEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.ColunaOrdenacaoExemploEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.StatusEnum;
import br.gov.pbh.prodabel.hubsmsa.enumeration.TipoOrdenacaoEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Exemplo;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.aud.ExemploAud;
import br.gov.pbh.prodabel.hubsmsa.util.PaginacaoUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;

@LocalBean
@Stateless
public class ExemploDAO extends GenericoDAO<Long, Exemplo> {

	private static final Logger LOG = LoggerFactory.getLogger(ExemploDAO.class);

	private static final String ID = "id";	
	private static final String STATUS = "status";
	private static final String CODIGO = "codigo";
	private static final String DESCRICAO_EXEMPLO = "descricao";
	private static final String ERRO_AO_CONSULTAR_OS_EXEMPLOS = "Erro ao consultar o(s) exemplo(s)";
	private static final String ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS = "Erro ao consultar o total de registros dos exemplos";
	private static final String REVISAO = "revisao";
	private static final String DATA_REVISAO = "dtRevisao";
	private static final String USUARIO_RESPONSAVEL = "matricula";	
	private static final String ASSUNTO = "assunto";
	private static final String DESCRICAO = "descricao";
	private static final String REVTYPE = "revType";
	
	
	public List<Exemplo> consultarExemplo(FiltroPesquisaExemploDTO filtroPesquisaExemplo)
			throws DAOException, RegistroNaoEncontradoException {
		
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Exemplo> query = getCriteriaBuilder().createQuery(Exemplo.class);
		Root<Exemplo> root = query.from(Exemplo.class);

		List<Predicate> parametros = montarParametros(filtroPesquisaExemplo, root, cb);
		List<Order> orderList = montarListaDeOrdenacao(filtroPesquisaExemplo, root, cb);

		query.select(root).where(parametros.toArray(new Predicate[parametros.size()])).orderBy(orderList);

		try {
			return ValidadorUtil.validarNoResultList(getEntityManager().createQuery(query)
					.setFirstResult(PaginacaoUtil.calcularPaginacaoAtual(filtroPesquisaExemplo.getNumeroPagina(),
							filtroPesquisaExemplo.getItensPorPagina()))
					.setMaxResults(filtroPesquisaExemplo.getItensPorPagina()).getResultList());
		} catch (PersistenceException e) {
			LOG.error(ERRO_AO_CONSULTAR_OS_EXEMPLOS, e);
			throw new DAOException();
		}

	}

	private List<Order> montarListaDeOrdenacao(FiltroPesquisaExemploDTO filtroPesquisaExemplo, Root<Exemplo> root,
			CriteriaBuilder cb) {
		List<Order> orderList = new ArrayList<>();
		if (filtroPesquisaExemplo.getTipoOrdenacao().equals(TipoOrdenacaoEnum.ASC.toString())) {
			orderList.add(cb.asc(root
					.get(ColunaOrdenacaoExemploEnum.valueOf(filtroPesquisaExemplo.getOrderBy().toUpperCase()).getName())));

		} else if (filtroPesquisaExemplo.getTipoOrdenacao().equals(TipoOrdenacaoEnum.DESC.toString())) {
			orderList.add(cb.desc(root
					.get(ColunaOrdenacaoExemploEnum.valueOf(filtroPesquisaExemplo.getOrderBy().toUpperCase()).getName())));
		}
		return orderList;
	}

	private List<Predicate> montarParametros(FiltroPesquisaExemploDTO filtroPesquisaExemplo, Root<Exemplo> root,
			CriteriaBuilder cb) {
		List<Predicate> parametros = new ArrayList<>();
		
		if (filtroPesquisaExemplo.getNomeExemplo() != null) {
			parametros.add(cb.like(cb.upper(root.get(DESCRICAO_EXEMPLO)), new StringBuilder().append(PORCENTAGEM)
					.append(filtroPesquisaExemplo.getNomeExemplo().trim().toUpperCase()).append(PORCENTAGEM).toString()));
		}

		if (filtroPesquisaExemplo.getCodigo() != null) {
			parametros.add(cb.equal(root.get(CODIGO), filtroPesquisaExemplo.getCodigo()));
		}

		if (filtroPesquisaExemplo.getStatus() != null) {
			parametros.add(cb.equal(cb.upper(root.get(STATUS)), StatusEnum.valueOf(filtroPesquisaExemplo.getStatus())));
		}

		return parametros;
	}

	
	public Integer consultarTotalRegistros(FiltroPesquisaExemploDTO exemploDTO) throws DAOException {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Long> query = getCriteriaBuilder().createQuery(Long.class);
		Root<Exemplo> root = query.from(Exemplo.class);

		List<Predicate> parametros = montarParametros(exemploDTO, root, cb);

		query.select(cb.countDistinct(root)).where(parametros.toArray(new Predicate[parametros.size()]));

		try {
			return getEntityManager().createQuery(query).getSingleResult().intValue();
		} catch (PersistenceException e) {
			LOG.error(ERRO_AO_CONSULTAR_O_TOTAL_DE_REGISTROS, e);
			throw new DAOException();
		}
	}

	
	public Boolean verificarNomeExistente(String nome) throws DAOException {

		StringBuilder jpql = new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
				.append(" FROM Exemplo exemplo where UPPER(exemplo.descricao)").append(" = ").append(":nome");

		TypedQuery<Boolean> query = getEntityManager().createQuery(jpql.toString(), Boolean.class);
		query.setParameter("nome",  nome);

		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			LOG.error("Erro ao verificar se existe Exemplo: ", e);
			throw new DAOException();
		}
	}

	
	public Boolean verificarSeCodigoExiste(String codigo) throws DAOException {
		StringBuilder jpql = new StringBuilder("SELECT CASE WHEN COUNT(1) <> 0 THEN TRUE ELSE FALSE END")
				.append(" FROM Exemplo exemplo where UPPER(exemplo.codigo)").append(" = ").append(":codigo");

		TypedQuery<Boolean> query = getEntityManager().createQuery(jpql.toString(), Boolean.class);
		query.setParameter(CODIGO, codigo);

		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			LOG.error("Erro ao verificar se existe Exemplo: ", e);
			throw new DAOException();
		}
	}	

	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public List<ExemploAud> consultarAlteracoes(FiltroPesquisaAlteracaoDTO filtroDTO) throws RegistroNaoEncontradoException, DAOException {
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<ExemploAud> queryCriteria = getCriteriaBuilder().createQuery(ExemploAud.class);
			Root<ExemploAud> root = queryCriteria.from(ExemploAud.class);
			Join<ExemploAud, Revision> joinRevisao = (Join) root.fetch(REVISAO);

			List<Predicate> parametros = montarParametrosAlteracao(filtroDTO, root, joinRevisao, cb);
			List<Order> orderList = montarListaDeOrdenacao(filtroDTO, root, cb, joinRevisao);
			queryCriteria.select(root).where(parametros.toArray(new Predicate[parametros.size()])).orderBy(orderList);
			
			return getEntityManager().createQuery(queryCriteria).getResultList();	

		} catch (NoResultException e) {
			throw new RegistroNaoEncontradoException();
		} catch (PersistenceException e) {
			throw new DAOException();
		}
	}
	private List<Order> montarListaDeOrdenacao(FiltroPesquisaAlteracaoDTO filtroDto, Root<ExemploAud> root,
			CriteriaBuilder cb, Join<ExemploAud, Revision> joinRevisao) {

		List<Order> orderList = new ArrayList<>();

		String colunaNome = ColunaOrdenacaoAlteracaoEnum.valueOf(filtroDto.getOrderBy().toUpperCase()).getName();

		Path<Object> rotaOrdenacao = null;

		if (isColunaFromRevisao(colunaNome)) {
			rotaOrdenacao = joinRevisao.get(colunaNome);
		} else if (isColunaDescricao(colunaNome)) {
			rotaOrdenacao = root.get(DESCRICAO_EXEMPLO);
		}

		if (filtroDto.getTipoOrdenacao().equals(TipoOrdenacaoEnum.DESC.toString())) {
			orderList.add(cb.desc(rotaOrdenacao));
		} else if (filtroDto.getTipoOrdenacao().equals(TipoOrdenacaoEnum.ASC.toString())) {
			orderList.add(cb.asc(rotaOrdenacao));
		}
		return orderList;
	}

	private boolean isColunaDescricao(String colunaNome) {
		return DESCRICAO.equalsIgnoreCase(colunaNome) || ASSUNTO.equalsIgnoreCase(colunaNome);
	}

	private boolean isColunaFromRevisao(String colunaNome) {
		for (Field iterableField : Revision.class.getDeclaredFields()) {
			iterableField.setAccessible(true);
			if (colunaNome.equalsIgnoreCase(iterableField.getName())) {
				return true;
			}
		}
		return false;
	}
	private List<Predicate> montarParametrosAlteracao(FiltroPesquisaAlteracaoDTO filtroDTO,
			Root<ExemploAud> root,
			Join<ExemploAud, Revision> joinRevisao, CriteriaBuilder cb) {
		List<Predicate> parametros = new ArrayList<>();
		
		if(filtroDTO.getDataInicial() != null && filtroDTO.getDataFinal() != null) {		
			parametros.add(cb.between(joinRevisao.get(DATA_REVISAO), 
					Date.from(filtroDTO.getDataInicial().atZone(ZoneId.systemDefault()).toInstant()), 
					Date.from(filtroDTO.getDataFinal().atZone(ZoneId.systemDefault()).toInstant())));
		}
		if(filtroDTO.getUsuarioResponsavel() != null) {	
			parametros.add(cb.like(cb.upper(joinRevisao.get(USUARIO_RESPONSAVEL)),
					new StringBuilder().append(PORCENTAGEM)
							.append(filtroDTO.getUsuarioResponsavel().trim().toUpperCase())
							.append(PORCENTAGEM).toString()));
		}
		if(filtroDTO.getCodigo() != null) {
			parametros.add(cb.equal(root.get(CODIGO), filtroDTO.getCodigo()));
		}

		if (filtroDTO.getDescricao() != null) {
			parametros.add(cb.like(cb.upper(root.get(DESCRICAO_EXEMPLO)),
					new StringBuilder().append(PORCENTAGEM)
							.append(filtroDTO.getDescricao().trim().toUpperCase())
							.append(PORCENTAGEM).toString()));
		}
		parametros.add(cb.notEqual(root.get(REVTYPE), RevisionType.ADD));
		
		return parametros;
	}

	
	public ExemploAud consultarExemploAudCompleto(Long id) throws RegistroNaoEncontradoException, DAOException {		
		try {
			StringBuilder query = new StringBuilder("SELECT es FROM ExemploAud es ");
			query.append("LEFT JOIN FETCH es.revisao ");			
			query.append("WHERE es.id = :id ORDER BY es.revisao.dtRevisao desc");

			TypedQuery<ExemploAud> typedQuery = getEntityManager().createQuery(query.toString(),
					ExemploAud.class);
			typedQuery.setParameter("id", id);
			typedQuery.setMaxResults(1);
			return typedQuery.getResultList().get(0);			

		} catch (NoResultException e) {
			throw new RegistroNaoEncontradoException();	
		} 
		catch (PersistenceException e) {
			LOG.error(ERRO_AO_CONSULTAR_OS_EXEMPLOS, e);
			throw new DAOException();
		}		
	}
	
	public List<EnversDTO> buscarHistoricoRevisoes(Long id, FiltroRevisaoDTO filtro, String[] atributos)
			throws RegistroNaoEncontradoException, DAOException {
		List<EnversDTO> listaDeRevisoes = new ArrayList<>();		
		setAuditReader(AuditReaderFactory.get(getEntityManager()));
		Exemplo entidade = consultarPorId(id);
		Exemplo revisaoObjetoTemporario = null;
		
		List<Object> revisoesPorId = consultarListaEntidadesPorId(entidade, id);
		try {
			iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario, entidade, id, atributos);			
		} catch (NoResultException e) {
			throw new RegistroNaoEncontradoException();
		} catch (Exception e) {
			throw new DAOException();
		}
		listaDeRevisoes.removeIf(list -> list.getRevisionType() == RevisionType.ADD);	
		
		return listaDeRevisoes;
	}
}