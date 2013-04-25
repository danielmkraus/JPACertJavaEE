package com.abank.data.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.abank.data.domain.enums.PhoneTypeEnum;

/**
 * @author dgrupp
 *
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Phone.QUERY_FIND_ALL, query = "SELECT b FROM Phone b"),
        @NamedQuery(name = Phone.QUERY_FIND_BY_ID, query = "SELECT b FROM Phone b where b.id = :id")
})
public class Phone implements BaseEntityIf, Serializable {
	
	private static final long serialVersionUID = -71813764371313835L;

    public static final String QUERY_FIND_ALL = "Phone.findAll";
    public static final String QUERY_FIND_BY_ID = "Phone.findById";

	/**
	 * ~~~ properties ~~~
	 */
	@Id
    @SequenceGenerator(name = "SEQ_GEN_PHONE", sequenceName = "SEQ_PK_PHONE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_PHONE")
	private Long id;
	
	@SuppressWarnings("unused")
	@Version 
	private Integer version;
	
	private String type;
	
	private String number;
	
	/**
	 * ~~~ constructor ~~~
	 */
	public Phone() {}

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
	 * @return the type
	 */
	public PhoneTypeEnum getType() {
		return PhoneTypeEnum.strValueOf(type);
	}

	/**
	 * @param type the type to set
	 */
	public void setType(PhoneTypeEnum type) {
		this.type = type.getStrValue();
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * ~~~ general overwrites ~~~
	 */
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phone other = (Phone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Phone [id=" + id + ", type=" + type + ", number=" + number
				+ "]";
	}

}
