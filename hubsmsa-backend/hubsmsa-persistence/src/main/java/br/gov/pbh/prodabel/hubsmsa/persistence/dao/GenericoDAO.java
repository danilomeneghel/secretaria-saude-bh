package br.gov.pbh.prodabel.hubsmsa.persistence.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import org.apache.commons.lang.StringUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.ValidatorBase;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.EntidadeBase;
import br.gov.pbh.prodabel.hubsmsa.util.ValidadorUtil;
// TODO: Auto-generated Javadoc
/**
 * Fornece as funções básicas de um DAO
 *
 * @param <K> - Chave
 * @param <T> - Tipo
 */
@SuppressWarnings("unchecked")
public abstract class GenericoDAO<K extends Serializable, T extends EntidadeBase<K>> {

	protected static final String AND = " AND ";
	protected static final String OR = " OR ";
	protected static final String GET_NAME = "getName";
	protected static final String CARGA_INICIAL = "Carga Inicial";

	private static final Logger LOG = LoggerFactory.getLogger(GenericoDAO.class);
	
	@PersistenceContext(unitName = "br.gov.pbh.prodabel.hubsmsa")
	private EntityManager em;
	private AuditReader reader;    
	
	/**
	 * Listar.
	 *
	 * @return the list
	 */
	public List<T> listar() {
		Class<T> typeClassEntity = (Class<T>) getTypeClassEntity();
		return em.createQuery(getCriteriaBuilder().createQuery(typeClassEntity)).getResultList();
	}
	
	/**
	 * Consultar por id.
	 *
	 * @param id the id
	 * @return the t
	 */
	public T consultarPorId(K id) {
		Class<T> typeClassEntity = (Class<T>) getTypeClassEntity();
		return em.find(typeClassEntity, id);
	}

