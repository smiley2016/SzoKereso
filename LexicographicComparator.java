package com.bartalus;

import java.util.Comparator;

public class LexicographicComparator implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        return Integer.compare(o1.getWord().length(),o2.getWord().length());
    }
}
