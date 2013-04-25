package com.abank.data.dao.impl;

import com.abank.data.dao.ProjectDaoIf;
import com.abank.data.dao.ProjectDaoIf;
import com.abank.data.domain.Project;
import com.abank.data.domain.Project;
import com.abank.data.producers.BankPersUnit;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by: gruppd, 09.02.13 15:17
 */
public class ProjectDao implements ProjectDaoIf {

    @Inject
    @BankPersUnit
    private EntityManager em;

    @Override
    public Project createNew() {
        return new Project();
    }

    @Override
    public void persist(Project project) {
        em.persist(project);
    }

    @Override
    public void merge(Project project) {
        em.merge(project);
    }

    @Override
    public List<Project> findAll() {
        List<Project> retList = em.createNamedQuery(Project.QUERY_FIND_ALL, Project.class).getResultList();
        return retList;
    }

    @Override
    public Project findById(long id) {
        Project project = em.createNamedQuery(Project.QUERY_FIND_BY_ID, Project.class).getSingleResult();
        return project;
    }

    @Override
    public void delete(Project project) {
        em.remove(project);
    }
}
