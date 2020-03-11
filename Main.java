package com.bartalus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    private static ArrayList<Word> dict;
    private static Boggle boggle = null;
    private static boolean isPrinted = false;
    private static ArrayList<Path> paths;
    private static int counter = 0;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static ArrayList<String> deletedLiterals = new ArrayList<>();

    public static void main(String[] args) {
        dict = new ArrayList<>();
        Character.initCharacters();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
            String line;
            if ((line = reader.readLine()) != null) {
                boggle = new Boggle(Integer.parseInt(line));
            }

            if ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                for (String item : words) {
                    dict.add(new Word(item, "GEEKSQUIZ"));
                }
            }
            if (boggle != null) {
                for (int row = 0; row < boggle.getBoggle().size(); ++row) {
                    if ((line = reader.readLine()) != null) {
                        String[] words = line.split(",");
                        for (String item : words) {
                            boggle.addItem(new Cell(item.charAt(0)), row);
                        }
                    }
                }
            }

            while ((line = reader.readLine()) != null) {
                Character.fillList(line.charAt(0), Integer.parseInt(String.valueOf(line.charAt(line.length() - 1))));
            }

            System.out.println(Character.characters.toString());

            if (boggle != null) {
                boggle.print();
            }

            reader.close();
        } catch (FileNotFoundException e) {
            e.fillInStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("Valassza ki a metodust: 1, 2, 3");
//        Scanner scanner = new Scanner(System.in);
//        int input = scanner.nextInt();

        switch (args[0]) {
            case "1": {
                System.out.println("Backtracking");
                paths = new ArrayList<>();
                for (Word item : dict) {
                    findCharacterInBoggle(item.getWord().charAt(0), item, 1);
                    if (isPrinted) {
                        for (Path step : paths) {
                            boggle.getBoggle().get(step.getRow()).get(step.getCol()).setVisited(true);
                            counter++;
                        }
                    }
                    paths.clear();
                }
                System.out.println("Steps: " + counter);
                break;
            }
            case "2": {
                System.out.println("Backtracking + MRV + Forward Checking");
                MRV();
                paths = new ArrayList<>();
                for (Word item : dict) {
                    findCharacterInBoggle(item.getWord().charAt(0), item, 2);
                    if (isPrinted) {
                        for (Path step : paths) {
                            boggle.getBoggle().get(step.getRow()).get(step.getCol()).setVisited(true);
                        }
                    }
                    paths.clear();
                }
                System.out.println("Steps: " + counter);
                break;
            }
            case "3": {
                System.out.println("Backtracking + MRV + AC-3");
                MRV();
                paths = new ArrayList<>();
                for (Word item : dict) {
                    findCharacterInBoggle(item.getWord().charAt(0), item, 3);
                    if (isPrinted) {
                        for (Path step : paths) {
                            boggle.getBoggle().get(step.getRow()).get(step.getCol()).setVisited(true);
                            counter++;
                        }
                    }
                    paths.clear();
                }
                System.out.println("Steps: " + counter);
                break;
            }

        }

    }

    private static void BT(Word selectedWord, char c, int indexOfNextChar, int row, int col, StringBuilder foundWord) {
        int temp = Character.getCharacters().get(boggle.getBoggle().get(row).get(col).getChr() - 'A');
        if (temp == 1
                && (selectedWord.getWord().charAt(selectedWord.getWord().length() - 1) == c)) {
            System.out.println(foundWord);
            isPrinted = true;
        } else {
            if (isSafe(row, col)) {
                boggle.getBoggle().get(row).get(col).setVisited(true);
                counter++;
                paths.add(new Path(row, col));
                int value = Character.getCharacters().get(boggle.getBoggle().get(row).get(col).getChr() - 'A');
                Character.getCharacters().set(boggle.getBoggle().get(row).get(col).getChr() - 'A', value - 1);
                if (isSafe(row - 1, col + 1) &&
                        (boggle.getBoggle().get(row - 1).get(col + 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BT(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row - 1, col + 1, foundWord);
                }
                if (isSafe(row, col + 1) &&
                        (boggle.getBoggle().get(row).get(col + 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BT(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row, col + 1, foundWord);
                }
                if (isSafe(row + 1, col + 1) &&
                        (boggle.getBoggle().get(row + 1).get(col + 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BT(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row + 1, col + 1, foundWord);
                }
                if (isSafe(row + 1, col) &&
                        (boggle.getBoggle().get(row + 1).get(col).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BT(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row + 1, col, foundWord);
                }
                if (isSafe(row + 1, col - 1) &&
                        (boggle.getBoggle().get(row + 1).get(col - 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BT(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row + 1, col - 1, foundWord);
                }
                if (isSafe(row, col - 1) &&
                        (boggle.getBoggle().get(row).get(col - 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BT(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row, col - 1, foundWord);
                }
                if (isSafe(row - 1, col - 1) &&
                        (boggle.getBoggle().get(row - 1).get(col - 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BT(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row - 1, col - 1, foundWord);
                }
                if (isSafe(row - 1, col) &&
                        (boggle.getBoggle().get(row - 1).get(col).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BT(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row - 1, col, foundWord);
                }
                boggle.getBoggle().get(row).get(col).setVisited(false);
                counter++;
                value = Character.getCharacters().get(boggle.getBoggle().get(row).get(col).getChr() - 'A');
                Character.getCharacters().set(boggle.getBoggle().get(row).get(col).getChr() - 'A', value + 1);
            }
        }
    }

    private static void BTWithForwardCheck(Word selectedWord, char c, int indexOfNextChar, int row, int col, StringBuilder foundWord) {
        int temp = Character.getCharacters().get(boggle.getBoggle().get(row).get(col).getChr() - 'A');
        if (temp == 1
                && (selectedWord.getWord().charAt(selectedWord.getWord().length() - 1) == c)) {
            System.out.println(foundWord);
            isPrinted = true;
        } else {
            if (isSafe(row, col)) {
                boggle.getBoggle().get(row).get(col).setVisited(true);
                ForwardChecking(c, selectedWord);
                counter++;
                paths.add(new Path(row, col));
                int value = Character.getCharacters().get(boggle.getBoggle().get(row).get(col).getChr() - 'A');
                Character.getCharacters().set(boggle.getBoggle().get(row).get(col).getChr() - 'A', value - 1);
                if (isSafe(row - 1, col + 1) &&
                        (boggle.getBoggle().get(row - 1).get(col + 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithForwardCheck(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row - 1, col + 1, foundWord);
                }
                if (isSafe(row, col + 1) &&
                        (boggle.getBoggle().get(row).get(col + 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithForwardCheck(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row, col + 1, foundWord);
                }
                if (isSafe(row + 1, col + 1) &&
                        (boggle.getBoggle().get(row + 1).get(col + 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithForwardCheck(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row + 1, col + 1, foundWord);
                }
                if (isSafe(row + 1, col) &&
                        (boggle.getBoggle().get(row + 1).get(col).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithForwardCheck(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row + 1, col, foundWord);
                }
                if (isSafe(row + 1, col - 1) &&
                        (boggle.getBoggle().get(row + 1).get(col - 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithForwardCheck(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row + 1, col - 1, foundWord);
                }
                if (isSafe(row, col - 1) &&
                        (boggle.getBoggle().get(row).get(col - 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithForwardCheck(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row, col - 1, foundWord);
                }
                if (isSafe(row - 1, col - 1) &&
                        (boggle.getBoggle().get(row - 1).get(col - 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithForwardCheck(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row - 1, col - 1, foundWord);
                }
                if (isSafe(row - 1, col) &&
                        (boggle.getBoggle().get(row - 1).get(col).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithForwardCheck(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row - 1, col, foundWord);
                }
                boggle.getBoggle().get(row).get(col).setVisited(false);
                counter++;
                value = Character.getCharacters().get(boggle.getBoggle().get(row).get(col).getChr() - 'A');
                Character.getCharacters().set(boggle.getBoggle().get(row).get(col).getChr() - 'A', value + 1);
            }
        }
    }

    private static void BTWithAC3(Word selectedWord, char c, int indexOfNextChar, int row, int col, StringBuilder foundWord) {
        int temp = Character.getCharacters().get(boggle.getBoggle().get(row).get(col).getChr() - 'A');
        if (temp == 1
                && (selectedWord.getWord().charAt(selectedWord.getWord().length() - 1) == c)) {
            System.out.println(foundWord);
            isPrinted = true;
        } else {
            if (isSafe(row, col)) {
                boggle.getBoggle().get(row).get(col).setVisited(true);
                DeleteCurrentLiteralFromDomains(c, selectedWord);
                counter++;
                paths.add(new Path(row, col));
                int value = Character.getCharacters().get(boggle.getBoggle().get(row).get(col).getChr() - 'A');
                Character.getCharacters().set(boggle.getBoggle().get(row).get(col).getChr() - 'A', value - 1);
                if (isSafe(row - 1, col + 1) &&
                        (boggle.getBoggle().get(row - 1).get(col + 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithAC3(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row - 1, col + 1, foundWord);
                }
                if (isSafe(row, col + 1) &&
                        (boggle.getBoggle().get(row).get(col + 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithAC3(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row, col + 1, foundWord);
                }
                if (isSafe(row + 1, col + 1) &&
                        (boggle.getBoggle().get(row + 1).get(col + 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithAC3(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row + 1, col + 1, foundWord);
                }
                if (isSafe(row + 1, col) &&
                        (boggle.getBoggle().get(row + 1).get(col).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithAC3(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row + 1, col, foundWord);
                }
                if (isSafe(row + 1, col - 1) &&
                        (boggle.getBoggle().get(row + 1).get(col - 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithAC3(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row + 1, col - 1, foundWord);
                }
                if (isSafe(row, col - 1) &&
                        (boggle.getBoggle().get(row).get(col - 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithAC3(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row, col - 1, foundWord);
                }
                if (isSafe(row - 1, col - 1) &&
                        (boggle.getBoggle().get(row - 1).get(col - 1).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithAC3(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row - 1, col - 1, foundWord);
                }
                if (isSafe(row - 1, col) &&
                        (boggle.getBoggle().get(row - 1).get(col).getChr().equals(selectedWord.getWord().charAt(indexOfNextChar)))) {
                    foundWord.append(selectedWord.getWord().charAt(indexOfNextChar));
                    BTWithAC3(selectedWord, selectedWord.getWord().charAt(indexOfNextChar), indexOfNextChar + 1, row - 1, col, foundWord);
                }
                boggle.getBoggle().get(row).get(col).setVisited(false);
                insertDeletedLiterals(c, selectedWord);
                counter++;
                value = Character.getCharacters().get(boggle.getBoggle().get(row).get(col).getChr() - 'A');
                Character.getCharacters().set(boggle.getBoggle().get(row).get(col).getChr() - 'A', value + 1);
            }
        }
    }

    private static void insertDeletedLiterals(char c, Word selectedWord) {
        for (Word item : dict) {
            if (item == selectedWord) {
                continue;
            }
            StringBuilder word = new StringBuilder(item.getDomain());
            deletedLiterals.remove(c);
            word.append(c);
            item.setDomain(word.toString());
        }
    }

    private static void DeleteCurrentLiteralFromDomains(char c, Word currentWord) {
        for (Word item : dict) {
            if (item == currentWord) {
                continue;
            }

            if (item.getDomain().contains("" + c)) {
                StringBuilder word = new StringBuilder(item.getDomain());
                deletedLiterals.add("" + item.getDomain().charAt(item.getDomain().indexOf(c)));
                word.replace(item.getDomain().indexOf(c), item.getDomain().indexOf(c) + 1, "");
                item.setDomain(word.toString());
            }

            ArrayList<Word> modifiedList = new ArrayList<>();
            for (Word word : dict) {
                if (word != item) {
                    modifiedList.add(word);
                }
            }
            dict = modifiedList;
        }
    }


    private static void findCharacterInBoggle(char c, Word selectedWord, int arg) {
        StringBuilder foundWord = new StringBuilder();
        for (ArrayList<Cell> item : boggle.getBoggle()) {
            for (Cell item2 : item) {
                if (item2.getChr().equals(c)) {
                    foundWord.append(c);
                    counter++;
                    if (arg == 1) {
                        BT(selectedWord, selectedWord.getWord().charAt(0), 1,
                                boggle.getBoggle().indexOf(item),
                                boggle.getBoggle().get(boggle.getBoggle().indexOf(item)).indexOf(item2),
                                foundWord);
                    } else if (arg == 2) {
                        BTWithForwardCheck(selectedWord, selectedWord.getWord().charAt(0), 1,
                                boggle.getBoggle().indexOf(item),
                                boggle.getBoggle().get(boggle.getBoggle().indexOf(item)).indexOf(item2),
                                foundWord);
                    } else {
                        BTWithAC3(selectedWord, selectedWord.getWord().charAt(0), 1,
                                boggle.getBoggle().indexOf(item),
                                boggle.getBoggle().get(boggle.getBoggle().indexOf(item)).indexOf(item2),
                                foundWord);
                    }

                }
            }
        }
    }

    private static boolean isSafe(int i, int j) {
        return i >= 0 && i < boggle.getBoggle().size()
                && j >= 0 && j < boggle.getBoggle().size()
                && !boggle.getBoggle().get(i).get(j).isVisited();
    }


    private static void ForwardChecking(char c, Word currentWord) {
        for (Word item : dict) {
            if (item == currentWord) {
                continue;
            }

            if (item.getDomain().contains("" + c)) {
                StringBuilder word = new StringBuilder(item.getDomain());
                word.replace(item.getDomain().indexOf(c), item.getDomain().indexOf(c) + 1, "");
                item.setDomain(word.toString());
            }

            ArrayList<Word> modifiedList = new ArrayList<>();
            for (Word word : dict) {
                if (word != item) {
                    modifiedList.add(word);
                }
            }
            dict = modifiedList;
        }
    }


    private static void MRV() {
        dict.sort(new LexicographicComparator());
        System.out.println(dict);
        Iterator<Word> iterator = dict.iterator();
        Word current;
        while (iterator.hasNext()) {
            current = iterator.next();
            for (int i = 0; i < current.getWord().length(); ++i) {
                if (Character.characters.get(current.getWord().charAt(i) - 'A') == 0) {
                    iterator.remove();
                    break;
                }
            }
        }
        System.out.println(dict);
    }
}
