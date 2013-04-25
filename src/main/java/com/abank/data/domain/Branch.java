package com.abank.data.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity branch
 * Created by: gruppd, 06.02.13 20:23
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Branch.QUERY_FIND_ALL, query = "SELECT b FROM Branch b"),
        @NamedQuery(name = Branch.QUERY_FIND_BY_ID, query = "SELECT b FROM Branch b where b.id = :id")
})
public class Branch implements BaseEntityIf, Serializable {

    private static final long serialVersionUID = -4495888449462905129L;

    public static final String QUERY_FIND_ALL = "Branch.findAll";
    public static final String QUERY_FIND_BY_ID = "Branch.findById";

    /**
     * ~~~ primary key ~~~
     */

    @Id
    @SequenceGenerator(name = "SEQ_GEN_BRANCH", sequenceName = "SEQ_PK_BRANCH", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_BRANCH")
    private Long id;

    /**
     * ~~~ relationships ~~~
     */

    @Embedded
    private Address address;

    // uni-directional relationship
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Department> departments;

    // bi-directional relationship
    @OneToMany(mappedBy = "branch")
    private Set<Employee> employees;

    // bi-directional relationship
    @OneToMany(mappedBy = "branch")
    private List<Account> accounts;

    /**
     * ~~~ properties ~~~
     */

    @SuppressWarnings("unused")
    @Version
    private Integer version;

    private String branchName;

    /**
     * ~~~ constructor ~~~
     */
    public Branch() {
    }

    public Branch(String branchName, Address address) {
        this.branchName = branchName;
        this.address = address;
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

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Department> getDepartments() {
        if (null == departments) {
            departments = new ArrayList<Department>();
        }
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
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

        Branch branch = (Branch) o;

        if (address != null ? !address.equals(branch.address) : branch.address != null) return false;
        if (departments != null ? !departments.equals(branch.departments) : branch.departments != null) return false;
        if (employees != null ? !employees.equals(branch.employees) : branch.employees != null) return false;
        if (id != null ? !id.equals(branch.id) : branch.id != null) return false;
        return !(branchName != null ? !branchName.equals(branch.branchName) : branch.branchName != null);

    }

    /**
     * Override of the Object#hashCode()
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (branchName != null ? branchName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (departments != null ? departments.hashCode() : 0);
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
        return "Branch{" +
                "id=" + id +
                ", branchName='" + branchName +
                ", address=" + address +
                '}';
    }
}
