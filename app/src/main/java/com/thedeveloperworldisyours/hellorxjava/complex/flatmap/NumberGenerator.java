package com.thedeveloperworldisyours.hellorxjava.complex.flatmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by javierg on 13/12/2016.
 */

public class NumberGenerator {

    public NumberGenerator() {}

    List<Integer> numbers() {
        return new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    List<Long> numbers(int upUntil) {
        final ArrayList<Long> numberList = new ArrayList<>(upUntil);
        for (long i = 0; i < upUntil; i++) {
            numberList.add(i);
        }
        return numberList;
    }
}
