package com.abank.data.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by: gruppd, 23.03.13 12:33
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(name = Project.QUERY_FIND_ALL, query = "SELECT p FROM Project p"),
        @NamedQuery(name = Project.QUERY_FIND_BY_ID, query = "SELECT p FROM Project p where p.id = :id")
})
public class Project implements BaseEntityIf, Serializable {

    public static final String QUERY_FIND_ALL = "Project.findAll";
    public static final String QUERY_FIND_BY_ID = "Project.findById";

    /**
     * ~~~ primary key ~~~
     */

    @Id
    @SequenceGenerator(name = "SEQ_GEN_PROJECT", sequenceName = "SEQ_PK_PROJECT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_PROJECT")
    private Long id;

    /**
     * ~~~ relationships ~~~
     */

    @ManyToMany
    private List<Employee> employees;

    /**
     * ~~~ properties ~~~
     */

    private String name;

    /**
     * ~~~ constructor ~~~
     */
    public Project() {
    }

    /**
     * ~~~ getters / setters ~~~
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * ~~~ general overwrites ~~~
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (employees != null ? !employees.equals(project.employees) : project.employees != null) return false;
        if (id != null ? !id.equals(project.id) : project.id != null) return false;
        if (name != null ? !name.equals(project.name) : project.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (employees != null ? employees.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project {" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
