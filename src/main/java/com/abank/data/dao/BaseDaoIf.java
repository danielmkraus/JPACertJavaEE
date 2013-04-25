package com.abank.data.dao;

import com.abank.data.domain.BaseEntityIf;
import com.abank.data.domain.Employee;

import java.util.List;

/**
 * Created by: gruppd, 09.02.13 15:10
 */
public interface BaseDaoIf<T extends BaseEntityIf> {

    T createNew();

    void persist(T entity);

    List<T> findAll();

    T findById(long id);

    void delete(T entity);

    void merge(T entity);

}
