package com.abank.data.helper;

import java.util.ArrayList;
import java.util.List;

import com.abank.data.domain.InfoMaterial;

/**
 * Created by: gruppd, 09.02.13 16:16
 */
public class InfoMaterialFactory {

    private InfoMaterialFactory() {
    }

    public static void main(String [] args) {
        List<InfoMaterial> il = createInfoMaterialFactoryList();
        for (InfoMaterial ifn : il) {
            System.out.println(ifn.toString());
        }
    }

    public static List<InfoMaterial> createInfoMaterialFactoryList() {
        List<InfoMaterial> infoMaterialFactoryList = new ArrayList<InfoMaterial>();
        for (int i = 0; i < DataArray.infoMaterial.length; i++) {
            InfoMaterial infoMaterial = new InfoMaterial(generateArticleNumber(), DataArray.infoMaterial[i]);
            infoMaterialFactoryList.add(infoMaterial);
        }
        return infoMaterialFactoryList;
    }

    private static String generateArticleNumber() {
        // "(([0-9]){6})-([0-9]){3})")
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<9; i++) {
            if (6 == i) {
                sb.append("-");
            }
            Integer randomNumber = DataCreatorHelper.randIntBetween(0,9);
            sb.append(randomNumber.toString());
        }

        return sb.toString();
    }


}
