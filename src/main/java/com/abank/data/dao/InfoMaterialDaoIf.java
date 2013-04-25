package com.abank.data.dao;

import com.abank.data.domain.InfoMaterial;
import com.abank.jsf.InfoMaterialOrderTableEntry;

import java.util.List;

/**
 * Created by: gruppd, 09.02.13 15:13
 */
public interface InfoMaterialDaoIf extends BaseDaoIf<InfoMaterial> {

    List<InfoMaterialOrderTableEntry> findAllTableEntry();

}
