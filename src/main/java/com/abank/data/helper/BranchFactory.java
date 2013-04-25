package com.abank.data.helper;

import com.abank.data.domain.Branch;
import com.abank.data.domain.Department;
import com.abank.data.domain.enums.DepartmentNameEnum;

import java.util.*;

/**
 * Created by: gruppd, 09.02.13 16:16
 */
public class BranchFactory {

    private static List<Branch> branchList = null;
    private static Department informationTechnology = null;
    private static Department legal = null;
    private static Department cashing = null;
    private static Department investment = null;
    private static Branch muc = null;
    private static Branch ham = null;
    private static Branch ber = null;

    private BranchFactory() {}

    public static List<Branch> createBranches() {
        branchList = new ArrayList<Branch>();

        informationTechnology = new Department(DepartmentNameEnum.IT.getStrValue());
        legal = new Department(DepartmentNameEnum.LEGAL.getStrValue());
        cashing = new Department(DepartmentNameEnum.CASH_DEP.getStrValue());
        investment = new Department(DepartmentNameEnum.INVEST.getStrValue());

        muc = new Branch("München", AddressFactory.generateRandomAddress("Muenchen"));
        muc.getDepartments().add(informationTechnology);
        muc.getDepartments().add(legal);
        branchList.add(muc);

        ham = new Branch("Hamburg", AddressFactory.generateRandomAddress("Hamburg"));
        ham.getDepartments().add(informationTechnology);
        ham.getDepartments().add(cashing);
        ham.getDepartments().add(legal);
        branchList.add(ham);

        ber = new Branch("Berlin", AddressFactory.generateRandomAddress("Berlin"));
        ber.getDepartments().add(informationTechnology);
        ber.getDepartments().add(investment);
        branchList.add(ber);

        return branchList;
    }

}
