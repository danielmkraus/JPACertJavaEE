package com.abank.data.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

/**
 * Created by: gruppd, 06.02.13 20:15
 */
@Entity
@NamedQueries({
        @NamedQuery(name = InfoMaterialOrder.QUERY_FIND_ALL, query = "SELECT imo FROM InfoMaterialOrder imo"),
        @NamedQuery(name = InfoMaterialOrder.QUERY_FIND_BY_ID, query = "SELECT imo FROM InfoMaterialOrder imo where imo.id = :id")
})
public class InfoMaterialOrder implements BaseEntityIf, Serializable {

    private static final long serialVersionUID = 8874579108726341280L;

    public static final String QUERY_FIND_ALL = "InfoMaterialOrder.findAll";
    public static final String QUERY_FIND_BY_ID = "InfoMaterialOrder.findById";

    /**
     * ~~~ primary key ~~~
     */

    @Id
    @SequenceGenerator(name = "SEQ_GEN_INFOMATORDER", sequenceName = "SEQ_PK_INFOMATORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_INFOMATORDER")
    private Long id;

    /**
     * ~~~ relationships ~~~
     */

    @ManyToOne
    private Systemuser systemUser;
    
    // TODO: missing link to customer (who is the receiver of the info material)

    @ManyToOne
    private InfoMaterial infoMaterial;

    /**
     * ~~~ properties ~~~
     */

    @Version
    private Integer version;

    @Temporal(TemporalType.DATE)
    private Calendar orderDate;

    private Integer quantity;

    /**
     * ~~~ constructor ~~~
     */
    public InfoMaterialOrder() {
    }

    public InfoMaterialOrder(Systemuser systemUser, InfoMaterial infoMaterial, Integer quantity) {
        this.systemUser = systemUser;
        this.infoMaterial = infoMaterial;
        this.orderDate = Calendar.getInstance();
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

        InfoMaterialOrder that = (InfoMaterialOrder) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (orderDate != null ? !orderDate.equals(that.orderDate) : that.orderDate != null) return false;

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
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
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
        String orderDateString = null;
        String systemUserIdString = null;
        String infoMaterialString = null;
        if (null != orderDate) {
            orderDateString = SDF.format(orderDate.getTime());
        }
        if (null != systemUser.getId()) {
        	systemUserIdString = systemUser.getId().toString();
        }
        if (null != infoMaterial.getId()) {
            infoMaterialString = infoMaterial.getId().toString();
        }


        return "Employee{" +
                ", id=" + id +
                ", version=" + version +
                ", systemUserId='" + systemUserIdString + '\'' +
                ", infoMaterialId='" + infoMaterialString + '\'' +
                ", orderDate=" + orderDateString + '\'' +
                '}';
    }

}
