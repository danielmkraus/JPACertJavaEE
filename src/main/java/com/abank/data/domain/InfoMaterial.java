package com.abank.data.domain;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 * Class to represent info material
 *
 * @author dgrupp
 */
@Entity
@NamedQueries(value = {
        @NamedQuery(name = InfoMaterial.QUERY_FIND_ALL, query = " SELECT i FROM InfoMaterial i "),
        @NamedQuery(name = InfoMaterial.QUERY_FIND_BY_ID, query = " SELECT i FROM InfoMaterial i where i.id = :id "),
        @NamedQuery(name = InfoMaterial.QUERYID_FIND_ALL_NEW, query = InfoMaterial.QUERYSQL_FIND_ALL_NEW)
})
public class InfoMaterial implements BaseEntityIf, Serializable {

    private static final long serialVersionUID = 8527635989042152293L;

    public static final Logger logger = Logger.getLogger(InfoMaterial.class.getCanonicalName());

    public static final String QUERY_FIND_ALL = "InfoMaterial.findAll";
    public static final String QUERY_FIND_BY_ID = "InfoMaterial.findById";
    public static final String QUERYID_FIND_ALL_NEW = "InfoMaterial.findAllNew";
    public static final String QUERYSQL_FIND_ALL_NEW = " SELECT NEW com.abank.jsf.InfoMaterialOrderTableEntry(i, 0) FROM InfoMaterial i ";

    /**
     * ~~~ primary key ~~~
     */

    @Id
    @SequenceGenerator(name = "SEQ_GEN_INFOMAT", sequenceName = "SEQ_PK_INFOMAT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_INFOMAT")
    private Long id;

    /**
     * ~~~ properties ~~~
     */

    private String title;

    //@NotNull
    //@Pattern(regexp = "(([0-9]){6})-([0-9]){3})")
    private String articleNumber;

    /**
     * ~~~ constructor ~~~
     */
    public InfoMaterial() {
    }

    public InfoMaterial(String articleNumber, String title) {
        this.articleNumber = articleNumber;
        this.title = title;
    }

    public InfoMaterial(InfoMaterial infoMaterial) {
        this.id = infoMaterial.id;
        this.articleNumber = infoMaterial.articleNumber;
        this.title = infoMaterial.title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
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

        InfoMaterial that = (InfoMaterial) o;

        if (articleNumber != null ? !articleNumber.equals(that.articleNumber) : that.articleNumber != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

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
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (articleNumber != null ? articleNumber.hashCode() : 0);
        return result;
    }

    /**
     * Override of Object#toString()
     *
     * @return textual representation of the object
     */
    @Override
    public String toString() {
        return "InfoMaterial{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", articleNumber='" + articleNumber + '\'' +
                '}';
    }
}
