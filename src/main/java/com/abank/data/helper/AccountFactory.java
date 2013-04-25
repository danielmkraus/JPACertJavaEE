package com.abank.data.helper;

import com.abank.data.domain.Account;
import com.abank.data.domain.enums.AccountTypeEnum;

import java.util.Calendar;
import java.util.logging.Logger;

/**
 * Created by: gruppd, 11.02.13 19:02
 */
public class AccountFactory {

    public static final Logger logger = Logger.getLogger(AccountFactory.class.getCanonicalName());

    /**
     * empty constructor
     */
    private AccountFactory() {}

    /**
     * A factory for an Account
     * @return an Account object with rather random values
     */
    public static Account generateRandomAccount() {
        Account retValue = new Account();
        String accountTypeString = DataArray.accountTypes[DataCreatorHelper.randIntBetween(0, DataArray.accountTypes.length - 1)];
        retValue.setAccountType(AccountTypeEnum.strValueOf(accountTypeString));
        retValue.setBankAccountNumber((long) DataCreatorHelper.randIntBetween(1000000, 2147483647));
        retValue.setBankIdentifierNumber((long) DataCreatorHelper.randIntBetween(1000000, 99999999));
        retValue.setIban(null);
        retValue.setSwift(null);
        Calendar openDate = DataCreatorHelper.generateRandomCalendar(1980, 2011);
        retValue.setOpenDate(openDate);
        retValue.setCloseDate(DataCreatorHelper.generateEndDate(openDate));

        return retValue;
    }


}