	private Class<?> getTypeClassEntity() {
		return (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	@SuppressWarnings("unused")
	private Class<?> getTypeClassDTO() {
		return (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
	}	
	
	/**
	 * Gravar.
	 *
	 * @param objeto the objeto
	 * @return the t
	 */
	public T gravar(T objeto) {
		ValidatorBase.validate(objeto);

		if (objeto.getId() == null) {
			em.persist(objeto);
			return objeto;
		}
		return em.merge(objeto);
	}
	
	/**
	 * Excluir.
	 *
	 * @param id the id
	 * @throws RegistroNaoEncontradoException the registro nao encontrado exception
	 */
	public void excluir(K id) throws RegistroNaoEncontradoException {
		T objeto = consultarPorId(id);
		ValidadorUtil.validarRegistroNaoEncontrado(objeto);
		excluir(objeto);
	}
	
	/**
	 * Merge.
	 *
	 * @param objeto the objeto
	 * @return the t
	 */
	public T merge(T objeto) {
		return em.merge(objeto);
	}
	
	/**
	 * Excluir.
	 *
	 * @param objeto the objeto
	 */
	public void excluir(T objeto) {
		em.remove(objeto);
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return em.getCriteriaBuilder();
	}

	public EntityManager getEntityManager() {
		return em;
	}
	
	/**
	 * Consultar entidades revisao por id.
	 *
	 * @param id the id
	 * @param atributosValidos the atributos validos
	 * @return the list
	 * @throws RegistroNaoEncontradoException the registro nao encontrado exception
	 * @throws DAOException the DAO exception
	 */
	public synchronized List<EnversDTO> consultarEntidadesRevisaoPorId(Long id, String...atributosValidos)
			throws RegistroNaoEncontradoException, DAOException {
		List<EnversDTO> listaDeRevisoes = new ArrayList<>();

		setAuditReader(AuditReaderFactory.get(em));		
		T revisaoObjetoTemporario = null;		
		T entidade = consultarPorId((K) id);

		try {
			List<Object> revisoesPorId = consultarListaEntidadesPorId(entidade, id);
			iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario, entidade, id, atributosValidos);
		} catch (Exception e) {
			LOG.error("Erro ao recuperar entidades de revisão ", e);
		}
		listaDeRevisoes.removeIf(list -> list.getRevisionType() == RevisionType.ADD);
		return listaDeRevisoes;	
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
	public void iterarSobreRevisoes(List<Object> revisoesPorId, List<EnversDTO> listaDeRevisoes,
			T revisaoObjetoTemporario, T entidade, Long id, String... atributosValidos) throws IllegalAccessException, CloneNotSupportedException, InvocationTargetException, NoSuchMethodException {
		for (int i = 0; i < revisoesPorId.size(); i++) {
			Object[] itemRevisao = (Object[]) revisoesPorId.get(i);
			T objetoRevisao = (T) itemRevisao[0];
			Revision rev = (Revision) itemRevisao[1];
			RevisionType revType = (RevisionType) itemRevisao[2];
			Map<String, Object> listaCamposAlterados = consultarValorAlterado(revisaoObjetoTemporario, objetoRevisao, atributosValidos);
			for (Entry<String, Object> itemAlterado : listaCamposAlterados.entrySet()) {
				EnversDTO revisaoDTO = mapearRevisaoDTO(itemAlterado, revType, rev, entidade, id, revisaoObjetoTemporario);
				listaDeRevisoes.add(revisaoDTO);
			}
			revisaoObjetoTemporario = (T) (objetoRevisao).clone();
		}

	}

	/**
	 * Mapear revisao DTO.
	 *
	 * @param item the item
	 * @param revType the rev type
	 * @param rev the rev
	 * @param entity the entity
	 * @param id the id
	 * @param revisaoObjetoTemporario the revisao objeto temporario
	 * @return the envers DTO
	 * @throws IllegalAccessException the illegal access exception
	 * @throws NoSuchMethodException the no such method exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	protected EnversDTO mapearRevisaoDTO(Entry<String, Object> item, RevisionType revType, Revision rev, T entity,
			Long id, T revisaoObjetoTemporario) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		EnversDTO revisaoDTO = new EnversDTO();
		revisaoDTO.setIdRevision(rev.getId());
		revisaoDTO.setRevisionType(revType);
		revisaoDTO.setEntityName(entity.getClass().getSimpleName());
		revisaoDTO.setUser(rev.getMatricula());
		revisaoDTO.setEntityId(id);
		revisaoDTO.setDateRevision(rev.getDtRevisao());
		revisaoDTO.setField(item.getKey());
		revisaoDTO.setValue(item.getValue() != null ? item.getValue().toString() : null);

		if (revisaoObjetoTemporario != null) {
			String valor = recuperaValordoCampo(revisaoObjetoTemporario, item.getKey());
			revisaoDTO.setOldValue(StringUtils.isNotBlank(valor) ? valor : StringUtils.EMPTY);
		} else {
			revisaoDTO.setOldValue(CARGA_INICIAL);
		}
		
		return revisaoDTO;
	}
	
	/**
	 * Consultar lista entidades por id.
	 *
	 * @param entity the entity
	 * @param id the id
	 * @return the list
	 * @throws RegistroNaoEncontradoException the registro nao encontrado exception
	 */
	public List<Object> consultarListaEntidadesPorId(T entity, Long id)
			throws RegistroNaoEncontradoException {
		List<Object> listaQuery = new ArrayList<>();
		if(entity != null && id != null) {
			
			AuditQuery auditQuery = getAuditReader().createQuery().forRevisionsOfEntity(entity.getClass(), false, true);
			auditQuery.add(AuditEntity.id().eq(id));
			listaQuery = auditQuery.getResultList();
		}
		return listaQuery;
	}
	
	
	
	/**
	 * Consultar alteracoes por id revisao.
	 *
	 * @param entidade the entidade
	 * @param idRevisao the id revisao
	 * @return the list
	 * @throws RegistroNaoEncontradoException the registro nao encontrado exception
	 */
	public List<Object> consultarAlteracoesPorIdRevisao(T entidade, Long idRevisao)
			throws RegistroNaoEncontradoException {
		List<Object> listaQuery = new ArrayList<>();
		setAuditReader(AuditReaderFactory.get(getEntityManager()));
		
		if(idRevisao != null) {
			
			AuditQuery auditQuery = getAuditReader().createQuery().forRevisionsOfEntity(entidade.getClass(), false, true);
			
			auditQuery.add(AuditEntity.revisionProperty("id").le(idRevisao));
			auditQuery.add(AuditEntity.id().eq(entidade.getId()));
			auditQuery.addOrder(AuditEntity.revisionProperty("id").desc());
			auditQuery.setMaxResults(2);
			listaQuery = auditQuery.getResultList();
		}
		Collections.reverse(listaQuery); 
		return listaQuery;
	}
	
	/**
	 * Consultar alteracoes por id revisao array.
	 *
	 * @param entidade the entidade
	 * @param idRevisao the id revisao
	 * @return the list
	 * @throws RegistroNaoEncontradoException the registro nao encontrado exception
	 */
	public List<Object[]> consultarAlteracoesPorIdRevisaoArray(T entidade, Long idRevisao)
			throws RegistroNaoEncontradoException {
		List<Object[]> listaQuery = new ArrayList<>();
		setAuditReader(AuditReaderFactory.get(getEntityManager()));
		
		if(idRevisao != null) {
			
			AuditQuery auditQuery = getAuditReader().createQuery().forRevisionsOfEntity(entidade.getClass(), false, true);
			
			auditQuery.add(AuditEntity.revisionProperty("id").le(idRevisao));
			auditQuery.add(AuditEntity.id().eq(entidade.getId()));
			auditQuery.addOrder(AuditEntity.revisionProperty("id").desc());
			auditQuery.setMaxResults(2);
			listaQuery = auditQuery.getResultList();
		}
		Collections.reverse(listaQuery); 
		return listaQuery;
	}
	
	/**
	 * Consultar entidade por id revisao.
	 *
	 * @param clazz the clazz
	 * @param idRevisao the id revisao
	 * @return the object
	 * @throws RegistroNaoEncontradoException the registro nao encontrado exception
	 */
	public Object consultarEntidadePorIdRevisao(Class<T> clazz, Long idRevisao)
			throws RegistroNaoEncontradoException {
		setAuditReader(AuditReaderFactory.get(getEntityManager()));
		
		if(idRevisao != null) {
			AuditQuery auditQuery = getAuditReader().createQuery().forRevisionsOfEntity(clazz, true, true);
			auditQuery.add(AuditEntity.revisionProperty("id").eq(idRevisao));
            return auditQuery.getResultList().stream().findFirst().orElse(null);
		}
          throw new RegistroNaoEncontradoException();
	}
		
	/**
	 * Consultar valor alterado.
	 *
	 * @param entidadeAntiga the entidade antiga
	 * @param entidadeNova the entidade nova
	 * @param atributosValidos the atributos validos
	 * @return the map
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws NoSuchMethodException the no such method exception
	 */
	protected Map<String, Object> consultarValorAlterado(T entidadeAntiga, T entidadeNova, String...atributosValidos)
			throws IllegalAccessException,  InvocationTargetException, NoSuchMethodException {
		Map<String, Object> valoresAlterados = new HashMap<>();
		if (entidadeAntiga != null) {
			for (Field atributo : entidadeAntiga.getClass().getDeclaredFields()) {
				atributo.setAccessible(true);

				if (!atributo.isAnnotationPresent(NotAudited.class)						
						&& isAtributoValido(atributo.getName(), atributosValidos)) {
					compararEntidades(valoresAlterados, atributo, entidadeNova, entidadeAntiga);
				}
			}
		} else {
			for (Field atributo : entidadeNova.getClass().getDeclaredFields()) {
				atributo.setAccessible(true);
				if (!atributo.isAnnotationPresent(NotAudited.class) && isAtributoValido(atributo.getName(), atributosValidos)) {
					adicionaEntidadeNova(valoresAlterados, entidadeNova, atributo);
				}
			}
		}
		return valoresAlterados;
	}

	/**
	 * Adiciona entidade nova.
	 *
	 * @param valoresAlterados the valores alterados
	 * @param entidadeNova the entidade nova
	 * @param atributo the atributo
	 * @throws IllegalAccessException the illegal access exception
	 * @throws NoSuchMethodException the no such method exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	protected void adicionaEntidadeNova(Map<String, Object> valoresAlterados, T entidadeNova, Field atributo)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		String nomeAtributo = atributo.getName();
		Object revisaoNova = atributo.get(entidadeNova);
		if (verificarAtributoChar(atributo)) {
			definirAtributoChar(nomeAtributo, atributo, valoresAlterados, entidadeNova);
		} else if(verificarAtributoEnum(atributo)) {
			definirAtributoEnum(entidadeNova, valoresAlterados, atributo, nomeAtributo);
		}
		else if (!Collection.class.isAssignableFrom(atributo.getType()) && revisaoNova != null) {
			valoresAlterados.put(nomeAtributo, revisaoNova);
		}

	}

	/**
	 * Comparar entidades.
	 *
	 * @param valoresAlterados the valores alterados
	 * @param atributo the atributo
	 * @param entidadeNova the entidade nova
	 * @param entidadeAntiga the entidade antiga
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws NoSuchMethodException the no such method exception
	 */
	private void compararEntidades(Map<String, Object> valoresAlterados, Field atributo, T entidadeNova,
			T entidadeAntiga) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String nomeAtributo = atributo.getName();
		Object revisaoAntiga = atributo.get(entidadeAntiga);
		Object revisaoNova = atributo.get(entidadeNova);
		if (verificarAtributoChar(atributo)) {
			definirAtributoChar(nomeAtributo, atributo, valoresAlterados, entidadeAntiga, entidadeNova);
		} else if(verificarAtributoEnum(atributo)) {
			definirAtributoEnum(entidadeAntiga, entidadeNova, valoresAlterados, atributo, nomeAtributo);
			    
		}
		else if(revisaoAntiga == null && revisaoNova != null && !Collection.class.isAssignableFrom(atributo.getType())) {
			valoresAlterados.put(nomeAtributo, revisaoNova);
		}
        else if (revisaoAntiga != null && revisaoNova != null
            && !revisaoAntiga.toString().equals(revisaoNova.toString())
				&& !Collection.class.isAssignableFrom(atributo.getType())) {
			valoresAlterados.put(nomeAtributo, revisaoNova);
		}
		else if(revisaoAntiga != null && revisaoNova == null && !Collection.class.isAssignableFrom(atributo.getType())) {
			valoresAlterados.put(nomeAtributo, revisaoNova);
		}

	}

	/**
	 * Verificar atributo enum.
	 *
	 * @param atributo the atributo
	 * @return true, if successful
	 */
	private boolean verificarAtributoEnum(Field atributo) {
		return atributo.getType().isEnum();
	}
	
	/**
	 * Definir atributo enum.
	 *
	 * @param entidadeNova the entidade nova
	 * @param valoresAlterados the valores alterados
	 * @param atributo the atributo
	 * @param nomeAtributo the nome atributo
	 * @throws IllegalAccessException the illegal access exception
	 * @throws NoSuchMethodException the no such method exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	private void definirAtributoEnum(T entidadeNova, Map<String, Object> valoresAlterados, Field atributo, String nomeAtributo) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Object enumNovo = atributo.get(entidadeNova);
		if(enumNovo != null) {
			Class<?> clzz = enumNovo.getClass();
		    Method method = clzz.getDeclaredMethod(GET_NAME);
		    String valorNovo = (String) method.invoke(enumNovo);
		    valoresAlterados.put(nomeAtributo, valorNovo);
		}
	}
	
	/**
	 * Definir atributo enum.
	 *
	 * @param entidadeAntiga the entidade antiga
	 * @param entidadeNova the entidade nova
	 * @param valoresAlterados the valores alterados
	 * @param atributo the atributo
	 * @param nomeAtributo the nome atributo
	 * @throws IllegalAccessException the illegal access exception
	 * @throws NoSuchMethodException the no such method exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	private void definirAtributoEnum(T entidadeAntiga, T entidadeNova, Map<String, Object> valoresAlterados, Field atributo, String nomeAtributo) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Object enumAntigo = atributo.get(entidadeAntiga);
		Object enumNovo = atributo.get(entidadeNova);
		if(enumAntigo == null && enumNovo != null) {
			Class<?> clzz = enumNovo.getClass();
			Method method = clzz.getDeclaredMethod(GET_NAME);
			String valorNovo = (String) method.invoke(enumNovo);
			valoresAlterados.put(nomeAtributo, valorNovo);
		}
		else if(enumAntigo != null && enumNovo != null){
			Class<?> clzz = enumAntigo.getClass();
			Method method = clzz.getDeclaredMethod(GET_NAME);
			String valorAntigo = (String) method.invoke(enumAntigo);
			String valorNovo = (String) method.invoke(enumNovo);
            if (!StringUtils.equalsIgnoreCase(valorAntigo, valorNovo)) {
				valoresAlterados.put(nomeAtributo, valorNovo);
            }
		}
	}
	
	/**
	 * Definir atributo char.
	 *
	 * @param nomeAtributo the nome atributo
	 * @param atributo the atributo
	 * @param valoresAlterados the valores alterados
	 * @param entidadeNova the entidade nova
	 * @throws IllegalAccessException the illegal access exception
	 */
	private void definirAtributoChar(String nomeAtributo, Field atributo,
			Map<String, Object> valoresAlterados, T entidadeNova) throws IllegalAccessException {
		Object revisaoNova = atributo.get(entidadeNova);
		if(revisaoNova != null) {
			String revisaoStringNova = new String((char[]) atributo.get(entidadeNova));
			valoresAlterados.put(nomeAtributo, revisaoStringNova);
		}
	}

	/**
	 * Definir atributo char.
	 *
	 * @param nomeAtributo the nome atributo
	 * @param atributo the atributo
	 * @param valoresAlterados the valores alterados
	 * @param entidadeAntiga the entidade antiga
	 * @param entidadeNova the entidade nova
	 * @throws IllegalAccessException the illegal access exception
	 */
	private void definirAtributoChar(String nomeAtributo, Field atributo,
			Map<String, Object> valoresAlterados, T entidadeAntiga, T entidadeNova) throws IllegalAccessException {
		Object revisaoAntiga = atributo.get(entidadeAntiga);
		Object revisaoNova = atributo.get(entidadeNova);
		String revisaoStringAntiga = StringUtils.EMPTY;
		String revisaoStringNova = StringUtils.EMPTY;
		if(revisaoAntiga != null) {
			revisaoStringAntiga = new String((char[]) atributo.get(entidadeAntiga));
		}
		if(revisaoNova != null) {			
			revisaoStringNova = new String((char[]) atributo.get(entidadeNova));
		}
		if (!StringUtils.equals(revisaoStringAntiga, revisaoStringNova)) {
			valoresAlterados.put(nomeAtributo, revisaoStringNova);
		}
	}
	

	/**
	 * Verificar atributo char.
	 *
	 * @param atributo the atributo
	 * @return true, if successful
	 */
	private boolean verificarAtributoChar(Field atributo) {
		return char[].class.equals(atributo.getGenericType());
	}

	/**
	 * Checks if is atributo valido.
	 *
	 * @param name the name
	 * @param atributosValidos the atributos validos
	 * @return true, if is atributo valido
	 */
	protected boolean isAtributoValido(String name, String... atributosValidos) {
		if(atributosValidos != null && atributosValidos.length > 0) {
			return (Arrays.asList(atributosValidos).contains(name));
		}
		return !(name.equals("serialVersionUID"));
	}

	/**
	 * Recupera valordo campo.
	 *
	 * @param entidade the entidade
	 * @param nomeAtributo the nome atributo
	 * @return the string
	 * @throws IllegalAccessException the illegal access exception
	 * @throws NoSuchMethodException the no such method exception
	 * @throws InvocationTargetException the invocation target exception
	 */
    protected String recuperaValordoCampo(T entidade, String nomeAtributo)
        throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		for (Field atributo : entidade.getClass().getDeclaredFields()) {
			if (atributo.getName().equals(nomeAtributo)) {
				atributo.setAccessible(true);
				Object attrObj = atributo.get(entidade);
				if(verificarAtributoChar(atributo)) {
					return new String((char[]) attrObj);
				}
				else if(verificarAtributoEnum(atributo) && attrObj != null) {
					Class<?> clzz = attrObj.getClass();
				    Method method = clzz.getDeclaredMethod(GET_NAME);
				    return (String) method.invoke(attrObj);
				}
				else {
					return attrObj != null ? attrObj.toString() : null; 
				}
				
			}
		}
		return null;
	}

	/**
	 * Numero total revisoes.
	 *
	 * @param id the id
	 * @param atributosValidos the atributos validos
	 * @return the integer
	 * @throws RegistroNaoEncontradoException the registro nao encontrado exception
	 * @throws DAOException the DAO exception
	 */
	public Integer numeroTotalRevisoes(Long id, String...atributosValidos) throws RegistroNaoEncontradoException, DAOException {
		List<EnversDTO> listaDeRevisoes = new CopyOnWriteArrayList<>();
		setAuditReader(AuditReaderFactory.get(em));
		T entidade = consultarPorId((K) id);
		T revisaoObjetoTemporario = null;
		List<Object> revisoesPorId = consultarListaEntidadesPorId(entidade, id);
		try {
			iterarSobreRevisoes(revisoesPorId, listaDeRevisoes, revisaoObjetoTemporario, entidade, id, atributosValidos);
		} catch (Exception e) {
			throw new DAOException();
		}
		listaDeRevisoes.removeIf(list -> list.getRevisionType() == RevisionType.ADD);
		return listaDeRevisoes.size();
	}

	public AuditReader getAuditReader() {
		return reader;
	}

	public void setAuditReader(AuditReader reader) {
		this.reader = reader;
	}

    /**
     * Formata tipo revisao.
     *
     * @param revType the tipo de para auds
     */
    public String formataTipoRevisao(RevisionType revType) {

      if (revType == RevisionType.ADD) {
        return "Inclusão";
      } else if (revType == RevisionType.MOD) {
        return "Alteração";
      } else if (revType == RevisionType.DEL) {
        return "Exclusão";
      }
      return null;
    }

}