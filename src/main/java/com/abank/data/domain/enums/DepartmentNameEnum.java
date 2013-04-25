package com.abank.data.domain.enums;

/**
 * Created by: gruppd, 06.02.13 20:17
 */
public enum DepartmentNameEnum {

    IT("Information Technology"),
    LEGAL("Legal Affairs"),
    CASH_DEP("Cash Department"),
    INVEST("Investment");

    final private String strValue;

    /**
     * Constructor to take a string and set the enum
     * @param title
     */
    private DepartmentNameEnum(final String title) {
        this.strValue = title;
    }

    /**
     * getter for the string value
     * @return
     */
    public String getStrValue() {
        return strValue;
    }

    /**
     * getter which takes a string
     * @param pattern
     * @return the corresponding enum value
     */
    public static DepartmentNameEnum strValueOf(final String pattern) {
        for (final DepartmentNameEnum value : DepartmentNameEnum.values()) {
            if (0 == value.getStrValue().compareTo(pattern)) {
                return value;
            }
        }
        return null;
    }

}
