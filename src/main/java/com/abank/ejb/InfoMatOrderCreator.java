package com.abank.ejb;

import com.abank.data.dao.InfoMaterialOrderDaoIf;
import com.abank.data.domain.InfoMaterial;
import com.abank.data.domain.InfoMaterialOrder;
import com.abank.data.domain.Systemuser;
import com.abank.data.producers.LoggedIn;
import com.abank.jsf.InfoMaterialOrderTableEntry;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Stateless
public class InfoMatOrderCreator implements Serializable {

    final static Logger logger = Logger.getLogger(InfoMatOrderCreator.class);

    @Inject
    InfoMaterialOrderDaoIf infoMatOrderDao;

    @Inject
    @LoggedIn
    Systemuser systemUser;

    public void createOrdersNew(List<InfoMaterialOrderTableEntry> tableEntryList) {
        for (InfoMaterialOrderTableEntry tableEntry : tableEntryList) {
            InfoMaterial infoMaterial = tableEntry.getInfoMaterial();
            Integer quantity = tableEntry.getQuantity();
            logger.debug("persisting InfoMaterialOrder " + infoMaterial + " quantity: " + quantity);
            InfoMaterialOrder infoMatOrder = new InfoMaterialOrder(systemUser, infoMaterial, quantity);
            infoMatOrderDao.persist(infoMatOrder);
        }
    }

}
