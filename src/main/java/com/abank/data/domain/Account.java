package com.abank.data.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.abank.data.domain.enums.AccountTypeEnum;

/**
 * Class to represent an account
 *
 * @author dgrupp
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Account.QUERY_FIND_ALL, query = "SELECT b FROM Account b"),
        @NamedQuery(name = Account.QUERY_FIND_BY_ID, query = "SELECT b FROM Account b where b.id = :id")
})
public class Account implements BaseEntityIf, Serializable {

    private static final long serialVersionUID = -6796053568115238411L;

    public static final String QUERY_FIND_ALL = "Account.findAll";
    public static final String QUERY_FIND_BY_ID = "Account.findById";

    /**
     * ~~~ primary key ~~~
     */

    @Id
    @SequenceGenerator(name = "SEQ_GEN_ACCOUNT", sequenceName = "SEQ_PK_ACCOUNT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_ACCOUNT")
    private Long id;

    /**
     * ~~~ relationships ~~~
     */

    // bi-directional relationship
    @ManyToOne
    private Customer customer;

    // bi-directional relationship
    @ManyToOne
    private Branch branch;

    /**
     * ~~~ properties ~~~
     */

    @SuppressWarnings("unused")
    @Version
    private Integer version;

    private String accountType;

    // TODO: add a validator
    private Long bankAccountNumber;

    // TODO: add a validator
    private Long bankIdentifierNumber;

    // TODO: add a validator
    private String iban;

    // TODO: add a validator
    private String swift;

    @Temporal(TemporalType.DATE)
    private Calendar openDate;

    @Temporal(TemporalType.DATE)
    private Calendar closeDate;

    /**
     * ~~~ constructors ~~~
     */
    public Account() {
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
     * @return the accountType
     */
    public String getAccountType() {
        return AccountTypeEnum.valueOf(accountType).getStrValue();
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType.getStrValue();
    }

    /**
     * @return the bankAccountNumber
     */
    public Long getBankAccountNumber() {
        return bankAccountNumber;
    }

    /**
     * @param bankAccountNumber the bankAccountNumber to set
     */
    public void setBankAccountNumber(Long bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    /**
     * @return the bankIdentifierNumber
     */
    public Long getBankIdentifierNumber() {
        return bankIdentifierNumber;
    }

    /**
     * @param bankIdentifierNumber the bankIdentifierNumber to set
     */
    public void setBankIdentifierNumber(Long bankIdentifierNumber) {
        this.bankIdentifierNumber = bankIdentifierNumber;
    }

    /**
     * @return the iban
     */
    public String getIban() {
        return iban;
    }

    /**
     * @param iban the iban to set
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * @return the swift
     */
    public String getSwift() {
        return swift;
    }

    /**
     * @param swift the swift to set
     */
    public void setSwift(String swift) {
        this.swift = swift;
    }

    /**
     * @return the openDate
     */
    public Calendar getOpenDate() {
        return openDate;
    }

    /**
     * @param openDate the openDate to set
     */
    public void setOpenDate(Calendar openDate) {
        this.openDate = openDate;
    }

    /**
     * @return the closeDate
     */
    public Calendar getCloseDate() {
        return closeDate;
    }

    /**
     * @param closeDate the closeDate to set
     */
    public void setCloseDate(Calendar closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
/**
     * ~~~ general overwrites ~~~
     */

    /**
     * overwrite of the equals() method
     *
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountType != null ? !accountType.equals(account.accountType) : account.accountType != null) return false;
        if (bankAccountNumber != null ? !bankAccountNumber.equals(account.bankAccountNumber) : account.bankAccountNumber != null)
            return false;
        if (bankIdentifierNumber != null ? !bankIdentifierNumber.equals(account.bankIdentifierNumber) : account.bankIdentifierNumber != null)
            return false;
        if (closeDate != null ? !closeDate.equals(account.closeDate) : account.closeDate != null) return false;
        if (iban != null ? !iban.equals(account.iban) : account.iban != null) return false;
        if (id != null ? !id.equals(account.id) : account.id != null) return false;
        if (openDate != null ? !openDate.equals(account.openDate) : account.openDate != null) return false;
        if (swift != null ? !swift.equals(account.swift) : account.swift != null) return false;

        return true;
    }

    /**
     * overwrite of the hashCode() method
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        result = 31 * result + (bankAccountNumber != null ? bankAccountNumber.hashCode() : 0);
        result = 31 * result + (bankIdentifierNumber != null ? bankIdentifierNumber.hashCode() : 0);
        result = 31 * result + (iban != null ? iban.hashCode() : 0);
        result = 31 * result + (swift != null ? swift.hashCode() : 0);
        result = 31 * result + (openDate != null ? openDate.hashCode() : 0);
        result = 31 * result + (closeDate != null ? closeDate.hashCode() : 0);
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
        String openDateString = (null == openDate) ? null : SDF.format(openDate.getTime());
        String closeDateString = (null == closeDate) ? null : SDF.format(closeDate.getTime());
        return "Account [id=" + id +
                ", accountType=" + accountType +
                ", bankAccountNumber=" + bankAccountNumber +
                ", bankIdentifierNumber=" + bankIdentifierNumber +
                ", iban=" + iban +
                ", swift=" + swift +
                ", openDate=" + openDateString +
                ", closeDate=" + closeDateString +
                "]";
    }


}
