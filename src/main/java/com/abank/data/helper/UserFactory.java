package com.abank.data.helper;

import com.abank.data.domain.Employee;
import com.abank.data.domain.Systemuser;

import java.util.*;

/**
 * Created by: gruppd, 09.02.13 16:16
 */
public class UserFactory {

    public static List<Systemuser> createUserList(List<Employee> employeeList) {
        List<Systemuser> userList = new ArrayList<Systemuser>();
        for (Employee employee : employeeList) {
        	Systemuser user = new Systemuser();
        	String firstPart = getSubstring(employee.getLastname(), 5);
        	String lastPart = getSubstring(employee.getFirstname(), 2);
        	String login = firstPart.concat(lastPart);
        	user.setLogin(login);
        	user.setPassword(login);
        	user.setLoggedIn(false);
        	userList.add(user);
        }
        return userList;
    }

    private static String getSubstring(String name, int num) {
    	String repText = null;
    	repText = name.replace("ä","ae");
    	repText = repText.replace("ö","oe");
    	repText = repText.replace("ü","ue");
    	repText = repText.replace("ß","ss");
    	if (repText.length() < num) {
    		repText = repText.substring(0,repText.length());    		
    	} else {
    		repText = repText.substring(0,num);
    	}
    	    	

        return repText;
    }

}
