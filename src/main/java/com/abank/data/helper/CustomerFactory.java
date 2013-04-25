package com.abank.data.helper;

import com.abank.data.domain.Customer;

import java.util.*;

/**
 * Created by: gruppd, 09.02.13 16:16
 */
public class CustomerFactory {

    private static Random generator = new Random();

    private CustomerFactory() {
    }

    public static void main(String[] args) {
        List<Customer> lC = createCustomers(10);
        for (Customer customer : lC) {
            System.out.println(customer.toString());
        }
    }

    public static List<Customer> createCustomers(int number) {
        List<Customer> customerList = new ArrayList<Customer>();
        for (int i = 0; i < number; i++) {
            customerList.add(generateRandomCustomer());
        }
        return customerList;
    }

    private static Customer generateRandomCustomer() {
        String town = DataArray.towns[generator.nextInt(9)];
        Customer customer = new Customer();
        customer.setFirstname(DataArray.firstNames[generator.nextInt(390)]);
        customer.setLastname(DataArray.lastNames[generator.nextInt(100)]);
        customer.setBirthDate(generateRandomBirthdate());
        customer.setAddress(AddressFactory.generateRandomAddress(town));
        customer.setPhoneNr(PhoneListFactory.getPhoneList(town));
        return customer;
    }

    private static Calendar generateRandomBirthdate() {
        int year = randBetween(1930, 1990);
        int month = randBetween(0, 11);
        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int day = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        gc.set(year, month, day);
        return gc;
    }

    public static int randBetween(int min, int max) {
        return generator.nextInt(max - min + 1) + min;
    }

}
