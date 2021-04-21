package br.gov.pbh.prodabel.hubsmsa.persistence.config;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.apache.commons.collections.CollectionUtils;

public class QueryDecorator implements Query {

    private Query query;

    public QueryDecorator(Query query) {
        this.query = query;
    }

    @Override
    public int executeUpdate() {
        return query.executeUpdate();
    }

    @Override
    public int getFirstResult() {
        return query.getFirstResult();
    }

    @Override
    public FlushModeType getFlushMode() {
        return query.getFlushMode();
    }

    @Override
    public Map<String, Object> getHints() {
        return query.getHints();
    }

    @Override
    public LockModeType getLockMode() {
        return query.getLockMode();
    }

    @Override
    public int getMaxResults() {
        return query.getMaxResults();
    }

    @Override
    public <T> Parameter<T> getParameter(int arg0, Class<T> arg1) {
        return query.getParameter(arg0, arg1);
    }

    @Override
    public Parameter<?> getParameter(int arg0) {
        return query.getParameter(arg0);
    }

    @Override
    public <T> Parameter<T> getParameter(String arg0, Class<T> arg1) {
        return query.getParameter(arg0, arg1);
    }

    @Override
    public Parameter<?> getParameter(String arg0) {
        return query.getParameter(arg0);
    }

    @Override
    public Object getParameterValue(int arg0) {
        return query.getParameterValue(arg0);
    }

    @Override
    public <T> T getParameterValue(Parameter<T> arg0) {
        return query.getParameterValue(arg0);
    }

    @Override
    public Object getParameterValue(String arg0) {
        return query.getParameterValue(arg0);
    }

    @Override
    public Set<Parameter<?>> getParameters() {
        return query.getParameters();
    }

    @Override
    public List getResultList() {
        return query.getResultList();
    }

    @Override
    public Object getSingleResult() {
        List resultado = getResultList();
        if (CollectionUtils.isNotEmpty(resultado)) {
            return resultado.get(0);
        }
        return null;
    }

    @Override
    public boolean isBound(Parameter<?> arg0) {
        return query.isBound(arg0);
    }

    @Override
    public Query setFirstResult(int arg0) {
        return query.setFirstResult(arg0);
    }

    @Override
    public Query setFlushMode(FlushModeType arg0) {
        return query.setFlushMode(arg0);
    }

    @Override
    public Query setHint(String arg0, Object arg1) {
        return query.setHint(arg0, arg1);
    }

    @Override
    public Query setLockMode(LockModeType arg0) {
        return query.setLockMode(arg0);
    }

    @Override
    public Query setMaxResults(int arg0) {
        return query.setMaxResults(arg0);
    }

    @Override
    public Query setParameter(int arg0, Calendar arg1, TemporalType arg2) {
        return query.setParameter(arg0, arg1, arg2);
    }

    @Override
    public Query setParameter(int arg0, Date arg1, TemporalType arg2) {
        return query.setParameter(arg0, arg1, arg2);
    }

    @Override
    public Query setParameter(int arg0, Object arg1) {
        return query.setParameter(arg0, arg1);
    }

    @Override
    public Query setParameter(Parameter<Calendar> arg0, Calendar arg1, TemporalType arg2) {
        return query.setParameter(arg0, arg1, arg2);
    }

    @Override
    public Query setParameter(Parameter<Date> arg0, Date arg1, TemporalType arg2) {
        return query.setParameter(arg0, arg1, arg2);
    }

    @Override
    public <T> Query setParameter(Parameter<T> arg0, T arg1) {
        return query.setParameter(arg0, arg1);
    }

    @Override
    public Query setParameter(String arg0, Calendar arg1, TemporalType arg2) {
        return query.setParameter(arg0, arg1, arg2);
    }

    @Override
    public Query setParameter(String arg0, Date arg1, TemporalType arg2) {
        return query.setParameter(arg0, arg1, arg2);
    }

    @Override
    public Query setParameter(String arg0, Object arg1) {
        return query.setParameter(arg0, arg1);
    }

    @Override
    public <T> T unwrap(Class<T> arg0) {
        return query.unwrap(arg0);
    }

}
