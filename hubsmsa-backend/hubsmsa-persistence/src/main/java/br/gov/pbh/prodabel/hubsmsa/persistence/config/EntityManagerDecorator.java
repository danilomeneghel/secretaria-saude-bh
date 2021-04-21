package br.gov.pbh.prodabel.hubsmsa.persistence.config;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

public class EntityManagerDecorator implements EntityManager {

    private EntityManager entityManager;

    public EntityManagerDecorator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void clear() {
        entityManager.clear();
    }

    @Override
    public void close() {
        entityManager.close();
    }

    @Override
    public boolean contains(Object arg0) {
        return entityManager.contains(arg0);
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String arg0, Class<T> arg1) {
        return new TypedQueryDecotator<>(entityManager.createNamedQuery(arg0, arg1));
    }

    @Override
    public Query createNamedQuery(String arg0) {
        return new QueryDecorator(entityManager.createNamedQuery(arg0));
    }

    @Override
    public Query createNativeQuery(String arg0, Class arg1) {
        return new QueryDecorator(entityManager.createNativeQuery(arg0, arg1));
    }

    @Override
    public Query createNativeQuery(String arg0, String arg1) {
        return new QueryDecorator(entityManager.createNativeQuery(arg0, arg1));
    }

    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
        return null;
    }

    @Override
    public Query createNativeQuery(String arg0) {
        return new QueryDecorator(entityManager.createNativeQuery(arg0));
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> arg0) {
        return new TypedQueryDecotator<>(entityManager.createQuery(arg0));
    }

    @Override
    public Query createQuery(CriteriaUpdate criteriaUpdate) {
        return null;
    }

    @Override
    public Query createQuery(CriteriaDelete criteriaDelete) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createQuery(String arg0, Class<T> arg1) {
        return new TypedQueryDecotator<>(entityManager.createQuery(arg0, arg1));
    }

    @Override
    public Query createQuery(String arg0) {
        return new QueryDecorator(entityManager.createQuery(arg0));
    }

    @Override
    public void detach(Object arg0) {
        entityManager.detach(arg0);
    }

    @Override
    public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2, Map<String, Object> arg3) {
        return entityManager.find(arg0, arg1, arg2, arg3);
    }

    @Override
    public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2) {
        return entityManager.find(arg0, arg1, arg2);
    }

    @Override
    public <T> T find(Class<T> arg0, Object arg1, Map<String, Object> arg2) {
        return entityManager.find(arg0, arg1, arg2);
    }

    @Override
    public <T> T find(Class<T> arg0, Object arg1) {
        return entityManager.find(arg0, arg1);
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    @Override
    public Object getDelegate() {
        return entityManager.getDelegate();
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManager.getEntityManagerFactory();
    }

    @Override
    public FlushModeType getFlushMode() {
        return entityManager.getFlushMode();
    }

    @Override
    public LockModeType getLockMode(Object arg0) {
        return entityManager.getLockMode(arg0);
    }

    @Override
    public Metamodel getMetamodel() {
        return entityManager.getMetamodel();
    }

    @Override
    public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
        return this.entityManager.createEntityGraph(aClass);
    }

    @Override
    public EntityGraph<?> createEntityGraph(String s) {
        return this.entityManager.createEntityGraph(s);
    }

    @Override
    public EntityGraph<?> getEntityGraph(String s) {
        return this.entityManager.getEntityGraph(s);
    }

    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
        return this.entityManager.getEntityGraphs(aClass);
    }

    @Override
    public Map<String, Object> getProperties() {
        return entityManager.getProperties();
    }

    @Override
    public <T> T getReference(Class<T> arg0, Object arg1) {
        return entityManager.getReference(arg0, arg1);
    }

    @Override
    public EntityTransaction getTransaction() {
        return entityManager.getTransaction();
    }

    @Override
    public boolean isOpen() {
        return entityManager.isOpen();
    }

    @Override
    public void joinTransaction() {
        entityManager.joinTransaction();
    }

    @Override
    public boolean isJoinedToTransaction() {
        return false;
    }

    @Override
    public void lock(Object arg0, LockModeType arg1, Map<String, Object> arg2) {
        entityManager.lock(arg0, arg1, arg2);
    }

    @Override
    public void lock(Object arg0, LockModeType arg1) {
        entityManager.lock(arg0, arg1);
    }

    @Override
    public <T> T merge(T arg0) {
        return entityManager.merge(arg0);
    }

    @Override
    public void persist(Object arg0) {
        entityManager.persist(arg0);
    }

    @Override
    public void refresh(Object arg0, LockModeType arg1, Map<String, Object> arg2) {
        entityManager.refresh(arg0, arg1, arg2);
    }

    @Override
    public void refresh(Object arg0, LockModeType arg1) {
        entityManager.refresh(arg0, arg1);
    }

    @Override
    public void refresh(Object arg0, Map<String, Object> arg1) {
        entityManager.refresh(arg0, arg1);
    }

    @Override
    public void refresh(Object arg0) {
        entityManager.refresh(arg0);
    }

    @Override
    public void remove(Object arg0) {
        entityManager.remove(arg0);
    }

    @Override
    public void setFlushMode(FlushModeType arg0) {
        entityManager.setFlushMode(arg0);
    }

    @Override
    public void setProperty(String arg0, Object arg1) {
        entityManager.setProperty(arg0, arg1);
    }

    @Override
    public <T> T unwrap(Class<T> arg0) {
        return entityManager.unwrap(arg0);
    }
}
