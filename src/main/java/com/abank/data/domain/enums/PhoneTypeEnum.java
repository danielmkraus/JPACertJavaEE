package com.abank.data.domain.enums;

public enum PhoneTypeEnum {
	MOBILE("mobil"),
    FIX_LINE("fest"),
    FAX("fax");

    final private String strValue;

    /**
     * Constructor to take a string and set the enum
     *
     * @param title
     */
    private PhoneTypeEnum(final String title) {
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
     * @return the corresponding JobRoleEnum value
     */
    public static PhoneTypeEnum strValueOf(final String pattern) {
        for (final PhoneTypeEnum value : PhoneTypeEnum.values()) {
            if (0 == value.getStrValue().compareTo(pattern)) {
                return value;
            }
        }
        return null;
    }
}
