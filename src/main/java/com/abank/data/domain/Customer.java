package com.abank.data.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;

/**
 * @author dgrupp
 */
@Entity
@Table(name = "CUSTOMER")
@NamedQueries({
        @NamedQuery(name = Customer.QUERYID_FIND_ALL, query = Customer.QUERYSQL_FIND_ALL),
        @NamedQuery(name = Customer.QUERYID_FIND_BY_FLB, query = Customer.QUERYSQL_FIND_BY_FLB),
        @NamedQuery(name = Customer.QUERYID_FIND_BY_ID, query = Customer.QUERYSQL_FIND_BY_ID)
})
public class Customer implements BaseEntityIf, Serializable {

    private static final long serialVersionUID = 8527635989042152293L;

    public static final String QUERYID_FIND_ALL = "Customer.findAll";
    public static final String QUERYSQL_FIND_ALL = "SELECT b FROM Customer b";
    public static final String QUERYID_FIND_BY_ID = "Customer.findById";
    public static final String QUERYSQL_FIND_BY_ID = "SELECT b FROM Customer b WHERE b.id = :id";
    public static final String QUERYID_FIND_BY_FLB = "Customer.findByFLB";
    public static final String QUERYSQL_FIND_BY_FLB = "SELECT b FROM Customer b WHERE b.firstname = :firstname AND b.lastname = :lastname AND b.birthDate = :birthdate";

    /**
     * ~~~ primary key ~~~
     */

    @Id
    @SequenceGenerator(name = "SEQ_GEN_CUSTOMER", sequenceName = "SEQ_PK_CUSTOMER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_CUSTOMER")
    private Long id;

    /**
     * ~~~ relationships ~~~
     */

    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "CUSTOMER_PHONE", joinColumns = @JoinColumn(name = "CUSTOMER_ID"), inverseJoinColumns = @JoinColumn(name = "PHONE_ID"))
    private List<Phone> phoneNr;

    // bi-directional relationship
    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<Account> accounts;

    /**
     * ~~~ properties ~~~
     */

    private String firstname;

    private String lastname;

    @Temporal(TemporalType.DATE)
    private Calendar birthDate;

    /**
     * ~~~ constructor ~~~
     */
    public Customer() {
    }

    public Customer(String firstname, String lastname, Calendar birthDate, Address address, List<Phone> phoneList) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNr = phoneList;
    }

    /**
     * ~~~ getters / setters ~~~
     */

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the phoneNr
     */
    public List<Phone> getPhoneNr() {
        return phoneNr;
    }

    /**
     * @param phoneNr the phoneNr to set
     */
    public void setPhoneNr(List<Phone> phoneNr) {
        this.phoneNr = phoneNr;
    }

    /**
     * @return the birthDate
     */
    public Calendar getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the accounts
     */
    public List<Account> getAccounts() {
        if (null == accounts) {
            accounts = new ArrayList<Account>();
        }
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
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

        Customer customer = (Customer) o;

        if (accounts != null ? !accounts.equals(customer.accounts) : customer.accounts != null) return false;
        if (address != null ? !address.equals(customer.address) : customer.address != null) return false;
        if (birthDate != null ? !birthDate.equals(customer.birthDate) : customer.birthDate != null) return false;
        if (firstname != null ? !firstname.equals(customer.firstname) : customer.firstname != null) return false;
        if (id != null ? !id.equals(customer.id) : customer.id != null) return false;
        if (lastname != null ? !lastname.equals(customer.lastname) : customer.lastname != null) return false;
        if (phoneNr != null ? !phoneNr.equals(customer.phoneNr) : customer.phoneNr != null) return false;

        return true;
    }

    /**
     * Override of the Object#hashCode()
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (phoneNr != null ? phoneNr.hashCode() : 0);
        result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
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

        return "Customer [id=" + id +
                ", firstname=" + firstname +
                ", lastname=" + lastname +
                ", address=" + address +
                ", birthDate=" + birthDateString +
                ", phone=" + phoneNr +
                "]";
    }


}
