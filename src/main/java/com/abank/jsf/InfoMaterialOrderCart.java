package com.abank.jsf;

import com.abank.data.domain.InfoMaterial;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Managed Bean to hold the state of a cart
 *
 * Created by: gruppd, 26.02.13 21:02
 */
public class InfoMaterialOrderCart implements Serializable {

	private static final long serialVersionUID = -6873715733611326525L;

	final static Logger logger = Logger.getLogger(InfoMaterialOrderCart.class);

    Map<InfoMaterial, Integer> infoMaterialMap = null;

    @PostConstruct
    public void postConstruct() {
        logger.info("InfoMaterialOrderCart object created");
        logger.debug("initializing infoMaterialMap");
        infoMaterialMap = new HashMap<InfoMaterial, Integer>();
    }

    @PreDestroy
    public void preDestroy(){
        logger.info("InfoMaterialOrderCart object destroyed");
    }

    /**
     * @param infoMaterial
     *            info material ("product") to be ordered
     * @param quantity
     *            quantity of each info material
     */
    public void updateCart(InfoMaterial infoMaterial, Integer quantity) {
        logger.debug("trying to update order for " + infoMaterial + " with quantity " + quantity);
        if (infoMaterialMap.containsKey(infoMaterial)) {
            logger.debug("infoMaterial already in trolley. Will only try to update quanity.");
            try {
                Integer resultQuantity = infoMaterialMap.get(infoMaterial) + quantity;
                if (resultQuantity < 0) {
                    logger.debug("resulting quantity (" + resultQuantity + ") must be >= 0");
                    throw new IllegalArgumentException("resulting quantity must be >= 0");
                } else if (resultQuantity == 0) {
                    logger.debug("resultQuantity = " + resultQuantity + " - will remove infoMaterial from trolley");
                    infoMaterialMap.remove(infoMaterial);
                } else {
                    logger.debug("resultQuantity = " + resultQuantity);
                    infoMaterialMap.remove(infoMaterial);
                    infoMaterialMap.put(infoMaterial, resultQuantity);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } else {
            logger.debug("Adding article " + infoMaterial.toString() + " to trolley; quantity = " + quantity);
            infoMaterialMap.put(infoMaterial, quantity);
        }
    }

    /**
     * Method to return the trolley content
     *
     * @return the trolley content
     */
    public Map<InfoMaterial, Integer> getInfoMaterialMap() {
        return infoMaterialMap;
    }
}
