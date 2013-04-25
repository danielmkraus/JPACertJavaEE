package com.abank.data.domain.enums;

public enum CountryEnum {
	
	FRANCE("France"),
    ENGLAND("England"),
    GERMANY("Germany"),
    NETHERLANDS("Netherlands"),
    ITALY("Italy");

    final private String strValue;

    /**
     * Constructor to take a string and set the enum
     *
     * @param title
     */
    private CountryEnum(final String title) {
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
    public static CountryEnum strValueOf(final String pattern) {
        for (final CountryEnum value : CountryEnum.values()) {
            if (0 == value.getStrValue().compareTo(pattern)) {
                return value;
            }
        }
        return null;
    }

}
