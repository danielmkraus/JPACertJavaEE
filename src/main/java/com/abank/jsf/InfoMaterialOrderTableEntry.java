package com.abank.jsf;

import com.abank.data.domain.InfoMaterial;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Table not to be persisted. Used merely for displaying table data on a JSF page.
 *
 * Created by: gruppd, 09.03.13 20:35
 */
public class InfoMaterialOrderTableEntry implements Serializable {

    private InfoMaterial infoMaterial;

    private Integer quantity;

    /**
     * ~~~ constructors ~~~
     */

    public InfoMaterialOrderTableEntry() {
    }

    public InfoMaterialOrderTableEntry(InfoMaterial infoMaterial, Integer quantity) {
        this.infoMaterial = infoMaterial;
        this.quantity = quantity;
    }

    /**
     * ~~~ getters / setters ~~~
     */

    public InfoMaterial getInfoMaterial() {
        return infoMaterial;
    }

    public void setInfoMaterial(InfoMaterial infoMaterial) {
        this.infoMaterial = infoMaterial;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



}
