package br.gov.pbh.prodabel.hubsmsa.persistence.config;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.apache.commons.collections.CollectionUtils;

public class TypedQueryDecotator<T> implements TypedQuery<T> {

    private TypedQuery<T> typedQuery;

    public TypedQueryDecotator(TypedQuery<T> typedQuery) {
        this.typedQuery = typedQuery;
    }

    @Override
    public int executeUpdate() {
        return typedQuery.executeUpdate();
    }

    @Override
    public int getFirstResult() {
        return typedQuery.getFirstResult();
    }

    @Override
    public FlushModeType getFlushMode() {
        return typedQuery.getFlushMode();
    }

    @Override
    public Map<String, Object> getHints() {
        return typedQuery.getHints();
    }

    @Override
    public LockModeType getLockMode() {
        return typedQuery.getLockMode();
    }

    @Override
    public int getMaxResults() {
        return typedQuery.getMaxResults();
    }

    @Override
    public <T> Parameter<T> getParameter(int arg0, Class<T> arg1) {
        return typedQuery.getParameter(arg0, arg1);
    }

    @Override
    public Parameter<?> getParameter(int arg0) {
        return typedQuery.getParameter(arg0);
    }

    @Override
    public <T> Parameter<T> getParameter(String arg0, Class<T> arg1) {
        return typedQuery.getParameter(arg0, arg1);
    }

    @Override
    public Parameter<?> getParameter(String arg0) {
        return typedQuery.getParameter(arg0);
    }

    @Override
    public Object getParameterValue(int arg0) {
        return typedQuery.getParameterValue(arg0);
    }

    @Override
    public <T> T getParameterValue(Parameter<T> arg0) {
        return typedQuery.getParameterValue(arg0);
    }

    @Override
    public Object getParameterValue(String arg0) {
        return typedQuery.getParameterValue(arg0);
    }

    @Override
    public Set<Parameter<?>> getParameters() {
        return typedQuery.getParameters();
    }

    @Override
    public List<T> getResultList() {
        return typedQuery.getResultList();
    }

    @Override
    public T getSingleResult() {
        List<T> resultado = getResultList();
        if (CollectionUtils.isNotEmpty(resultado)) {
            return resultado.get(0);
        }
        return null;
    }

    @Override
    public boolean isBound(Parameter<?> arg0) {
        return typedQuery.isBound(arg0);
    }

    @Override
    public TypedQuery<T> setFirstResult(int arg0) {
        return typedQuery.setFirstResult(arg0);
    }

    @Override
    public TypedQuery<T> setFlushMode(FlushModeType arg0) {
        return typedQuery.setFlushMode(arg0);
    }

    @Override
    public TypedQuery<T> setHint(String arg0, Object arg1) {
        return typedQuery.setHint(arg0, arg1);
    }

    @Override
    public TypedQuery<T> setLockMode(LockModeType arg0) {
        return typedQuery.setLockMode(arg0);
    }

    @Override
    public TypedQuery<T> setMaxResults(int arg0) {
        return typedQuery.setMaxResults(arg0);
    }

    @Override
    public TypedQuery<T> setParameter(int arg0, Calendar arg1, TemporalType arg2) {
        return typedQuery.setParameter(arg0, arg1, arg2);
    }

    @Override
    public TypedQuery<T> setParameter(int arg0, Date arg1, TemporalType arg2) {
        return typedQuery.setParameter(arg0, arg1, arg2);
    }

    @Override
    public TypedQuery<T> setParameter(int arg0, Object arg1) {
        return typedQuery.setParameter(arg0, arg1);
    }

    @Override
    public TypedQuery<T> setParameter(Parameter<Calendar> arg0, Calendar arg1, TemporalType arg2) {
        return typedQuery.setParameter(arg0, arg1, arg2);
    }

    @Override
    public TypedQuery<T> setParameter(Parameter<Date> arg0, Date arg1, TemporalType arg2) {
        return typedQuery.setParameter(arg0, arg1, arg2);
    }

    @Override
    public TypedQuery<T> setParameter(String arg0, Calendar arg1, TemporalType arg2) {
        return typedQuery.setParameter(arg0, arg1, arg2);
    }

    @Override
    public TypedQuery<T> setParameter(String arg0, Date arg1, TemporalType arg2) {
        return typedQuery.setParameter(arg0, arg1, arg2);
    }

    @Override
    public TypedQuery<T> setParameter(String arg0, Object arg1) {
        return typedQuery.setParameter(arg0, arg1);
    }

    @Override
    public <T> T unwrap(Class<T> arg0) {
        return typedQuery.unwrap(arg0);
    }

    @Override
    public <U extends Object> TypedQuery<T> setParameter(Parameter<U> arg0, U arg1) {
        return typedQuery.setParameter(arg0, arg1);
    }

}
