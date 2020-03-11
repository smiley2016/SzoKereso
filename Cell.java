package com.bartalus;

class Cell {
    private java.lang.Character chr;
    private boolean visited ;

    Cell(java.lang.Character item) {
        chr = item;
        visited = false;
    }

    java.lang.Character getChr() {
        return chr;
    }

    boolean isVisited() {
        return visited;
    }

    void setVisited(boolean visited) {
        this.visited = visited;
    }
}
