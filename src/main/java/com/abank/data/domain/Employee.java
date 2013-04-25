package com.abank.data.domain;

import com.abank.data.domain.enums.JobRoleEnum;
import com.abank.data.domain.enums.PhoneTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by: gruppd, 06.02.13 20:15
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Employee.QUERY_FIND_ALL, query = "SELECT b FROM Employee b"),
        @NamedQuery(name = Employee.QUERY_FIND_BY_ID, query = "SELECT b FROM Employee b where b.id = :id")
})
public class Employee implements BaseEntityIf, Serializable {

    private static final long serialVersionUID = 4628401176088768783L;

    public static final String QUERY_FIND_ALL = "Employee.findAll";
    public static final String QUERY_FIND_BY_ID = "Employee.findById";

    /**
     * ~~~ primary key ~~~
     */

    @Id
    @Column(name = "EMP_ID")
    @SequenceGenerator(name = "SEQ_GEN_EMPLOYEE", sequenceName = "SEQ_PK_EMPLOYEE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_EMPLOYEE")
    private Long id;

    /**
     * ~~~ relationships ~~~
     */

    // bi-directional relationship
//    @OneToOne(mappedBy="employee")
//    private Systemuser systemUser;

    // bi-directional relationship
    @ManyToOne
    private Branch branch;

    // bi-directional relationship
    @ManyToOne
    private Department department;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "EMPLOYEE_PHONE", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"), inverseJoinColumns = @JoinColumn(name = "PHONE_ID"))
    private List<Phone> phoneNr;

    /**
     * Both key and value is a basic type.
     * Since value is basic type the map is stored in an element collection table.
     * Since none of them is unique only the combination can serve as a primary key.
     * TODO: wurde ein PK constraint erstellt?
     */
    @ElementCollection
    @CollectionTable(name = "EMP_PHONE")     // the name of the collection table
    @MapKeyColumn(name = "PHONE_TYPE")       // the name of the key column
    @Column(name = "PHONE_NUM")              // the name of the value column
    private Map<String, String> phoneNrKeyBasicTypeElementBasicType;

    /**
     * Key is an enumeration and value is a basic type.
     * Since value is basic type the map is stored in an element collection table.
     */
    @ElementCollection
    @CollectionTable(name = "EMP_PHONE_ENUMERATED") // the name of the collection table
    @MapKeyEnumerated(EnumType.STRING)              // specifies how the enumeration is being stored (or: @MapKeyTemporal)
    @MapKeyColumn(name = "PHONE_TYPE")              // the name of the key column
    @Column(name = "PHONE_NUM")                     // the name of the value column
    private Map<PhoneTypeEnum, String> phoneNrKeyEnumTypeElementBasicType;



    @ManyToOne
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> directs = new ArrayList<Employee>();

    @ManyToMany(mappedBy = "employees")
    private List<Project> projects = new ArrayList<Project>();

    /**
     * ~~~ properties ~~~
     */

    @SuppressWarnings("unused")
    @Version
    private Integer version;

    private String firstname;

    private String lastname;

    @Temporal(TemporalType.DATE)
    private Calendar birthDate;

    @Temporal(TemporalType.DATE)
    private Calendar startDate;

    @Temporal(TemporalType.DATE)
    private Calendar endDate;

    private Long salary;

    private String jobRole;

    @Column(precision = 10, scale = 2)
    private BigDecimal turnover;

    private Boolean unionMember;

    /**
     * ~~~ constructor ~~~
     */
    public Employee() {
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public List<Phone> getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(List<Phone> phoneNr) {
        this.phoneNr = phoneNr;
    }

    public JobRoleEnum getJobRole() {
        return JobRoleEnum.strValueOf(jobRole);
    }

    public void setJobRole(JobRoleEnum jobRole) {
        this.jobRole = jobRole.getStrValue();
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public BigDecimal getTurnover() {
        return turnover;
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getDirects() {
        return directs;
    }

    public void setDirects(List<Employee> directs) {
        this.directs = directs;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Boolean getUnionMember() {
        return unionMember;
    }

    public void setUnionMember(Boolean unionMember) {
        this.unionMember = unionMember;
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

        Employee employee = (Employee) o;

        if (birthDate != null ? !birthDate.equals(employee.birthDate) : employee.birthDate != null) return false;
        if (endDate != null ? !endDate.equals(employee.endDate) : employee.endDate != null) return false;
        if (firstname != null ? !firstname.equals(employee.firstname) : employee.firstname != null) return false;
        if (!id.equals(employee.id)) return false;
        if (jobRole != employee.jobRole) return false;
        if (lastname != null ? !lastname.equals(employee.lastname) : employee.lastname != null) return false;
        if (salary != null ? !salary.equals(employee.salary) : employee.salary != null) return false;
        if (startDate != null ? !startDate.equals(employee.startDate) : employee.startDate != null) return false;
        if (turnover != null ? !turnover.equals(employee.turnover) : employee.turnover != null) return false;

        return true;
    }

    /**
     * Override of the Object#hashCode()
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (jobRole != null ? jobRole.hashCode() : 0);
        result = 31 * result + (turnover != null ? turnover.hashCode() : 0);
        return result;
    }

    /**
     * Override of Object#toString()
     *
     * @return textual representation of the object
     */
    @Override
    public String toString() {
        SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy");
        String birthDateString = (null == birthDate) ? null : SDF.format(birthDate.getTime());
        String startDateString = (null == startDate) ? null : SDF.format(startDate.getTime());
        String endDateString = (null == endDate) ? null : SDF.format(endDate.getTime());

        return "Employee{" +
                ", id=" + id +
                ", version=" + version +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthDate=" + birthDateString +
                ", startDate=" + startDateString +
                ", endDate=" + endDateString +
                ", salary=" + salary +
                ", jobRole=" + jobRole +
                ", turnover=" + turnover +
                '}';
    }

}
