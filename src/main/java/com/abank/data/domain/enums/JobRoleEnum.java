package com.abank.data.domain.enums;

/**
 * Created by: gruppd, 06.02.13 20:17
 */
public enum JobRoleEnum {

    KEY_ACC_MAN("Key Account Manager"),
    INV_CONSULT("Investment Consultant"),
    CUST_SERV_AGENT("Customer Service Agent"),
    DEP_MANAGER("Department Manager"),
    CRED_SELLER("Credit Seller"),
    DEBT_COLLECTOR("Debt Collector"),
    DEVELOPER("Developer"),
    ARCHITECT("Architect"),
    OPERATOR("Operator"),
    TESTER("Tester"),
    SYSTEM_ANALYST("System Analyst"),
    TEAM_ASS("Team Assistant"),
    LAWYER("Lawyer"),
    ATTORNEY("Attorney"),
    COUNSELER("Counseler");

    final private String strValue;

    /**
     * Constructor to take a string and set the enum
     *
     * @param title
     */
    private JobRoleEnum(final String title) {
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
    public static JobRoleEnum strValueOf(final String pattern) {
        for (final JobRoleEnum value : JobRoleEnum.values()) {
            if (0 == value.getStrValue().compareTo(pattern)) {
                return value;
            }
        }
        return null;
    }

    }
