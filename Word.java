package com.bartalus;

import java.util.ArrayList;
import java.util.Comparator;

public class Word implements Comparator {
    private String domain;
    private String word;

    Word(String word, String boggleCharacters) {
        domain = boggleCharacters;
        this.word = word;
    }

    String getDomain() {
        return domain;
    }

    void setDomain(String domain) {
        this.domain = domain;
    }

    String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public int compare(Object o1, Object o2) {
        int ob1 = ((Word)o1).getWord().length();
        int ob2 = ((Word)o2).getWord().length();

        return Integer.compare(ob1, ob2);
    }

    @Override
    public String toString() {
        return word;
    }
}
