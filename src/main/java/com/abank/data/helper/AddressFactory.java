package com.abank.data.helper;

import com.abank.data.domain.Address;
import com.abank.data.domain.enums.CountryEnum;

import java.util.Map;
import java.util.Random;

/**
 * Created by: gruppd, 11.02.13 19:02
 */
public class AddressFactory {

    private static Random generator = new Random();
    private static Map<String, String> zipAreaCode;

    private AddressFactory() {
    }

    public static Address generateRandomAddress(String town) {
        zipAreaCode = DataArray.initializeZipAreaCode();
        return new Address(getRandomStreet(), getRandomHouseNr(), getRandomZip(town), town, CountryEnum.GERMANY);
    }

    public static void main(String[] args) {
        System.out.println(generateRandomAddress("Muenchen"));
        System.out.println(generateRandomAddress("Hamburg"));
        System.out.println(generateRandomAddress("Rostock"));
    }

    private static String getRandomZip(String town) {
        StringBuffer sb = new StringBuffer();
        int min = 1;
        int max = 9;
        sb.append(zipAreaCode.get(town));
        for (int i=0;i<4;i++) {
            sb.append(generator.nextInt(max - min + 1) + min);
        }

        return sb.toString();
    }

    private static String getRandomHouseNr() {
        int min = 1;
        int max = 100;
        int lengthNumber = generator.nextInt(max - min + 1) + min;
        String houseNr = new String(Integer.toString(lengthNumber));
        return houseNr;
    }

    private static String getRandomStreet() {
        String streetName = null;
        generator = new Random();
        streetName = DataArray.streetList[generator.nextInt(76)];
        return streetName;
    }



}
