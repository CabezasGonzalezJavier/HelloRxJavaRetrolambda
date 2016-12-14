package com.thedeveloperworldisyours.hellorxjava.complex.flatmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by javierg on 13/12/2016.
 */

public class StringGenerator {
    private static final int DEFAULT_STRING_LENGHT = 12;
    private static final char[] symbols;

    static {
        StringBuilder tmpSymbols = new StringBuilder();
        for (char numberChar = '0'; numberChar <= '9'; numberChar++) {
            tmpSymbols.append(numberChar);
        }
        for (char letterChar = 'a'; letterChar <= 'z'; letterChar++) {
            tmpSymbols.append(letterChar);
        }
        symbols = tmpSymbols.toString().toCharArray();
    }

    private final int stringLenght;
    private final char[] charArray;
    private final Random random;

    public StringGenerator() {
        this(DEFAULT_STRING_LENGHT);
    }

    public StringGenerator(int stringLength) {
        this.stringLenght = stringLength;
        this.charArray = new char[stringLenght];
        this.random = new Random();
    }

    List<String> randomStringList() {
        final int size = 10;
        final List<String> elements = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            elements.add(nextString());
        }
        return elements;
    }

    String nextString() {
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = symbols[random.nextInt(symbols.length)];
        }
        return String.valueOf(charArray);
    }
}
