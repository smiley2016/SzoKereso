package com.bartalus;

import java.util.ArrayList;

public class Character {
    static ArrayList<Integer> characters ;

    static ArrayList<Integer> getCharacters() {
        return characters;
    }

    static void initCharacters(){
        characters = new ArrayList<>();
        for(int i = 0; i<26; ++i){
            characters.add(0);
        }

    }

    public static void setCharacters(ArrayList<Integer> characters) {
        Character.characters = characters;
    }

    static void fillList(char chr, int num){
        characters.set(chr - 'A', num);
    }

    public int getChrNumber(char chr){
        return characters.get(chr - 'A');
    }
}
