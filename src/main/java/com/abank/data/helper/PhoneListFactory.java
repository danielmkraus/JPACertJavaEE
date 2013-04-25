package com.abank.data.helper;

import com.abank.data.domain.Phone;
import com.abank.data.domain.enums.PhoneTypeEnum;

import java.util.*;

/**
 * Created by: gruppd, 09.02.13 16:45
 */
public class PhoneListFactory {

    private PhoneListFactory() {
    }

    private static Map<String, String> areaCode;
    private static List<String> mobileCode;
    private static Random generator = new Random();

    private static void initializePhoneList() {
        areaCode = new HashMap<String, String>();
        areaCode.put("Muenchen", "089");
        areaCode.put("Hamburg", "040");
        areaCode.put("Berlin", "030");
        areaCode.put("Augsburg", "0821");
        areaCode.put("Dortmund", "0231");
        areaCode.put("Bonn", "0228");
        areaCode.put("Kassel", "0561");
        areaCode.put("Leipzig", "0341");
        areaCode.put("Rostock", "0381");
    }

    private static void intializeMobileNumberList() {
        mobileCode = new ArrayList<String>();
        mobileCode.add("0171");
        mobileCode.add("0179");
        mobileCode.add("0151");
        mobileCode.add("0172");
        mobileCode.add("0178");
        mobileCode.add("0161");
    }

    public static List<Phone> getPhoneList(String town) {
        initializePhoneList();
        intializeMobileNumberList();
        List<Phone> phoneList = new ArrayList<Phone>();
        Phone fixLine = new Phone();
        fixLine.setType(PhoneTypeEnum.FIX_LINE);
        fixLine.setNumber(getRandomFixNumber(town));
        phoneList.add(fixLine);

        Integer r = generator.nextInt(2);
        if (r.equals(new Integer(0))) {
            // generate mobile number
            Phone faxLine = new Phone();
            faxLine.setType(PhoneTypeEnum.MOBILE);
            faxLine.setNumber(getRandomMobileNumber());
            phoneList.add(faxLine);
        } else {
            // generate FAX
            Phone faxLine = new Phone();
            faxLine.setType(PhoneTypeEnum.FAX);
            faxLine.setNumber(getRandomFixNumber(town));
            phoneList.add(faxLine);
        }

        return phoneList;
    }

    /**
     * Generates a phone number based on a local area code for a specific town with a length of the numbers exceeding the local area code between min and max
     *
     * @param town
     * @return
     */
    private static String getRandomFixNumber(String town) {
        int min = 5;
        int max = 9;
        StringBuffer sb = new StringBuffer();
        sb.append(areaCode.get(town));
        int lengthNumber = generator.nextInt(max - min + 1) + min;
        for (int i = 0; i < lengthNumber; i++) {
            int r = generator.nextInt(10);
            sb.append(r);
        }

        return sb.toString();
    }

    private static String getRandomMobileNumber() {
        StringBuffer sb = new StringBuffer();
        sb.append(mobileCode.get(generator.nextInt(mobileCode.size())));
        for (int i = 0; i < 6; i++) {
            int r = generator.nextInt(10);
            sb.append(r);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        List<Phone> pList = getPhoneList("Berlin");
        printList(pList);
        pList = getPhoneList("Muenchen");
        printList(pList);
        pList = getPhoneList("Hamburg");
        printList(pList);
        pList = getPhoneList("Muenchen");
        printList(pList);
        pList = getPhoneList("Hamburg");
        printList(pList);
        pList = getPhoneList("Muenchen");
        printList(pList);
        pList = getPhoneList("Hamburg");
        printList(pList);
        pList = getPhoneList("Muenchen");
        printList(pList);
        pList = getPhoneList("Hamburg");
        printList(pList);
    }

    public static void printList(List<Phone> list) {
        for (Phone s : list) {
            System.out.println("Number");
            System.out.println(s.getType());
            System.out.println(s.getNumber());
        }
    }
}
