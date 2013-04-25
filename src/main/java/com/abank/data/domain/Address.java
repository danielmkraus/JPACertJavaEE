package com.abank.data.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.abank.data.domain.enums.CountryEnum;

@Embeddable
public class Address {

    /**
     * ~~~ properties ~~~
     */

    @Column(name = "STREET")
    private String street;

    @Column(name = "HOUSENUMBER")
    private String number;

    @Column(name = "ZIP_CODE")
    private String zip;

    @Column(name = "TOWN")
    private String town;

    @Column(name = "COUNTRY")
    private String country;

    /**
     * ~~~ constructors ~~~
     */

    public Address() {
    }

    public Address(String street, String hn, String zip, String town, CountryEnum country) {
        this.street = street;
        this.number = hn;
        this.zip = zip;
        this.town = town;
        this.country = country.getStrValue();
    }

    /**
     * ~~~ getters / setters ~~~
     */

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
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
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return the town
     */
    public String getTown() {
        return town;
    }

    /**
     * @param town the town to set
     */
    public void setTown(String town) {
        this.town = town;
    }

    /**
     * @return the country
     */
    public CountryEnum getCountry() {
        return CountryEnum.valueOf(country);
    }

    /**
     * @param country the country to set
     */
    public void setCountry(CountryEnum country) {
        this.country = country.getStrValue();
    }

    /**
     * ~~~ general overrides ~~~
     */

    /**
     * Override of the Object#hashCode()
     *
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((town == null) ? 0 : town.hashCode());
        result = prime * result + ((zip == null) ? 0 : zip.hashCode());
        return result;
    }

    /**
     * Override of Object.equals()
     *
     * @param the object to compare
     * @return a boolean indicating the equality of the object
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (country != other.country)
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (town == null) {
            if (other.town != null)
                return false;
        } else if (!town.equals(other.town))
            return false;
        if (zip == null) {
            if (other.zip != null)
                return false;
        } else if (!zip.equals(other.zip))
            return false;
        return true;
    }

    /**
     * Override of Object#toString()
     *
     * @return textual representation of the object
     */
    @Override
    public String toString() {
        return "Address [street=" + street +
                ", number=" + number +
                ", zip=" + zip +
                ", town=" + town +
                ", country=" + country +
                "]";
    }


}
