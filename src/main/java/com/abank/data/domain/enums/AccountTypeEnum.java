package com.abank.data.domain.enums;

/**
 * Created by: gruppd, 06.02.13 20:17
 */
public enum AccountTypeEnum {

    SAVINGS("Savings Account"),
    CUSTODY("Custody Account"),
    GIRO("Giro Account"),
    MANAGED("Managed Account");

    final private String strValue;

    /**
     * Constructor to take a string and set the enum
     *
     * @param title
     */
    private AccountTypeEnum(final String title) {
        this.strValue = title;
    }

    /**
     * getter for the string value
     *
     * @return
     */
    public String getStrValue() {
        return strValue;
    }

    /**
     * getter which takes a string
     *
     * @param pattern
     * @return the corresponding enum value
     */
    public static AccountTypeEnum strValueOf(final String pattern) {
        for (final AccountTypeEnum value : AccountTypeEnum.values()) {
            if (0 == value.getStrValue().compareTo(pattern)) {
                return value;
            }
        }
        return null;
    }

}
