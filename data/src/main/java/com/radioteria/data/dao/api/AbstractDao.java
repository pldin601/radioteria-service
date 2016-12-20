package com.radioteria.data.dao.api;

import com.radioteria.data.utils.CriteriaCallback;

import java.util.List;

public interface AbstractDao<P, E> {

    void save(E entity);

    void delete(E entity);

    void deleteByKey(P key);

    void flush();

    E load(P id);

    E find(P id);

    E findByPropertyValue(String propertyName, String propertyValue);

    E findByCriteria(CriteriaCallback<E> criteriaCallback);

    List<E> listByPropertyValue(String propertyName, String propertyValue);

    List<E> list();

    List<E> listByCriteria(CriteriaCallback<E> criteriaCallback);

    P findIdByPropertyValue(String propertyName, String propertyValue);

}