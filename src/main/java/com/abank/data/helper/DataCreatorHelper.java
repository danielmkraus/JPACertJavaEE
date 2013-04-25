package com.abank.data.helper;

import java.util.*;

/**
 * Created by: gruppd, 21.02.13 20:03
 */
public class DataCreatorHelper extends Random {

    private static final long serialVersionUID = -8029274929211990323L;

    private static Random generator = new Random();


    /**
     * Generates a random Calendar object between the given ranges (year)
     *
     * @param from
     * @param until
     * @return
     */
    public static Calendar generateRandomCalendar(Integer from, Integer until) {
        int year = randIntBetween(from, until);
        int month = randIntBetween(0, 11);
        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int day = randIntBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        gc.set(year, month, day);
        return gc;
    }


    /**
     * In random cases generates a date which is between at least one year after the start date and earlier than 2012
     *
     * @param startDate
     * @return
     */
    public static Calendar generateEndDate(Calendar startDate) {
        Calendar retDate;
        Integer random = randIntBetween(0, 10);
        if (random.compareTo(new Integer(4)) == 0) {
            retDate = generateRandomCalendar(startDate.get(Calendar.YEAR) + 1, 2012);
            return retDate;
        } else {
            return null;
        }
    }


    /**
     * chops a list into non-view sublists of length L
     *
     * @param list
     * @param L
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(list.subList(i, Math.min(N, i + L))));
        }
        return parts;
    }


    /**
     * Generates a random integer within a given range
     * @param min
     * @param max
     * @return a random integer
     */
    public static int randIntBetween(int min, int max) {
        return generator.nextInt(max - min + 1) + min;
    }


}
