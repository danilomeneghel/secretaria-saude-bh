package br.gov.pbh.prodabel.hubsmsa.util.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * Classe utilitaria para abstrair o uso de Criteria API
 * @author claudivan.moreira
 * @param <E> Tipo da entidade a ser utilizada como root da query
 */
public class EntityQuery<E> {

  public enum OrderType {
    ASC, 
    DESC
  }

  private final EntityManager entityManager;

  private final Class<E> entityClass;

  private final CriteriaBuilder criteriaBuilder;

  private final CriteriaQuery<E> criteriaQuery;

  private final CriteriaQuery<Long> countQuery;

  private final Root<E> root;

  private final List<Predicate> predicates = new ArrayList<>();

  private Integer firstResult;

  private Integer maxResults;

  private List<Order> orders = new ArrayList<>();

  /**
   * Instantiates a new entity query.
   *
   * @param entityManager the entity manager
   * @param entityClass the entity class
   * @param isCountQuery the is count query
   */
  private EntityQuery(EntityManager entityManager, Class<E> entityClass, Boolean isCountQuery) {
    this.entityManager = entityManager;
    this.entityClass = entityClass;
    this.criteriaBuilder = entityManager.getCriteriaBuilder();
    this.criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
    this.countQuery = criteriaBuilder.createQuery(Long.class);
    if (isCountQuery) {
      this.root = countQuery.from(criteriaQuery.getResultType());
    } else {
      this.root = criteriaQuery.from(criteriaQuery.getResultType());
    }
  }

  /**
   * Cria um novo EntityQuery que gera queries do tipo 'select col0, col1,... from...'
   * @param entityManager Instancia do EntityManager
   * @param entityClass Classe da entidade para a raiz do select
   */
  public static <T> EntityQuery<T> create(EntityManager entityManager, Class<T> entityClass) {
    return new EntityQuery<>(entityManager, entityClass, Boolean.FALSE);
  }

  /**
   * Cria um novo EntityQuery que monta uma query do tipo 'select count(distinct o.id) from...'
   * @param entityManager Instancia do EntityManager
   * @param entityClass Classe da entidade para a raiz do select
   */
  public static <T> EntityQuery<T> createCount(EntityManager entityManager, Class<T> entityClass) {
    return new EntityQuery<>(entityManager, entityClass, Boolean.TRUE);
  }

  /**
   * Executa a query com os filtros
   * @return Lista de resultados
   */
  public List<E> list() {
    TypedQuery<E> typedQuery = prepareSelectTypedQuery();

    if (firstResult != null) {
      typedQuery.setFirstResult(firstResult);
    }

    if (maxResults != null) {
      typedQuery.setMaxResults(maxResults);
    }

    return typedQuery.getResultList();
  }

  /**
   * Executa a query com os filtros
   * @return Registro do resultado
   */
  public E uniqueResult() {
    TypedQuery<E> typedQuery = prepareSelectTypedQuery();
    return typedQuery.getSingleResult();
  }

