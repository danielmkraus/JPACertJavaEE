package com.abank.data.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * Entity department
 *
 * Created by: gruppd, 06.02.13 20:14
 */
@Entity
@Table(name = "DEPARTMENT")
@NamedQueries({
        @NamedQuery(name = Department.QUERY_FIND_ALL, query = "SELECT b FROM Department b"),
        @NamedQuery(name = Department.QUERY_FIND_BY_ID, query = "SELECT b FROM Department b where b.id = :id")
})
public class Department implements BaseEntityIf, Serializable {

    private static final long serialVersionUID = -8029274929211990303L;

    public static final String QUERY_FIND_ALL = "Department.findAll";
    public static final String QUERY_FIND_BY_ID = "Department.findById";

    /**
     * ~~~ primary key ~~~
     */

    @Id
    @SequenceGenerator(name = "SEQ_GEN_DEPARTMENT", sequenceName = "SEQ_PK_DEPARTMENT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_DEPARTMENT")
    private Long id;

    /**
     * ~~~ relationships ~~~
     */

    @OneToMany(mappedBy = "department")
    private Set<Employee> employees;

    /**
     * to nominate e.g. the number of customers to be trained / educated in a training room
     * Since value is an entity type the map is stored as a relationship in an entity table.
     * The Customer-table will hold two columns - 1: the employee id 2: the string of the training room
     * TODO: update relationship to comply with DataFactory!!!
     */
    @OneToMany(mappedBy="department")
    @MapKeyColumn(name="CUB_ID")
    private Map<String, Employee> employeesByCubicle;


    /**
     * ~~~ properties ~~~
     */

    @SuppressWarnings("unused")
    @Version
    private Integer version;

    private String departmentName;

    /**
     * ~~~ constructor ~~~
     */
    public Department() {
    }

    public Department(String departmentName) {
        this.departmentName = departmentName;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Set<Employee> getEmployees() {
        if (null == employees) {
            employees = new HashSet<Employee>();
        }
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    /**
     * ~~~ general overwrites ~~~
     */

    /**
     * Override of Object.equals()
     *
     * @param o the object to compare
     * @return a boolean indicating the equality of the object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (departmentName != null ? !departmentName.equals(that.departmentName) : that.departmentName != null)
            return false;
        if (employees != null ? !employees.equals(that.employees) : that.employees != null) return false;
        return id.equals(that.id);

    }

    /**
     * Override of the Object#hashCode()
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        result = 31 * result + (employees != null ? employees.hashCode() : 0);
        return result;
    }

    /**
     * Override of Object#toString()
     *
     * @return textual representation of the object
     */
    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}