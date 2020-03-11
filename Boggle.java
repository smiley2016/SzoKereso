package com.bartalus;

import java.util.ArrayList;

class Boggle {

    private ArrayList<ArrayList<Cell>> boggle;

    Boggle(int size) {
        boggle = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            boggle.add(new ArrayList<>());
        }
    }

    ArrayList<ArrayList<Cell>> getBoggle() {
        return boggle;
    }

    void addItem(Cell cell, int counter) {
        boggle.get(counter).add(cell);
    }

    void print() {
        for (ArrayList<Cell> strings : boggle) {
            for (Cell cell : strings) {
                System.out.print(cell.getChr() + " ");
            }
            System.out.println();
        }
    }
}