  /**
   * Prepare select typed query.
   *
   * @return the typed query
   */
  private TypedQuery<E> prepareSelectTypedQuery() {
    criteriaQuery.select(root);
    criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).orderBy(orders);
    return entityManager.createQuery(criteriaQuery);
  }

  /**
   * <p>Realiza o count do root que atende aos criterios. </p>
   * <b>Atencao:</b> O count com innerJoinFetch nao e suportado, apenas com innerJoin.
   * @return Quantidade de itens que atende aos criterios
   */
  public Long count() {
    Expression<Long> countExp = null;
    if (countQuery.isDistinct()) {
      countExp = criteriaBuilder.countDistinct(this.root);
    } else {
      countExp = criteriaBuilder.count(this.root);
    }
    countQuery.select(countExp);
    countQuery.where(predicates.toArray(new Predicate[predicates.size()]));
    TypedQuery<Long> typedQuery = entityManager.createQuery(countQuery);
    return typedQuery.getSingleResult();
  }

  /**
   * <p>Adiciona uma clausula <i>inner join fetch</i> na query</p>
   * <pre>
   *    innerJoinFetch("pessoa.contato") 
   * </pre>
   * 
   * Ira produzir
   * 
   * <pre>
   *    ... from pessoa p inner join fetch p.contato c
   * </pre>
   * 
   * @param attribute Nome do campo da entidade E para JOIN
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> innerJoinFetch(String attribute) {
    root.fetch(attribute, JoinType.INNER);
    return this;
  }

  /**
   * <p>Adiciona uma clausula <i>inner join</i> na query<p>
   * <pre>
   *    innerJoin("pessoa.contato") 
   * </pre>
   * 
   * Ira produzir
   * 
   * <pre>
   *    ... from pessoa p inner join p.contato c
   * </pre>
   * @param attribute Nome do campo da entidade E para JOIN
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> innerJoin(String attribute) {
    root.join(attribute, JoinType.INNER);
    return this;
  }

  /**
   * <p>Informa que a query deve recuperar somente resultados distintos</p>
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> distinct() {
    countQuery.distinct(true);
    criteriaQuery.distinct(true);
    return this;
  }

  /**
   * <p>Adiciona ordenacao ascendente na query pelo <i>path</i>.</p>
   * <pre>
   *    addAscendingOrderBy("pessoa.contato.rua")
   * </pre>
   * 
   * Ira produzir:
   * 
   * <pre>
   *    ... order by o.id asc
   * </pre>
   * @param path Path para ser usado na ordenacao.
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> addAscendingOrderBy(String path) {
    orders.add(criteriaBuilder.asc(toJpaPath(path)));
    return this;
  }

  /**
   * <p>Adiciona ordenacao descendente na query pelo <i>path</i>.</p>
   * <pre>
   *    addDescendingOrderBy("pessoa.contato.rua")
   * </pre>
   * 
   * Ira produzir:
   * 
   * <pre>
   *    ... order by o.id desc
   * </pre>
   * @param path Path para ser usado na ordenacao.
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> addDescendingOrderBy(String path) {
    orders.add(criteriaBuilder.desc(toJpaPath(path)));
    return this;
  }

  /**
   * <p>Adiciona a ordenacao na query pelo <i>path</i> e na ordem <i>orderType</i>.</p>
   * <pre>
   *    addOrderBy("o.id", "desc")
   *    addOrderBy("o.name", "asc")
   * </pre>
   * 
   * Ira produzir:
   * 
   * <pre>
   *    ... order by o.id desc, o.name asc
   * </pre>
   * @param path Path para ser usado na ordenacao.
   * @param orderType Tipo da ordenacao a ser adicionada asc/desc/ASC/DESC. Ver {@link OrderType}
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> addOrderBy(String path, String orderType) {
    if (OrderType.ASC.toString().equals(orderType)) {
      addAscendingOrderBy(path);
    } else {
      addDescendingOrderBy(path);
    }
    return this;
  }

  /**
   * <p>Adiciona o inicio do range de resultados a serem recuperados.</p>
   * <pre>
   *    setFirstResult(10)
   * </pre>
   * 
   * Ira produzir:
   * 
   * <pre>
   *    ... offset 10
   * </pre>
   * @param firstResult Quantidade de registros que devem ser saltados para iniciar a retornar as linhas
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> setFirstResult(Integer firstResult) {
    this.firstResult = firstResult;
    return this;
  }

  /**
   * <p>Adiciona a quantidade limite de registros que devem ser recuperados.</p>
   * <pre>
   *    setMaxResults(60)
   * </pre>
   * 
   * Ira produzir:
   * 
   * <pre>
   *    ... limit 60
   * </pre>
   * @param maxResults Quantidade limite de registros que devem ser recuperados.
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> setMaxResults(Integer maxResults) {
    this.maxResults = maxResults;
    return this;
  }

  /**
   * <p>Adiciona um valor para testar se corresponde ao path informado na base.</p>
   * <b>Aceita numeros.</b>
   * <pre>
   *    objectEqualsTo("pessoa.idade", 29L)
   *    objectEqualsTo("pessoa.nome", "Claudivan")
   * </pre>
   * 
   * Ira produzir:
   * 
   * <pre>
   *    ... where alias0_.pessoa.idade = 29
   * </pre>
   * 
   * @param path Path para ser usado no predicate <i>equals</i>.
   * @param value Valor para ser testado no predicate <i>equals</i>.
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> objectEqualsTo(String path, Object value) {
    if (value != null) {
      addEqualPredicate(path, value);
    }
    return this;
  }

  /**
   * Object not equals to.
   *
   * @param path the path
   * @param value the value
   * @return the entity query
   */
  public EntityQuery<E> objectNotEqualsTo(String path, Object value) {
    if (value != null) {
      addNotEqualPredicate(path, value);
    }
    return this;
  }

  /**
   * <p>Adiciona um valor para testar se esta contido no path informado na base.</p>
   * 
   * <pre>
   *    objectEqualsTo("pessoa.nome", "Claudi")
   * </pre>
   * 
   * Ira produzir:
   * 
   * <pre>
   *    ... where UPPER(alias0_.pessoa.nome) like ('%CLAUDI%') 
   * </pre>
   * 
   * @param path Path para ser usado no predicate <i>like</i>.
   * @param value Valor para ser testado no predicate <i>like</i>.
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> like(String path, String value) {
    if (StringUtils.isNotBlank(value)) {
      String likeString = new StringBuilder("%")
          .append(value.trim().toUpperCase())
          .append("%")
          .toString();
      predicates.add(criteriaBuilder.like(criteriaBuilder.upper(toJpaPath(path)), likeString));
    }
    return this;
  }

  /**
   * Like unaccent.
   *
   * @param path the path
   * @param value the value
   * @return the entity query
   */
  public EntityQuery<E> likeUnaccent(String path, String value) {
    if (StringUtils.isNotBlank(value)) {
      String likeString =
          new StringBuilder("%").append(value.trim().toUpperCase()).append("%").toString();
      predicates.add(criteriaBuilder.like(criteriaBuilder.function("unaccent", String.class,
          criteriaBuilder.upper(toJpaPath(path))), likeString));
    }
    return this;
  }

  /**
   * <p>Cria filtros usando paths que podem ou nao existir na entidade.</p>
   * 
   * Entidade:
   * <pre>
   *    class Pessoa {
   *        String nome;
   *        //getters and setters
   *    } 
   * </pre>
   * 
   * Paths:
   * <pre>
   *    objectEqualsToPredicate("pessoa.nome", "Claudi")
   *    //Path que nao existe na classe Pessoa
   *    objectEqualsToPredicate("pessoa.endereco.bairro", "Claudi")
   * </pre>
   * 
   * @param path Path para ser usado no predicate <i>equals</i>.
   * @param value Valor para ser testado no predicate <i>equals</i>.
   * @return EntityQuery atualizado
   */
  public Optional<Predicate> objectEqualsToPredicate(String path, Object value) {
    if (value != null) {
      return Optional.of(equalPredicate(path, value));
    }
    return Optional.empty();
  }
  
  /**
   * Object not equals to predicate.
   *
   * @param path the path
   * @param value the value
   * @return the optional
   */
  public Optional<Predicate> objectNotEqualsToPredicate(String path, Object value) {
    if (value != null) {
      return Optional.of(notEqualPredicate(path, value));
    }
    return Optional.empty();
  }

  /**
   * <p>
   * Adicionar filtros com a clausula <i>OR</i>.
   * </p>
   * 
   * <pre>
   *   EntityQuery eq = EntityQuery.create(getEntityManager(), EntityXpto.class);
   *   Optional<Predicate> pIdade = eq.objectEqualsToPredicate("pessoa.idade", 29L);
   *   Optional<Predicate> pSexo = eq.objectEqualsToPredicate("pessoa.genero", "M");
   *   Optional<Predicate> pDataNascimento = eq.objectEqualsToPredicate("pessoa.dataNascimento", "18/09");
   *   
  *   eq.addInDisjunction(pIdade, pSexo).addInDisjunction(pDataNascimento)
   * <pre>
   * 
   * Ira produzir:
   * 
   * <pre>
   *    ... or ( alias0.pessoa.idade = 29 and alias0.pessoa.genero = 'M') 
   *    ... or alias0.pessoa.data_nascimento = '18/09'
   * </pre>
   * 
   * @param optionalPredicates Predicates para adicao.
   * @return EntityQuery atualizado
   */
  @SuppressWarnings("unchecked")
  public EntityQuery<E> addInDisjunction(Optional<Predicate>... optionalPredicates) {
    List<Predicate> predicateList = Arrays.stream(optionalPredicates).filter(Optional::isPresent)
        .map(Optional::get).collect(Collectors.toList());
    if (predicateList.size() > 1) {
      predicates
      .add(criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()])));
    } else if (predicateList.size() == 1) {
      predicates.add(predicateList.get(0));
    }
    return this;
  }

  /**
   * <p>Adiciona um String para testar se corresponde ao path informado na base.</p>
   * <b>Nao aceita numeros.</b>
   * <pre>
   *    stringEqualsTo("pessoa.nome", "Claudivan")
   * </pre>
   * 
   * Ira produzir:
   * 
   * <pre>
   *    ... where alias0_.pessoa.idade = 'Claudivan'
   * </pre>
   * 
   * @param path Path para ser usado no predicate <i>equals</i>.
   * @param value String para ser testada no predicate <i>equals</i>.
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> stringEqualsTo(String path, String value) {
    if (StringUtils.isNotBlank(value)) {
      addEqualPredicate(path, value);
    }
    return this;
  }

  /**
   * <p>Adiciona um filtro logico maior ou igual na query.</p>
   * <b>Nao numeros.</b>
   * 
   * <pre>
   *    greaterThanOrEqualsTo("pessoa.idade", 29L)
   * </pre>
   * 
   * Ira produzir:
   * 
   * <pre>
   *    ... where alias0_.pessoa.idade >= 29
   * </pre>
   * 
   * @param path Path para ser usado no predicate <i>greaterThanOrEqualTo</i>.
   * @param value Valor para ser testada no predicate <i>greaterThanOrEqualTo</i>.
   * @return EntityQuery atualizado
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public EntityQuery<E> greaterThanOrEqualsTo(String path, Comparable comparable) {
    if (Objects.nonNull(comparable)) {
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(toJpaPath(path), comparable));
    }
    return this;
  }

  /**
   * <p>Adiciona um filtro logico menor ou igual na query.</p>
   * <b>Nao numeros.</b>
   * <pre>
   *    lessThanOrEqualsTo("pessoa.idade", 29L)
   * </pre>
   * 
   * Ira Produzir:
   * 
   * <pre>
   *    ... where alias0_.pessoa.idade <= 29
   * </pre>
   * 
   * @param path Path para ser usado no predicate <i>lessThanOrEqualTo</i>.
   * @param value Valor para ser testada no predicate <i>lessThanOrEqualTo</i>.
   * @return EntityQuery atualizado
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public EntityQuery<E> lessThanOrEqualsTo(String path, Comparable comparable) {
    if (Objects.nonNull(comparable)) {
      predicates.add(criteriaBuilder.lessThanOrEqualTo(toJpaPath(path), comparable));
    }
    return this;
  }

  /**
   * <p>Adiciona um filtro para periodo de datas na query.</p>
   * 
   * <pre>
   *    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
   *    Date start = formatter.parse("01/01/2020");
   *    Date end = formatter.parse("31/12/2020");
   *    between("pessoa.dataAtualizacao", start, end);
   * </pre>
   *  
   *  Ira produzir:
   * 
   * <pre>
   *    ... where alias0_.pessoa.data_atualizacao between '2020-01-01' and '2020-12-31';
   * </pre>
   * 
   * @param path Path para ser usado no predicate <i>between</i>.
   * @param firstDate Data inicial para ser testada no predicate <i>between</i>.
   * @param secondDate Data fim para ser testada no predicate <i>between</i>.
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> between(String path, Date firstDate, Date secondDate) {
    if (Objects.nonNull(firstDate) && Objects.nonNull(secondDate)) {
      predicates.add(criteriaBuilder.between(toJpaPath(path), firstDate, secondDate));
    }
    return this;
  }

  /**
   * <p>Adiciona um filtro para lista de valores.</p>
   * <pre>
   * in("pessoa.status", Arrays.asList("A", "I"))
   * </pre>
   *  
   *  Ira produzir:
   * 
   * <pre>
   *    ... where alias0_.pessoa.status in ('A', 'I')
   * </pre>
   * 
   * @param path Path para ser usado no predicate <i>in</i>.
   * @param collection Lista de valores para o predicate <i>in</i>.
   * @return EntityQuery atualizado
   */
  @SuppressWarnings("rawtypes")
  public EntityQuery<E> in(String path, Collection collection) {
    if (CollectionUtils.isNotEmpty(collection)) {
      predicates.add(criteriaBuilder.in(toJpaPath(path)).value(collection));
    }
    return this;
  }

  /**
   * <p>Adiciona a clausula groupBy na query.</p>
   * <pre>
   *    in("pessoa.status")
   * <pre>
   *  
   *  Ira produzir:
   * 
   * <pre>
   *    ... groupBy alias0_.pessoa.status
   * </pre>
   * 
   * @param path Path para ser usado no predicate <i>in</i>.
   * @param collection Lista de valores para o predicate <i>in</i>.
   * @return EntityQuery atualizado
   */
  public EntityQuery<E> groupBy(String path) {
    if (StringUtils.isNotBlank(path)) {
      criteriaQuery.groupBy(toJpaPath(path));
    }
    return this;
  }

  /**
   * Adds the equal predicate.
   *
   * @param path the path
   * @param value the value
   */
  private void addEqualPredicate(String path, Object value) {
    predicates.add(equalPredicate(path, value));
  }

  /**
   * Adds the not equal predicate.
   *
   * @param path the path
   * @param value the value
   */
  private void addNotEqualPredicate(String path, Object value) {
    predicates.add(notEqualPredicate(path, value));
  }

  /**
   * Equal predicate.
   *
   * @param path the path
   * @param value the value
   * @return the predicate
   */
  private Predicate equalPredicate(String path, Object value) {
    return criteriaBuilder.equal(toJpaPath(path), value);
  }

  /**
   * Not equal predicate.
   *
   * @param path the path
   * @param value the value
   * @return the predicate
   */
  private Predicate notEqualPredicate(String path, Object value) {
    return criteriaBuilder.notEqual(toJpaPath(path), value);
  }

  /**
   * To jpa path.
   *
   * @param <T> the generic type
   * @param stringPath the string path
   * @return the path
   */
  private <T> Path<T> toJpaPath(String stringPath) {
    String[] pathParts = StringUtils.split(stringPath, '.');

    assert pathParts != null && pathParts.length > 0 : "Path cannot be empty";

    Path<T> jpaPath = null;
    for (String eachPathPart : pathParts) {
      if (jpaPath == null) {
        jpaPath = root.get(eachPathPart);
      } else {
        jpaPath = jpaPath.get(eachPathPart);
      }
    }

    return jpaPath;
  }

}
