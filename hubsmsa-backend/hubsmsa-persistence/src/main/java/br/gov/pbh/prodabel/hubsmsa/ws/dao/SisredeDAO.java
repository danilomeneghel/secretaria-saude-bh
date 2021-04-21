package br.gov.pbh.prodabel.hubsmsa.ws.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.ValidatorBase;
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
public abstract class SisredeDAO<K extends Serializable, T extends EntidadeBase<K>> {

  @PersistenceContext(unitName = "sisrede")
  private EntityManager em;

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
    return (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
        .getActualTypeArguments()[1];
  }

  @SuppressWarnings("unused")
  private Class<?> getTypeClassDTO() {
    return (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
        .getActualTypeArguments()[2];
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

}
